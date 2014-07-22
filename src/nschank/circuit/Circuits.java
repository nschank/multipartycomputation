package nschank.circuit;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Nicolas Schank for package nschank.circuit
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A Utility class or dealing with {@code Circuit}s
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public final class Circuits
{
	private final static Pattern finish = Pattern.compile("^\\s*(\\w+\\d*)\\s*$");

	private final static Pattern reduceParen = Pattern.compile("\\(\\s*(\\w+\\d*)\\s*\\)");
	private final static Pattern not = Pattern.compile("\\s*NOT\\s+(\\w+\\d*)\\s*");
	private final static Pattern simpleGate = Pattern.compile("\\s*(\\w+\\d*)\\s+(AND|OR|NOR|NAND|XOR)\\s+(\\w+\\d*)\\s*");

	/**
	 *
	 */
	private Circuits()
	{
		//Utility class
	}

	public static Circuit<Boolean> buildBooleanCircuit(String description,
													   Map<String, Function<Collection<Boolean>, Boolean>> inputGates)
	{
		if(description.isEmpty()) return new Circuit<>();

		int counter = 0;
		Circuit<Boolean> c = new Circuit<>();
		Map<String, Function<Collection<Boolean>,Boolean>> gateNames = new HashMap<>();

		boolean failure = false;
		while(!failure)
		{
			failure = true;
			if(finish.matcher(description).matches()) return c;

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
				Function<Collection<Boolean>, Boolean> g = buildGate("NOT");
				gateNames.put("lit" + (counter), g);
				c.add(g);

				description = current.replaceFirst(" lit" + counter + " ");
				c.connect(gateNames.get("lit" + (counter++)), gateNames.get(notting));

				failure = false;
			}

			while((current = simpleGate.matcher(description)).find())
			{
				String a = current.group(1);
				String gate = current.group(2);
				String b = current.group(3);

				addIfInput(a, c, gateNames, inputGates);
				addIfInput(b, c, gateNames, inputGates);

				Function<Collection<Boolean>, Boolean> g = buildGate(gate);
				gateNames.put("lit" + counter, g);
				c.add(g);

				description = current.replaceFirst(" lit" + counter + " ");
				c.connect(gateNames.get("lit" + counter), gateNames.get(a));
				c.connect(gateNames.get("lit" + (counter++)), gateNames.get(b));

				failure = false;
			}

		}
		return null;
	}

	private static Function<Collection<Boolean>, Boolean> lambdaOf(final String gateName)
	{
		switch(gateName)
		{
			case "INPUT": return (e)->{throw new Error("Input value was not defined.");};
			case "AND": return (e)->e.stream().allMatch(Boolean::booleanValue);
			case "OR": return (e)->e.stream().anyMatch(Boolean::booleanValue);
			case "NOT": return (e)->!e.iterator().next();
			case "NOR": return (e)->e.stream().allMatch(b->!b);
			case "NAND": return (e)->e.stream().anyMatch(b->!b);
			case "XOR": return (e)->e.stream().skip(1).reduce(e.iterator().next(), Boolean::logicalXor);

			default: return null;
		}
	}

	private static Function<Collection<Boolean>, Boolean> buildGate(final String gateName)
	{
		return new Function<Collection<Boolean>, Boolean>()
		{
			@Override
			public Boolean apply(final Collection<Boolean> booleans)
			{
				return lambdaOf(gateName).apply(booleans);
			}
		};
	}

	public static void addIfInput(String name, Circuit<Boolean> c,
								  Map<String, Function<Collection<Boolean>, Boolean>> gates,
								  Map<String, Function<Collection<Boolean>, Boolean>> inputs)
	{
		if(!gates.containsKey(name))
		{
			Function<Collection<Boolean>, Boolean> g = buildGate("INPUT");
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
		System.out.println(buildGate("INPUT") == buildGate("INPUT"));
	}
}
