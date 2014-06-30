//package nschank.crypto.ot.oneoftwo_gmw87;
//
//import nschank.crypto.*;
//import nschank.crypto.history.History;
//import nschank.crypto.history.HistoryImpl;
//import nschank.crypto.history.LearningType;
//import nschank.crypto.player.HonestParticipant;
//import nschank.crypto.player.Participant;
//import nschank.crypto.protocol.Protocol;
//import nschank.crypto.scheme.Scheme;
//import nschank.crypto.scheme.Schemes;
//
//import java.math.BigInteger;
//import java.util.*;
//import java.util.function.Function;
//
//
///**
// * Created by Nicolas Schank for package nschank.crypto.ot.oneoftwo_gmw87
// * Created on 26 Jun 2014
// * Last updated on 26 Jun 2014
// *
// * Equality not defined as specified by Protocol.
// *
// * Return value is the key under which Bob learned b sub alpha
// *
// * @author nschank, Brown University
// * @version 2.1
// */
//public class OneOfTwoOT87 implements Protocol<Information<Boolean>>
//{
//	private static final Random randomness = new Random();
//	private final Participant alice;
//	private final Information<Boolean> alpha;
//	private final Information<Boolean> b0;
//	private final Information<Boolean> b1;
//	private final Participant bob;
//	private final int k;
//	private final Set<Participant> participants;
//
//	/**
//	 *
//	 */
//	public OneOfTwoOT87(Participant Alice, Participant Bob, int k, Information<Boolean> b0, Information<Boolean> b1,
//						Information<Boolean> alpha)
//	{
//		this.b0 = b0;
//		this.b1 = b1;
//		this.alpha = alpha;
//		this.participants = new HashSet<>(2);
//		this.participants.add(Alice);
//		this.participants.add(Bob);
//		this.alice = Alice;
//		this.bob = Bob;
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
//		BigInteger x_0 = BigInteger.probablePrime(7, randomness);
//		BigInteger x_1 = BigInteger.probablePrime(7, randomness);
//
//		Alice.give(x_0, "a random integer", LearningType.CHOSEN, "x0");
//		Alice.give(x_1, "a random integer", LearningType.CHOSEN, "x1");
//
//		byte[] x0 = x_0.toByteArray();
//		byte[] x1 = x_1.toByteArray();
//		Information[] balpha = new Information[8 * x0.length];
//		Information<Boolean> alpha = Bob.give(true, "the number bob would like to have", LearningType.CHOSEN, "alpha");
//		for(int i = 0; i < x0.length; i++)
//		{
//			for(int j = 0; j < 8; j++)
//			{
//				boolean b_0 = (x0[i] & (1 << j)) != 0;
//				boolean b_1 = (x1[i] & (1 << j)) != 0;
//
//				Information<Boolean> b0 = Alice.give(b_0, "bit " + (i * 8 + 8 - j) + " of x0", LearningType.CALC, "b0");
//				Information<Boolean> b1 = Alice.give(b_1, "bit " + (i * 8 + 8 - j) + " of x1 ", LearningType.CALC,
//													 "b1");
//
//				h.add("Alice and Bob perform the OneOfTwoOT Protocol for one bit");
//				h.tabUp();
//				Protocol<Information<Boolean>> ot = new OneOfTwoOT87(Alice, Bob, 32, b0, b1, alpha);
//				balpha[i * 8 + 7 - j] = ot.call();
//				Alice.forget(b0, b1);
//				h.tabDown();
//			}
//		}
//
//		BigInteger b = new BigInteger("0");
//		for(int i = 0; i < 8 * x0.length; i++)
//			if((Boolean) balpha[i].getValue()) b = b.setBit(8 * x0.length - i - 1);
//		Bob.give(b, "his set of bits in order, as one number", LearningType.CALC, "xalpha");
//		Bob.forget(balpha);
//		System.out.println(h);
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
//	public Information<Boolean> call() throws Exception
//	{
//		Scheme<BigInteger, BigInteger> permutation = Schemes.RSA(this.k);
//		Information<Function<BigInteger, BigInteger>> enc = alice.give(permutation::encrypt, "a trapdoor permutation",
//																	   LearningType.CREATED, "f");
//		Information<Function<BigInteger, BigInteger>> dec = alice.give(permutation::decrypt, "the trapdoor of f",
//																	   LearningType.CALC, "f^-1");
//		Information<Function<BigInteger, Boolean>> Bf = alice.give(permutation::hardcoreBit,
//																   "a hardcore predicate of f", LearningType.CHOSEN,
//																   "B_F");
//
//		alice.send(enc, enc.getContext(alice), bob);
//		alice.send(Bf, Bf.getContext(alice), bob);
//
//		Information<BigInteger> r0 = bob.give(BigInteger.probablePrime(k - 1, randomness),
//											  "a random value in f's domain", LearningType.CHOSEN, "r0");
//		Information<BigInteger> r1 = bob.give(BigInteger.probablePrime(k - 1, randomness),
//											  "a random value in f's domain", LearningType.CHOSEN, "r1");
//
//		Information<BigInteger> ralpha = !alpha.getValue() ? r0 : r1;
//		Information<BigInteger> fralpha = bob.give(enc.getValue().apply(ralpha.getValue()),
//												   "the encryption of " + ralpha.getValue(), LearningType.CALC,
//												   "f(ralpha)");
//		bob.forget(enc);
//
//		Information<BigInteger> u, v;
//
//		if(!alpha.getValue())
//		{
//			u = bob.send(fralpha, "a random looking value", alice);
//			v = bob.send(r1, "a random looking value", alice);
//			bob.forget(r1, fralpha);
//		} else
//		{
//			u = bob.send(r0, "a random looking value", alice);
//			v = bob.send(fralpha, "a random looking value", alice);
//			bob.forget(r0, fralpha);
//		}
//
//		Information<Boolean> c0 = alice.give(Bf.getValue().apply(dec.getValue().apply(u.getValue())),
//											 "the hardcore bit of f^-1(" + u.getName() + ")", LearningType.CALC, "c0");
//		Information<Boolean> c1 = alice.give(Bf.getValue().apply(dec.getValue().apply(v.getValue())),
//											 "the hardcore bit of f^-1(" + v.getName() + ")", LearningType.CALC, "c1");
//		alice.forget(enc, dec, Bf);
//
//		Information<Boolean> d0 = alice.give(!c0.getValue().equals(b0.getValue()), "c0 XOR " + b0.getName(),
//											 LearningType.CALC, "d0");
//		Information<Boolean> d1 = alice.give(!c1.getValue().equals(b1.getValue()), "c1 XOR " + b1.getName(),
//											 LearningType.CALC, "d1");
//		alice.forget(c0, c1);
//
//		Information<Boolean> dalpha;
//		if(!alpha.getValue())
//		{
//			dalpha = alice.send(d0, "the hardcore bit of r0 XOR b0", bob);
//			alice.send(d1, "a random looking value", bob);
//			alice.forget(d0, d1);
//			bob.forget(d1);
//		} else
//		{
//			alice.send(d0, "a random looking value", bob);
//			dalpha = alice.send(d1, "the hardcore bit of r1 XOR b1", bob);
//			alice.forget(d0, d1);
//			bob.forget(d0);
//		}
//
//		Information<Boolean> hc = bob.give(Bf.getValue().apply(ralpha.getValue()), "the hardcore bit of ralpha",
//										   LearningType.CALC);
//		bob.forget(Bf);
//		Information<Boolean> balpha = bob.give(!hc.getValue().equals(dalpha.getValue()), hc.getName() + " XOR dalpha",
//											   LearningType.CALC, "balpha");
//		bob.forget(fralpha, hc, r0, r1, dalpha);
//
//		return balpha;
//	}
//
//	@Override
//	public Set<Participant> getParticipants()
//	{
//		return Collections.unmodifiableSet(participants);
//	}
//
//	/**
//	 * @return A String representation of this OneOfTwoOT87
//	 */
//	@Override
//	public String toString()
//	{
//		throw new UnsupportedOperationException("toString not yet implemented");
//	}
//}
