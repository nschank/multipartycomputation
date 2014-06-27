package nschank.crypto.ot.oneoftwo_gmw87;

import nschank.crypto.*;
import nschank.crypto.scheme.Scheme;
import nschank.crypto.scheme.Schemes;

import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.ot.oneoftwo_gmw87
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * Equality not defined as specified by Protocol.
 *
 * Return value is the key under which Bob learned b sub alpha
 *
 * @author nschank, Brown University
 * @version 1.4
 */
public class OneOfTwoOT87 implements Protocol<String>
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
	public OneOfTwoOT87(Participant Alice, Participant Bob, String b0key, String b1key, String alphakey, int k)
	{
		this.b0key = b0key;
		this.b1key = b1key;
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

		BigInteger x_0 = BigInteger.probablePrime(128, randomness);
		BigInteger x_1 = BigInteger.probablePrime(128, randomness);

		byte[] x0 = x_0.toByteArray();
		byte[] x1 = x_1.toByteArray();
		boolean[] balpha = new boolean[8 * x0.length];
		String alpha = Bob.give(true, " alpha");
		for(int i = 0; i < x0.length; i++)
		{
			for(int j = 0; j < 8; j++)
			{
				boolean b_0 = (x0[i] & (1 << j)) != 0;
				boolean b_1 = (x1[i] & (1 << j)) != 0;

				Protocol<String> ot = new OneOfTwoOT87(Alice, Bob, Alice.give(b_0, " b_0"), Alice.give(b_1, " b_1"),
						alpha, 32);
				String balphakey = ot.call();
				balpha[i * 8 + 7 - j] = (Boolean) Bob.get(balphakey);
			}
		}

		System.out.println("x was: " + x_1);
		BigInteger b = new BigInteger("0");
		for(int i = 0; i < 8 * x0.length; i++)
			if(balpha[i]) b = b.setBit(8 * x0.length - i - 1);
		System.out.println("x  is: " + b);
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
		boolean b0, b1, alpha;
		b0 = (Boolean) Alice.get(b0key);
		b1 = (Boolean) Alice.get(b1key);
		alpha = (Boolean) Bob.get(alphakey);

		Scheme<BigInteger, BigInteger> permutation = Schemes.RSA(this.k);

		String _alice1 = Alice
				.give(permutation, " (f,f^1, B_f), a trapdoor permutation scheme from [0,2^k-1]->[0,2^k-1]");
		Bob.getHistory().add("Alice sends Bob the one-way permutation and its hardcore predicate", this.participants);
		String _bob1 = Bob.give((Function<BigInteger, BigInteger>) permutation::encrypt, " a one-way permutation f");
		String _bob4 = Bob.give((Function<BigInteger, Boolean>) permutation::hardcoreBit, " a hardcore predicate B_f");

		BigInteger x0 = BigInteger.probablePrime(k - 1, randomness);
		BigInteger x1 = BigInteger.probablePrime(k - 1, randomness);
		boolean hc = permutation.hardcoreBit(alpha ? x1 : x0);

		String _bob2 = Bob.give(x0, " a random value in the function's domain, x_0");
		String _bob3 = Bob.give(x1, " another random value in the function's domain, x_1");

		BigInteger fxalpha = permutation.encrypt(alpha ? x1 : x0);
		String _bob5 = Bob.give(fxalpha, " the encryption of x_" + (alpha ? "1, " + _bob3 : "0, " + _bob2));

		BigInteger u = alpha ? x0 : fxalpha;
		BigInteger v = alpha ? fxalpha : x1;

		Bob.getHistory().add("Bob sends Alice " + (alpha ? _bob2 : _bob5), this.participants);
		String _alice2 = Alice.give(u, " a random-looking number within the permutation's domain, u");
		Bob.getHistory().add("Bob sends Alice " + (alpha ? _bob5 : _bob3), this.participants);
		String _alice3 = Alice.give(v, " another random-looking number within the permutation's domain, v");

		boolean c0 = permutation.hardcoreBit(permutation.decrypt(u));
		boolean c1 = permutation.hardcoreBit(permutation.decrypt(v));
		String _alice4 = Alice.give(c0, " c0, the hardcore bit of the decryption of " + _alice2);
		String _alice5 = Alice.give(c1, " c1, the hardcore bit of the decryption of " + _alice3);

		boolean d0 = c0 != b0;
		boolean d1 = c1 != b1;
		Alice.getHistory().add("Alice sends Bob d_0, c0=" + _alice4 + " xored with b_0=" + b0key);
		String _bob6 = Bob
				.give(d0, alpha ? " a random-looking number" : " the hardcore bit of " + _bob2 + " xored with b_0");
		Alice.getHistory().add("Alice sends Bob d_1, c1=" + _alice5 + " xored with b_1=" + b1key);
		String _bob7 = Bob
				.give(d1, !alpha ? " a random-looking number" : " the hardcore bit of " + _bob3 + " xored with b_1");

		String _bob8 = Bob.give(hc, " the hardcore bit of " + (alpha ? _bob3 : _bob2));

		Alice.forget(_alice1, _alice2, _alice3, _alice4, _alice5);
		Bob.forget(_bob1, _bob2, _bob3, _bob4, _bob5, _bob6, _bob7, _bob8);

		return Bob.give((alpha ? d1 != hc : d0 != hc), " b_alpha=" + (alpha ? b1key : b0key));
	}

	@Override
	public Set<Participant> getParticipants()
	{
		return Collections.unmodifiableSet(participants);
	}

	/**
	 * @return A String representation of this OneOfTwoOT87
	 */
	@Override
	public String toString()
	{
		throw new UnsupportedOperationException("toString not yet implemented");
	}
}
