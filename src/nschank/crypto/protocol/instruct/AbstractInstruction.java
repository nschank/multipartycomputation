package nschank.crypto.protocol.instruct;

import java.util.*;


/**
 * Created by Nicolas Schank for package nschank.crypto.protocol.instruct
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * An implementation of the {@code Instruction} interface which deals with some minutiae, mostly the internals of
 * arguments. Assumes that expected arguments are constant
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public abstract class AbstractInstruction<T> implements Instruction<T>
{
	protected final Set<String> argumentNames;
	protected final Map<String, Object> arguments;
	private final String name;
	private final String plaintext;
	protected Random randomness = new Random();

	/**
	 * Creates an {@code AbstractInstruction}, which possesses only the internals of arguments and randomness.
	 *
	 * @param expectedArguments
	 * 		The names of all arguments which are expected by this instruction
	 */
	protected AbstractInstruction(Set<String> expectedArguments, String name, String plaintext)
	{
		this.name = name;
		this.plaintext = plaintext;
		this.argumentNames = Collections.unmodifiableSet(expectedArguments);
		this.arguments = new HashMap<>();
	}

	/**
	 * @param name
	 * 		The name of an argument
	 *
	 * @return The value attached to that name, or null if no such argument exists
	 */
	@Override
	public Object get(final String name)
	{
		return this.arguments.get(name);
	}

	@Override
	public Map<String, Object> getAll()
	{
		return Collections.unmodifiableMap(this.arguments);
	}

	/**
	 * @return The variable names of all required inputs to this Instruction
	 */
	@Override
	public Iterable<String> inputs()
	{
		//Already unmodifiable
		return argumentNames;
	}

	/**
	 * @return A description, in English (or math) which describes exactly how this {@code Instruction} is evaluated.
	 */
	@Override
	public String plaintext()
	{
		return this.plaintext;
	}

	/**
	 * Forces an Instruction to use the given Random. If you want to ensure that the Instruction is indeed using the
	 * provided randomness, try resupplying the Instruction with a new random and the same seed a few dozen times, to
	 * see that the same value is created.
	 *
	 * @param randomness
	 * 		A new Randomness value to use
	 */
	@Override
	public void randomize(final Random randomness)
	{
		this.randomness = randomness;
	}

	/**
	 * Returns true if all values in {@code inputs()} have been provided to this {@code Instruction}s
	 *
	 * @return Whether or not this Instruction has all inputs supplied to it yet.
	 */
	@Override
	public boolean ready()
	{
		return this.arguments.keySet().containsAll(this.argumentNames);
	}

	/**
	 * @return The name of this Instruction's output, according to the protocol.
	 */
	@Override
	public String returnedName()
	{
		return this.name;
	}

	/**
	 * Provides this {@code Instruction} with a value of the given name. Returns true if the {@code Instruction} wants
	 * the value (that is, it expected a value with that name).
	 *
	 * @param valueName
	 * 		The name of an argument to supply to this {@code Instruction}
	 * @param value
	 * 		The value to supply under that name
	 *
	 * @return True iff {@code inputs()} contains {@code valueName}
	 */
	@Override
	public boolean supply(final String valueName, final Object value)
	{
		if(this.argumentNames.contains(valueName))
		{
			this.arguments.put(valueName, value);
			return true;
		} else return false;
	}

	/**
	 * @return A String representation of this AbstractInstruction
	 */
	@Override
	public String toString()
	{
		return this.plaintext();
	}
}
