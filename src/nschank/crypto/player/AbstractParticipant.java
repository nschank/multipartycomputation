package nschank.crypto.player;

import nschank.crypto.history.History;
import nschank.crypto.protocol.instruct.Instruction;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;


/**
 * Created by Nicolas Schank for package nschank.crypto.player
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * An implementation of {@code Participant} which deals with most of the annoying threading aspects inherent in their
 * design.
 *
 * TODO allow participants to perform multiple protocols by fixing up run, expect, and receive
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public abstract class AbstractParticipant implements Participant
{
	private ExecutorService exec = Executors.newFixedThreadPool(5);
	private Map<Participant, Collection<String>> expected;
	private History h;
	private Iterable<Instruction> instructions;
	protected Map<String, Wrapper> values;

	/**
	 * @param h
	 * 		A History for this Participant to contribute to
	 */
	protected AbstractParticipant(History h)
	{
		this.values = new HashMap<>();
		this.h = h;
	}

	/**
	 * Intended only for use by a {@code Protocol} to inform this {@code Participant} what values it should expect from
	 * other {@code Participant}s.
	 *
	 * @param expectedValues
	 * 		A mapping from {@code Participant}s to the values expected to receive from them
	 */
	@Override
	public void expect(final Map<Participant, Collection<String>> expectedValues)
	{
		this.expected = expectedValues;
		for(Participant p : expectedValues.keySet())
			for(String name : expectedValues.get(p))
				values.put(name, new Wrapper()); //An empty wrapper, so that execution will stop when
	}

	/**
	 * Intended for use mostly by {@code Instruction}s, to give other {@code Participant}s values.
	 *
	 * @param valueName
	 * 		The name of a value the {@code Participant} is expecting
	 * @param value
	 * 		The value under that name
	 * @param from
	 * 		The Participant sending the value
	 *
	 * @return True iff both: this {@code Participant} was expecting the value, and the {@code Participant} agreed
	 * with the value provided (that is, accepted the value as correct).
	 */
	@Override
	public boolean provide(final String valueName, final Object value, final Participant from)
	{
		if(!this.expected.containsKey(from) || !this.expected.get(from).contains(valueName))
		{
			this.h.add(this.getName() + " refused a value " + valueName + "=" + value + " from " + from.getName(),
					   this);
			return false;
		} else
		{
			this.h.add(this.getName() + " accepted a value " + valueName + "=" + value + " from " + from.getName(),
					   this);
			this.values.get(valueName).putLiteral(value);
		}
		return true;
	}

	/**
	 * Intended only for use by a {@code Protocol} to provide this {@code Participant} with an ordered list of
	 * {@code Instruction}s to follow.
	 *
	 * @param protocol
	 * 		A list of {@code Instruction}s to follow.
	 */
	@Override
	public void receive(final Iterable<Instruction> protocol)
	{
		this.h.add(this.getName() + " was told how to perform a protocol.", this);
		this.instructions = protocol;
	}

	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run()
	{
		try
		{
			for(Instruction i : this.instructions)
			{
				for(Object s : i.inputs())
				{
					try
					{
						i.supply(s.toString(), this.values.get(s.toString()).get(30, TimeUnit.SECONDS));
					} catch(TimeoutException t)
					{
						throw new Exception("Instruction " + i.plaintext() + " timed out when waiting for argument " + s
								.toString());
					} catch(NullPointerException n)
					{
						throw new Exception("Required argument " + s.toString() + " in instruction " + i.plaintext()
													+ " could not be found.");
					}
				}
				Object value;
				value = exec.submit(i).get(30, TimeUnit.SECONDS);
				this.h.add(this.getName() + " set " + i.returnedName() + " to " + value + ": " + i.plaintext(), this);
				this.values.put(i.returnedName(), new Wrapper(value));
			}
			this.h.add(this.getName() + " got an output: " + this.values.get("output").get(), this);
		} catch(Exception e)
		{
			this.h.add(this.getName() + " failed to correctly perform the protocol.");
			e.printStackTrace();
		} finally
		{
			exec.shutdownNow();
		}
	}

	/**
	 * @return A String representation of this AbstractParticipant
	 */
	@Override
	public String toString()
	{
		throw new UnsupportedOperationException("toString not yet implemented");
	}

	/**
	 * Wraps any object as a Future
	 */
	protected static class Wrapper implements Future<Object>
	{
		private volatile Optional<Object> value;

		/**
		 * Creates a Future which will only return a value after putLiteral() is used.
		 */
		public Wrapper()
		{
			this.value = Optional.empty();
		}

		/**
		 * Creates a Future which gets instantly, and returns the given value
		 *
		 * @param value
		 * 		What value to wrap in this FutureWrapper
		 */
		public Wrapper(Object value)
		{
			this.value = Optional.of(value);
		}

		@Override
		public boolean cancel(final boolean mayInterruptIfRunning)
		{
			return false;
		}

		@Override
		public Object get() throws InterruptedException, ExecutionException
		{
			while(!this.value.isPresent()) ;
			return this.value.get();
		}

		@Override
		public Object get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException,
				TimeoutException
		{
			long finish = System.nanoTime() + unit.toNanos(timeout);
			while(!value.isPresent() && finish > System.nanoTime()) ;
			if(value.isPresent()) return value.get();
			else throw new TimeoutException();
		}

		@Override
		public boolean isCancelled()
		{
			return false;
		}

		@Override
		public boolean isDone()
		{
			return this.value.isPresent();
		}

		/**
		 * Sets the internal value of this wrapper to the given value
		 *
		 * @param value
		 * 		Any value
		 */
		public void putLiteral(Object value)
		{
			this.value = Optional.of(value);
		}
	}
}
