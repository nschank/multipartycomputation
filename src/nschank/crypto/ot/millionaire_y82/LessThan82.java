package nschank.crypto.ot.millionaire_y82;

import nschank.crypto.*;
import nschank.crypto.scheme.Scheme;
import nschank.crypto.scheme.Schemes;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.ot.millionaire_y82
 * Created on 27 Jun 2014
 * Last updated on 27 Jun 2014
 *
 * Performs Yao's protocol on two bytes (1-255) to determine if Alice has an i value which is strictly less than Bob's j
 * value.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class LessThan82 implements Protocol<Information<Boolean>>
{
	private static final Random randomness = new SecureRandom();
	private int N;
	private final Participant alice;
	private final Participant bob;
	private final Information<Byte> i;
	private final Information<Byte> j;

	/**
	 *
	 */
	public LessThan82(Participant Alice, Participant Bob, int N, Information<Byte> i, Information<Byte> j)
	{
		this.alice = Alice;
		this.bob = Bob;
		this.N = N;
		this.i = i;
		this.j = j;
	}

	/**
	 * Example protocol
	 */
	public static void main(String[] args) throws Exception
	{
		History h = new HistoryImpl();
		Participant alice = new HonestParticipant(h, "Alice");
		Participant bob = new HonestParticipant(h, "Bob");

		Information<Byte> i = alice.give((byte) 56, "how many million dollars she has", LearningType.CHOSEN, "i");
		Information<Byte> j = bob.give((byte) 90, "how many million dollars he has", LearningType.CHOSEN, "j");

		new LessThan82(alice, bob, 128, i, j).call();
		System.out.println(h);

		System.out.println("\n\n\n");

		for(String s : alice.getPersonalHistory())
			System.out.println(s);


		System.out.println("\n\n\n");


		for(String s : bob.getPersonalHistory())
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
	public Information<Boolean> call() throws Exception
	{
		Information<BigInteger> x = bob.give(BigInteger.probablePrime(N, randomness), "a random N-bit number",
											 LearningType.CHOSEN, "x");
		Scheme<BigInteger, BigInteger> scheme = Schemes.RSA(N);

		Information<Function<BigInteger, BigInteger>> enc = alice.give(scheme::encrypt, "an encryption scheme",
																	   LearningType.CREATED, "Ea");
		Information<Function<BigInteger, BigInteger>> dec = alice.give(scheme::decrypt, "the decryption scheme for Ea",
																	   LearningType.CALC, "Da");
		alice.send(enc, enc.getContext(alice), bob);
		Information<BigInteger> k = bob.give(enc.getValue().apply(x.getValue()), "the encryption of k under Ea",
											 LearningType.CALC, "k");
		Information<BigInteger> kj1 = bob.give(k.getValue().subtract(BigInteger.valueOf(j.getValue().longValue())).add(
				BigInteger.ONE), "k-j+1", LearningType.CALC);
		bob.send(kj1, "a random-looking number", alice);
		List<BigInteger> yua = new ArrayList<>(127);
		for(int u = 1; u <= 127; u++)
		{
			yua.add(dec.getValue().apply(kj1.getValue().add(BigInteger.valueOf(u - 1))));
		}
		Information<List<BigInteger>> yu = alice.give(yua, "the decryptions of k-j+1...k-j+127", LearningType.CALC,
													  "yu");
		Information<BigInteger> p = alice.give(BigInteger.probablePrime(N / 2, randomness), "a random N/2-bit prime",
											   LearningType.CHOSEN, "p");

		List<BigInteger> zua = new ArrayList<>(127);
		for(int u = 1; u <= 127; u++)
		{
			zua.add(yua.get(u - 1).add(u < i.getValue().intValue() ? BigInteger.ONE : BigInteger.ZERO).mod(
					p.getValue()));
		}
		Information<List<BigInteger>> zu = alice.give(zua, "yu+(u>=i) mod p", LearningType.CALC, "zu");
		alice.send(p, p.getContext(alice), bob);
		alice.send(zu, "a list of random-looking numbers mod p", bob);

		Information<BigInteger> xmodp = bob.give(x.getValue().mod(p.getValue()), "x mod p", LearningType.CALC);
		Information<Boolean> eq = bob.give(xmodp.getValue().equals(zua.get(j.getValue().intValue() - 1)),
										   "i < j (x ~ zj mod p)", LearningType.CALC);
		bob.send(eq, eq.getContext(bob), alice);

		return eq;
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return null;
	}
}
