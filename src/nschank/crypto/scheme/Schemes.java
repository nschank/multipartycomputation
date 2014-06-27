package nschank.crypto.scheme;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.function.Function;


/**
 * Created by Nicolas Schank for package nschank.crypto.scheme
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A Utility class for creating encryption/decryption schemes
 *
 * @author nschank, Brown University
 * @version 1.3.1
 */
public final class Schemes
{
	private static BigInteger TWO = new BigInteger("2");
	private static Random randomness = new SecureRandom();

	/**
	 *
	 */
	private Schemes()
	{
		//Utility class
	}

	public static Scheme<BigInteger, BigInteger> RSA(final int k)
	{
		BigInteger p = BigInteger.probablePrime(k / 2 + 1, randomness);
		BigInteger q;
		do q = BigInteger.probablePrime(k / 2 + k % 2, randomness); while(p.equals(q));

		final BigInteger n = p.multiply(q);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger et;
		do et = BigInteger.probablePrime(phi.bitLength() - 1, randomness); while(!phi.gcd(et).equals(BigInteger.ONE));
		final BigInteger e = et;

		final BigInteger d = e.modInverse(phi);

		return schemeBuilder((BigInteger b) -> b.modPow(e, n), (BigInteger b) -> b.modPow(d, n),
				"(n=" + n.toString() + ",e=" + e.toString() + ")", "(d=" + d.toString() + ")",
				(BigInteger b) -> b.testBit(1));
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{

	}

	public static <M, C> Scheme<M, C> schemeBuilder(final Function<M, C> E, final Function<C, M> D, final String pk,
													final String sk, final Function<M, Boolean> hardcore)
	{
		return new Scheme<M, C>()
		{
			@Override
			public C encrypt(final M m)
			{
				return E.apply(m);
			}

			@Override
			public M decrypt(final C c)
			{
				return D.apply(c);
			}

			@Override
			public String privateKey()
			{
				return sk;
			}

			@Override
			public String publicKey()
			{
				return pk;
			}

			@Override
			public boolean hardcoreBit(final M m)
			{
				return hardcore.apply(m);
			}
		};
	}
}
