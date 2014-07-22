package nschank.circuit;

import nschank.collect.graph.DirectedAcyclicGraph;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * Created by Nicolas Schank for package nschank.circuit
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A Circuit is type of DirectedAcyclicGraph than can additionally be "evaluated"; that is, the
 * most ancestral nodes can be associated with a particular function value based on the values of
 * their children, all the way down to the lowest nodes, which are 'inputs' and must be directly
 * associated in the evaluation call.
 *
 * @author nschank, Brown University
 * @version 2.1
 */
public class Circuit<T> extends DirectedAcyclicGraph<Function<Collection<T>,T>>
{
	public Map<Function<Collection<T>,T>,T> evaluate(Map<Function<Collection<T>,T>,T> input)
	{
		for(Function<Collection<T>, T> output : this.top)
			input.put(output, evaluate(output, input));

		return input;
	}

	private T evaluate(Function<Collection<T>, T> eval, Map<Function<Collection<T>, T>,T> inputs)
	{
		if(inputs.containsKey(eval)) return inputs.get(eval);
		else
		{
			this.allConnectedTo(eval).forEach(g -> inputs.put(g, evaluate(g,inputs)));
			return eval.apply(this.allConnectedTo(eval).stream().map(inputs::get).collect(Collectors.toList()));
		}
	}

	public Collection<Function<Collection<T>,T>> outputs()
	{
		return Collections.unmodifiableSet(this.top);
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		Map<String, Function<Collection<Boolean>, Boolean>> inputs = new HashMap<>();
		String descr = "x XOR y AND NOT z";

		Circuit<Boolean> c = Circuits.buildBooleanCircuit(descr, inputs);

		Function<Collection<Boolean>, Boolean> output = c.top.iterator().next();

		List<String> var = inputs.keySet().stream().collect(Collectors.toList());

		for(String s : var)
			System.out.print(s + "\t\t");
		System.out.println(descr);
		for(int i = 0; i < Math.pow(2,var.size()); i++)
		{
			Map<Function<Collection<Boolean>, Boolean>,Boolean> currentInputs = new HashMap<>();

			for(int j = 0; j < var.size(); j++)
			{
				System.out.print(((i&(1 << j))==0) + "\t");
				currentInputs.put(inputs.get(var.get(j)), (i & (1 << j)) == 0);
			}
			System.out.println(c.evaluate(currentInputs).get(output));
		}
	}
}
