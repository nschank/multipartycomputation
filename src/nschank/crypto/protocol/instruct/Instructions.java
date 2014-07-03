package nschank.crypto.protocol.instruct;

import nschank.crypto.player.Participant;

import java.util.Collections;
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

	/**
	 * Creates an Instruction which creates a variable named {@code name} by doing whatever is described in
	 * {@code description}. The function {@code creator} performs the actions required using the created Instruction
	 * and that Instruction's {@code Random} as inputs.
	 * @param name
	 * 		The name of the created variable
	 * @param description
	 * 		The description of the action performed by this {@code Instruction}
	 * @param creator
	 * 		The action to be performed by this {@code Instruction}. The {@code Instruction} and its random are the inputs
	 * 		to the function; the output is assigned to the variable {@code name}.
	 * @param arguments
	 * 		A list of all variable names required by this {@code Instruction}
	 * @return An Instruction built from these inputs.
	 */
	public static Instruction createFrom(final String name, final String description,
										 final BiFunction<Instruction, Random, Object> creator,
										 final String... arguments)
	{
		final Set<String> required = new HashSet<>();
		Collections.addAll(required, arguments);
		return new AbstractInstruction(required, name, description)
		{
			@Override
			public Object call() throws Exception
			{
				return creator.apply(this, this.randomness);
			}
		};
	}

	/**
	 * An Instruction that assigns the value currently at {@code fromname} to the name {@code newname}.
	 * @param newname
	 * 		The new name for a variable
	 * @param description
	 * 		The description of the action performed by this {@code Instruction}
	 * @param fromname
	 * 		An existing variable whose value needs to be reused somehow
	 * @return An Instruction built from these inputs
	 */
	public static Instruction rename(final String newname, final String description, final String fromname)
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

	/**
	 * An Instruction which sends the value at the variable named {@code tosend} from the Participant {@code from} to the
	 * Participant {@code to}.
	 * @param tosend
	 * 		The name of a variable to send
	 * @param description
	 * 		The description of the action performed by this {@code Instruction}
	 * @param from
	 * 		The Participant sending this value. Should possess a variable named {@code tosend}
	 * @param to
	 * 		The Participant expecting this value. Will possess a variable named {@code tosend} after this Instruction
	 * 		is run.
	 * @return An Instruction built from these inputs.
	 */
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
				return to.provide(tosend, this.get(tosend), from);
			}
		};
	}
}
