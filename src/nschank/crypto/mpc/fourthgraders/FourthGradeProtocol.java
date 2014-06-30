//package nschank.crypto.mpc.fourthgraders;
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
//import java.security.SecureRandom;
//import java.util.*;
//
//
///**
// * Created by Nicolas Schank for package nschank.crypto.mpc.fourthgraders
// * Created on 29 Jun 2014
// * Last updated on 29 Jun 2014
// *
// * A Protocol (intended more for didactic use than practical use) which allows any group of people, each of whom has a
// * particular "crush", to each individually determine whether their crush is requited.
// *
// * @author nschank, Brown University
// * @version 1.1
// */
//public class FourthGradeProtocol implements Protocol<Map<Participant, Information<Boolean>>>
//{
//	private static final Random RANDOMNESS = new SecureRandom();
//
//	/*
//		Maps from a person to their crush
//	 */
//	private final Map<Participant, Information<Participant>> crushes;
//
//	/*
//		Security constraint
//	 */
//	private final int k;
//
//	/**
//	 * Creates a FourthGrade Protocol which has:
//	 * @param crushes
//	 * 		A map from participants to their own crushes
//	 * @param k
//	 * 		A security parameter
//	 */
//	public FourthGradeProtocol(final Map<Participant, Information<Participant>> crushes, final int k)
//	{
//		this.crushes = crushes;
//		this.k = k;
//	}
//
//
//	/**
//	 * Performs an example of the fourth grade protocol
//	 */
//	public static void main(String[] args) throws Exception
//	{
//		History h = new HistoryImpl();
//		List<Participant> parts = new ArrayList<>();
//
//		for(int i = 0; i < 40; i++)
//			parts.add(new HonestParticipant(h, "Participant" + (i + 1)));
//
//		final Map<Participant, Information<Participant>> crush = new HashMap<>();
//		for(int i = 0; i < 40; i++)
//		{
//			int mycrush;
//			do mycrush = (int) (Math.random() * 40); while(mycrush == i);
//			crush.put(parts.get(i), parts.get(i).give(parts.get(mycrush), "their crush", LearningType.CHOSEN, "rho"));
//		}
//		Map<Participant, Information<Boolean>> ret = new FourthGradeProtocol(crush, 128).call();
//
//		for(Participant p : parts)
//		{
//			System.out.println(p + "\tRequited: " + ret.get(p) + "\tCrush: " + crush.get(p));
//		}
//
//	}
//
//	/**
//	 * Broadcasts a piece of information to all parties.
//	 *
//	 * @param from
//	 * 		Who is broadcasting the info
//	 * @param info
//	 * 		The piece of information to broadcast
//	 * @param <T>
//	 * 		The type of information being sent
//	 */
//	private <T> void broadcast(Participant from, Information<T> info)
//	{
//		for(Participant p : this.getParticipants())
//			if(p != from) from.send(info, info.getContext(from), p);
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
//	public Map<Participant, Information<Boolean>> call() throws Exception
//	{
//		final Map<Participant, Information<String>> pk = new HashMap<>(),
//				sk = new HashMap<>(),
//				pik = new HashMap<>(),
//				sigmak = new HashMap<>();
//		final Map<Participant, Information<BigInteger>> r = new HashMap<>();
//
//		for(Participant p : this.crushes.keySet())
//		{
//			Scheme<BigInteger, BigInteger> pub = Schemes.RSA(3 * this.k);
//			Scheme<BigInteger, BigInteger> sec = Schemes.RSA(this.k);
//
//			pk.put(p, p.give(pub.publicKey(), "a public key", LearningType.CALC, "pk" + p.getName()));
//			sk.put(p, p.give(pub.privateKey(), "a private key", LearningType.CALC, "sk" + p.getName()));
//			pik.put(p, p.give(sec.publicKey(), "another public key", LearningType.CALC, "pik" + p.getName()));
//			sigmak.put(p, p.give(sec.privateKey(), "another private key", LearningType.CALC, "sigmak" + p.getName()));
//			r.put(p, p.give(BigInteger.probablePrime(this.k / 2, RANDOMNESS), "a random value", LearningType.CHOSEN,
//							"r" + p.getName()));
//
//			broadcast(p, pk.get(p));
//			broadcast(p, r.get(p));
//		}
//
//		final Map<Participant, Information<BigInteger>> c = new HashMap<>();
//		for(Participant p : this.crushes.keySet())
//		{
//			BigInteger x = new BigInteger(pik.get(p).getValue(), 2);
//			BigInteger send = Schemes.encryptRSA(pk.get(this.crushes.get(p).getValue()).toString(), this.k * 3).apply(
//					x);
//
//			c.put(p, p.give(send, "the encryption of pik under my crush's public key", LearningType.CALC,
//							"c" + p.getName()));
//			broadcast(p, c.get(p));
//		}
//
//		final Map<Participant, Information<String>> delta = new HashMap<>();
//		final Map<Participant, Information<BigInteger>> x = new HashMap<>();
//		for(Participant p : this.crushes.keySet())
//		{
//			delta.put(p, p.give(Schemes.decryptRSA(sk.get(p).getValue(), this.k * 3).apply(c.get(this.crushes.get(p)
//																										 .getValue())
//																								   .getValue())
//										.toString(2), "the decryption of crushes c under sk", LearningType.CALC,
//								"delta" + p.getName()));
//			x.put(p, p.give(Schemes.encryptRSA(delta.get(p).getValue(), this.k).apply(r.get(this.crushes.get(p)
//																									.getValue())
//																							  .getValue()),
//							"the encryption of crushes rp under delta", LearningType.CALC, "x" + p.getName()));
//			broadcast(p, x.get(p));
//		}
//
//		final Map<Participant, Information<BigInteger>> s = new HashMap<>();
//		final Map<Participant, Information<Boolean>> requited = new HashMap<>();
//		for(Participant p : this.crushes.keySet())
//		{
//			s.put(p, p.give(Schemes.decryptRSA(sigmak.get(p).getValue(), this.k).apply(x.get(this.crushes.get(p)
//																									 .getValue())
//																							   .getValue()),
//							"the decryption of crushes x under sigma", LearningType.CALC, "s" + p.getName()));
//			requited.put(p, p.give(s.get(p).getValue().equals(r.get(p).getValue()), "whether my crush is requited",
//								   LearningType.CALC));
//		}
//
//		return requited;
//	}
//
//	@Override
//	public Set<Participant> getParticipants()
//	{
//		return Collections.unmodifiableSet(crushes.keySet());
//	}
//}
