package nschank.crypto.ot.oneoftwo_gmw87;

import nschank.crypto.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by Nicolas Schank for package nschank.crypto.ot.oneoftwo_gmw87
 * Created on 27 Jun 2014
 * Last updated on 27 Jun 2014
 *
 * A one-of-two OT implementation that transfers a byte, using
 * OneOfTwoOT87 as a primitive.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class OneOfTwoOT87Byte implements Protocol<String>
{
	private static final Random randomness = new Random();
	private final Participant Alice;
	private final Participant Bob;
	private final String alphakey;
	private final String b0key;
	private final String b1key;
	private final int k;
	private final Set<Participant> participants;

	/**
	 *
	 */
	public OneOfTwoOT87Byte(Participant Alice, Participant Bob, String x0key, String x1key, String alphakey, int k)
	{
		this.b0key = x0key;
		this.b1key = x1key;
		this.alphakey = alphakey;
		this.participants = new HashSet<>(2);
		this.participants.add(Alice);
		this.participants.add(Bob);
		this.Alice = Alice;
		this.Bob = Bob;

		this.k = k;
	}

	/**
	 *
	 */
	public static void main(String[] args) throws Exception
	{
		History h = new HistoryImpl();
		Participant Alice = new HonestParticipant(h, "Alice");
		Participant Bob = new HonestParticipant(h, "Bob");

		Protocol<String> x = new OneOfTwoOT87Byte(Alice, Bob, Alice.give((byte) 122, " x_0"),
				Alice.give((byte) 24, " x_1"), Bob.give(false, " alpha"), 32);
		System.out.println(Bob.get(x.call()));
		for(String s : h.eventsVisibleTo(Bob))
			System.out.println(s);
	}

	/**
	 * Computes a result, or throws an exception if unable to do so.
	 *
	 * @return computed result
	 *
	 * @throws Exception
	 * 		if unable to compute a result
	 */
	@Override
	public String call() throws Exception
	{
		byte x0 = (byte) Alice.get(b0key);
		byte x1 = (byte) Alice.get(b1key);
		String[] xalphab = new String[8];
		Alice.getHistory()
			 .add("Alice and Bob perform 8 runs of the 1-of-2 OT protocol, one bit of " + b0key + " and " + b1key
					 + " at a time.");
		for(int j = 0; j < 8; j++)
		{

			boolean b_0 = (x0 & (1 << j)) != 0;
			boolean b_1 = (x1 & (1 << j)) != 0;
			String _alice0 = Alice.give(b_0, " b_0");
			String _alice1 = Alice.give(b_1, " b_1");

			Protocol<String> ot = new OneOfTwoOT87(Alice, Bob, _alice0, _alice1, alphakey, k);
			xalphab[j] = ot.call();
			Alice.forget(_alice0, _alice1);
		}

		byte b = 0;
		for(int i = 0; i < 8; i++)
			if((Boolean) Bob.get(xalphab[i])) b |= (1 << i);
		String _ret = Bob.give(b, "x_alpha, the concatenation of the previous 8 b_alphas");
		Bob.forget(xalphab);
		return _ret;
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return Collections.unmodifiableSet(this.participants);
	}

	/**
	 * @return A String representation of this OneOfTwoOT87Int
	 */
	@Override
	public String toString()
	{
		throw new UnsupportedOperationException("toString not yet implemented");
	}
}
