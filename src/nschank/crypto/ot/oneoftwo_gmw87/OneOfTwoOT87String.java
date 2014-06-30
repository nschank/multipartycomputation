//package nschank.crypto.ot.oneoftwo_gmw87;
//
//import nschank.crypto.*;
//import nschank.crypto.history.History;
//import nschank.crypto.history.HistoryImpl;
//import nschank.crypto.history.LearningType;
//import nschank.crypto.player.HonestParticipant;
//import nschank.crypto.player.Participant;
//import nschank.crypto.protocol.Protocol;
//
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.Set;
//
//
///**
// * Created by Nicolas Schank for package nschank.crypto.ot.oneoftwo_gmw87
// * Created on 27 Jun 2014
// * Last updated on 27 Jun 2014
// *
// * @author nschank, Brown University
// * @version 1.1
// */
//public class OneOfTwoOT87String implements Protocol<Information<String>>
//{
//
//	private final Participant Alice;
//	private final Participant Bob;
//	private final Information<Boolean> alpha;
//	private final Information<String> b0;
//	private final Information<String> b1;
//	private final int k;
//	private final Set<Participant> participants;
//
//	/**
//	 *
//	 */
//	public OneOfTwoOT87String(Participant Alice, Participant Bob, int k, Information<String> b0, Information<String> b1,
//							  Information<Boolean> alpha)
//	{
//		this.b0 = b0;
//		this.b1 = b1;
//		this.alpha = alpha;
//		this.participants = new HashSet<>(2);
//		this.participants.add(Alice);
//		this.participants.add(Bob);
//		this.Alice = Alice;
//		this.Bob = Bob;
//
//		this.k = k;
//	}
//
//	/**
//	 *
//	 */
//	public static void main(String[] args) throws Exception
//	{
//		History h = new HistoryImpl();
//		Participant Alice = new HonestParticipant(h, "Alice");
//		Participant Bob = new HonestParticipant(h, "Bob");
//
//		Protocol<Information<String>> x = new OneOfTwoOT87String(Alice, Bob, 32, Alice.give("In the courtyard|",
//																							"a String",
//																							LearningType.CHOSEN, "s0"),
//																 Alice.give("By the dining room table|", "a String",
//																			LearningType.CHOSEN, "s1"), Bob.give(false,
//																												 "which String he wants",
//																												 LearningType.CHOSEN,
//																												 "alpha"));
//		x.call();
//
//		for(String s : h.eventsVisibleTo(Alice))
//			System.out.println(s);
//		System.out.println("\n\n\n");
//		for(String s : h.eventsVisibleTo(Bob))
//			System.out.println(s);
//	}
//
//	/**
//	 * Computes a result, or throws an exception if unable to do so.
//	 *
//	 * @return computed result
//	 *
//	 * @throws Exception
//	 * 		if unable to compute a result
//	 */
//	@Override
//	public Information<String> call() throws Exception
//	{
//		String x0 = b0.getValue();
//		String x1 = b1.getValue();
//
//		byte[] _b0 = x0.getBytes();
//		byte[] _b1 = x1.getBytes();
//
//		Information[] xalphab = new Information[Math.max(_b0.length, _b1.length)];
//		byte[] tostr = new byte[xalphab.length];
//
//		for(int j = 0; j < xalphab.length; j++)
//		{
//			byte b_0 = j >= _b0.length ? (byte) j : _b0[j];
//			byte b_1 = j >= _b1.length ? (byte) j : _b1[j];
//
//			Alice.getHistory().add("Alice and Bob perform the OneOfTwoOT Protocol for one byte", this.participants);
//			Information<Byte> _alice0 = Alice.give(b_0, "byte " + j + " of s0", LearningType.CALC, "B0");
//			Information<Byte> _alice1 = Alice.give(b_1, "byte " + j + " of s1", LearningType.CALC, "B1");
//			Alice.getHistory().tabUp();
//			Protocol<Information<Byte>> ot = new OneOfTwoOT87Byte(Alice, Bob, k, _alice0, _alice1, alpha);
//			xalphab[j] = ot.call();
//			tostr[j] = (Byte) xalphab[j].getValue();
//			Alice.forget(_alice0, _alice1);
//			Alice.getHistory().tabDown();
//		}
//
//
//		String s = new String(tostr);
//		Information<String> _ret = Bob.give(s, "the concatenation of the previously calculated bytes",
//											LearningType.CALC, "s");
//		Bob.forget(xalphab);
//		return _ret;
//	}
//
//	@Override
//	public Set<Participant> getParticipants()
//	{
//		return Collections.unmodifiableSet(this.participants);
//	}
//
//}
