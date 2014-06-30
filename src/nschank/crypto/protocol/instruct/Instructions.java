package nschank.crypto.protocol.instruct;

import nschank.crypto.player.Participant;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;


/**
 * Created by Nicolas Schank for package nschank.crypto.protocol.instruct
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * A Utility class for dealing with {@code Instruction}s
 *
 * @author nschank, Brown University
 * @version 1.1
 * @see nschank.crypto.protocol.instruct.Instruction
 */
public final class Instructions
{
	/**
	 *
	 */
	private Instructions()
	{
		//Utility class
	}

	public static Instruction choose(final String newname, final String description, final String fromname)
	{
		final Set<String> required = new HashSet<>();
		required.add(fromname);
		return new AbstractInstruction(required, newname, description)
		{
			@Override
			public Object call() throws Exception
			{
				return this.arguments.get(fromname);
			}
		};
	}

	public static Instruction createFrom(final String name, final String description,
										 final BiFunction<Instruction, Random, Object> creator,
										 final String... arguments)
	{
		final Set<String> required = new HashSet<>();
		for(String arg : arguments)
			required.add(arg);
		return new AbstractInstruction(required, name, description)
		{
			@Override
			public Object call() throws Exception
			{
				return creator.apply(this, this.randomness);
			}
		};
	}

	public static Instruction send(final String tosend, final String description, final Participant from,
								   final Participant to)
	{
		final Set<String> required = new HashSet<>();
		required.add(tosend);
		return new AbstractInstruction(required, "!received:" + tosend, description)
		{
			@Override
			public Object call() throws Exception
			{
				while(!to.provide(tosend, this.get(tosend), from)) ;
				return true;
			}
		};
	}
}
