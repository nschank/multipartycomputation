package nschank.crypto.mpc.garbled_y86;

import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.mpc.garbled_y86
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * Turns any gate (that is, a Function from Booleans to a Boolean) into a Garbled Gate
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class GarbleGate implements Function<Function<Collection<Boolean>,Boolean>, Function<Collection<BigInteger>,BigInteger>>
{
	/**
	 *
	 */
	public GarbleGate()
	{

	}

	@Override
	public Function<Collection<BigInteger>, BigInteger> apply(
			final Function<Collection<Boolean>, Boolean> iterableBooleanFunction)
	{
		return null;
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{

	}
}
