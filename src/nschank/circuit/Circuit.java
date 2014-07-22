package nschank.circuit;

import nschank.collect.graph.DirectedAcyclicGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * Created by Nicolas Schank for package nschank.circuit
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A Circuit is an
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class Circuit extends DirectedAcyclicGraph<Gate>
{
	final static Pattern finish = Pattern.compile("^\\s*(\\w+\\d*)\\s*$");

	final static Pattern reduceParen = Pattern.compile("\\(\\s*(\\w+\\d*)\\s*\\)");
	final static Pattern not = Pattern.compile("\\s*NOT\\s+(\\w+\\d*)\\s*");
	final static Pattern simpleGate = Pattern.compile("\\s*(\\w+\\d*)\\s+(AND|OR|NOR|NAND|XOR)\\s+(\\w+\\d*)\\s*");



	public Map<Gate,Boolean> evaluate(Map<Gate,Boolean> input)
	{
		for(Gate g : this.top)
			input.put(g, evaluate(g, input));

		return input;
	}

	private boolean evaluate(Gate eval, Map<Gate,Boolean> inputs)
	{
		if(inputs.containsKey(eval)) return inputs.get(eval);
		else
		{
			this.allConnectedTo(eval).forEach(g -> inputs.put(g, evaluate(g,inputs)));
			return eval.apply(this.allConnectedTo(eval).stream().map(inputs::get).collect(Collectors.toList()));
		}
	}

	public static Circuit build(String description, Map<String,Gate> inputGates)
	{
		if(description.isEmpty()) return new Circuit();

		int counter = 0;
		Circuit c = new Circuit();
		Map<String,Gate> gateNames = new HashMap<>();

		boolean failure = false;
		while(!failure)
		{
			failure=true;
			if(finish.matcher(description).matches())
				return c;

			Matcher current;
			while((current = reduceParen.matcher(description)).find())
			{
				description = current.replaceFirst(" " + current.group(1) + " ");
				failure = false;
			}

			while((current = not.matcher(description)).find())
			{
				String notting = current.group(1);
				addIfInput(notting, c, gateNames, inputGates);
				Gate g = Gate.newGate(Gate.NOR);
				gateNames.put("lit" + (counter), g);
				c.add(g);

				description = current.replaceFirst(" lit" + counter + " ");
				c.connect(gateNames.get("lit"+(counter++)), gateNames.get(notting));

				failure = false;
			}

			while((current = simpleGate.matcher(description)).find())
			{
				String a = current.group(1);
				String gate = current.group(2);
				String b = current.group(3);

				addIfInput(a, c, gateNames, inputGates);
				addIfInput(b, c, gateNames, inputGates);

				Gate g = Gate.newGate(Gate.fromName(gate));
				gateNames.put("lit" + counter, g);
				c.add(g);

				description = current.replaceFirst(" lit" + counter +" ");
				c.connect(gateNames.get("lit" + counter), gateNames.get(a));
				c.connect(gateNames.get("lit" + (counter++)), gateNames.get(b));

				failure = false;
			}

		}
		return null;
	}

	public static void addIfInput(String name, Circuit c, Map<String,Gate> gates, Map<String,Gate> inputs)
	{
		if(!gates.containsKey(name))
		{
			Gate g = Gate.newGate(Gate.INPUT);
			inputs.put(name, g);
			gates.put(name, g);
			c.add(g);
		}
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{
		Map<String, Gate> inputs = new HashMap<>();
		String descr = "A AND B AND C AND D AND E OR X";

		Circuit c = build(descr, inputs);

		Gate output = c.top.iterator().next();

		List<String> var = inputs.keySet().stream().collect(Collectors.toList());

		for(String s : var)
			System.out.print(s + "\t\t");
		System.out.println(descr);
		for(int i = 0; i < Math.pow(2,var.size()); i++)
		{
			Map<Gate,Boolean> currentInputs = new HashMap<>();

			for(int j = 0; j < var.size(); j++)
			{
				System.out.print(((i&(1 << j))==0) + "\t");
				currentInputs.put(inputs.get(var.get(j)), (i & (1 << j)) == 0);
			}
			System.out.println(c.evaluate(currentInputs).get(output));
		}
	}
}
