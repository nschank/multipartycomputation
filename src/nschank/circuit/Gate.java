package nschank.circuit;

import java.util.Collection;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.circuit
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A single Boolean gate. Due to the simplicity of constructing gates with higher fan-in than 2 using gates with fan-in 2,
 *  we will assume all gates have fan-in 2. Gates can be treated as being a function of two inputs returning a single
 *  output. Gates should all have the following properties:
 * 		- Consistency of output: If the two inputs are identical to the same gate, the output must also be identical.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public interface Gate extends Function<Collection<Boolean>, Boolean>
{
	public static final Gate AND = e->e.stream().allMatch(i -> i);
	public static final Gate OR = e->e.stream().anyMatch(i->i);
	public static final Gate NOR = e->!e.stream().anyMatch(i -> i);
	public static final Gate NAND = e->!e.stream().allMatch(i->i);
	public static final Gate OUTPUT = e->e.iterator().next();
	public static final Gate INPUT = e->{throw new Error("Input value not declared during evaluation!");};
	public static Gate fromName(String name)
	{
		switch(name)
		{
			case "AND": return AND;
			case "OR": return OR;
			case "NOR": return NOR;
			case "NAND": return NAND;
			case "OUTPUT": return OUTPUT;
			case "INPUT": return INPUT;
			default: return null;
		}
	}

	public static Gate newGate(final Function<Collection<Boolean>,Boolean> fun)
	{
		return new Gate()
		{
			@Override
			public Boolean apply(final Collection<Boolean> booleans)
			{
				return fun.apply(booleans);
			}
		};
	}
}
