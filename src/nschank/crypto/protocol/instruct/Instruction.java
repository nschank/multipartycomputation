package nschank.crypto.protocol.instruct;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;


/**
 * Created by Nicolas Schank for package nschank.crypto.protocol.instruct
 * Created on 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * An {@code Instruction} is the interface through which {@code Participant}s run a {@code Protocol}. {@code Instruction}s
 * have the following properties:
 * <ol>
 * <li>An Instruction can be run as many times as desired in order to ensure correctness.</li>
 * <li>An Instruction can be supplied with a different randomness than its default one, should a {@code Participant}
 * want to not depend on the randomness of the {@code Protocol}.</li>
 * <li>An Instruction must be completely transparent about exactly what values are being used; as such, a plaintext
 * version of the Instruction's evaluation must be supplied.</li>
 * </ol>
 *
 * @author nschank, Brown University
 * @version 1.1
 * @see nschank.crypto.protocol.Protocol
 */
public interface Instruction<T> extends Callable<T>
{
	/**
	 * @param name
	 * 		The name of an argument
	 *
	 * @return The value attached to that name, or null if no such argument exists
	 */
	public Object get(String name);
	/**
	 * @return All arguments bound within this instruction
	 */
	Map<String, Object> getAll();
	/**
	 * @return The variable names of all required inputs to this Instruction
	 */
	public Iterable<String> inputs();
	/**
	 * @return A description, in English (or math) which describes exactly how this {@code Instruction} is evaluated.
	 */
	default public String plaintext()
	{
		return "*untrustworthy instruction*";
	}
	/**
	 * Forces an Instruction to use the given Random. If you want to ensure that the Instruction is indeed using the
	 * provided randomness, try resupplying the Instruction with a new random and the same seed a few dozen times, to
	 * see that the same value is created.
	 *
	 * @param randomness
	 * 		A new Randomness value to use
	 */
	public void randomize(Random randomness);
	/**
	 * @return Whether or not this Instruction has all inputs supplied to it yet.
	 */
	public boolean ready();
	/**
	 * @return The name of this Instruction's output, according to the protocol.
	 */
	public String returnedName();
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
	public boolean supply(String valueName, Object value);
}
