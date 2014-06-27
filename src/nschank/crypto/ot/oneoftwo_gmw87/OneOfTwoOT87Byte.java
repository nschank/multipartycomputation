package nschank.crypto.ot.oneoftwo_gmw87;

import nschank.crypto.*;

import java.util.Collections;
import java.util.HashSet;
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
 * @version 2.1
 */
public class OneOfTwoOT87Byte implements Protocol<Information<Byte>>
{
	private final Participant Alice;
	private final Participant Bob;
	private final Information<Boolean> alpha;
	private final Information<Byte> b0;
	private final Information<Byte> b1;
	private final int k;
	private final Set<Participant> participants;

	/**
	 *
	 */
	public OneOfTwoOT87Byte(Participant Alice, Participant Bob, int k, Information<Byte> b0, Information<Byte> b1,
							Information<Boolean> alpha)
	{
		this.b0 = b0;
		this.b1 = b1;
		this.alpha = alpha;
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

		Protocol<Information<Byte>> x = new OneOfTwoOT87Byte(Alice, Bob, 32, Alice.give((byte) 122, "a random byte",
																						LearningType.CHOSEN, "x0"),
															 Alice.give((byte) 44, "a random byte", LearningType.CHOSEN,
																		"x1"), Bob.give(true, "which byte he wants",
																						LearningType.CHOSEN, "alpha"));
		x.call();
		for(String s : h.eventsVisibleTo(Bob))
			System.out.println(s);
		System.out.println("\n\n\n");
		for(String s : h.eventsVisibleTo(Alice))
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
	public Information<Byte> call() throws Exception
	{
		byte x0 = b0.getValue();
		byte x1 = b1.getValue();
		Information[] xalphab = new Information[8];

		for(int j = 0; j < 8; j++)
		{
			boolean b_0 = (x0 & (1 << j)) != 0;
			boolean b_1 = (x1 & (1 << j)) != 0;
			Alice.getHistory().add("Alice and Bob perform the OneOfTwoOT Protocol for one bit", this.participants);
			Information<Boolean> _alice0 = Alice.give(b_0, "bit " + (8 - j) + " of x0", LearningType.CALC, "b0");
			Information<Boolean> _alice1 = Alice.give(b_1, "bit " + (8 - j) + " of x1", LearningType.CALC, "b1");
			Alice.getHistory().tabUp();
			Protocol<Information<Boolean>> ot = new OneOfTwoOT87(Alice, Bob, k, _alice0, _alice1, alpha);
			xalphab[j] = ot.call();
			Alice.forget(_alice0, _alice1);
			Alice.getHistory().tabDown();
		}

		byte b = 0;
		for(int i = 0; i < 8; i++)
			if((Boolean) xalphab[i].getValue()) b |= (1 << i);
		Information<Byte> _ret = Bob.give(b, "the concatenation of the previous 8 b_alphas", LearningType.CALC,
										  "xalpha");
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
