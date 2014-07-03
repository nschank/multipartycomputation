package nschank.crypto.scheme;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;


/**
 * Created by Nicolas Schank for package nschank.crypto.scheme
 * Created on 02 Jul 2014 using code from Scheme v1.3.2
 * Last updated on 02 Jul 2014
 *
 * An implementation of the RSAScheme, a permutation within BigIntegers.
 *
 * Does have a hardcore predicate: the bit value of the bit in the 2's position. Does not use the bit in the 1's position
 * so that the predicate is still hardcore if the messagespace is from (likely) primes only.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class RSAScheme implements EncryptionScheme<BigInteger, BigInteger>
{
	private final BigInteger d;
	private final BigInteger e;
	private final BigInteger n;
	private final String privateKey;
	private final String publicKey;
	private final int securityParameter;

	/**
	 * Creates an RSAScheme with the given security parameter. The RSA's public modulus is guaranteed to be of a bit
	 * length identical to the security parameter. Uses a new SecureRandom().
	 *
	 * @param securityParameter
	 * 		The required bit length of the public modulus.
	 */
	public RSAScheme(final int securityParameter)
	{
		this(securityParameter, new SecureRandom());
	}

	/**
	 * @param securityParameter
	 * 		The required bit length of the public modulus.
	 * @param randomness
	 * 		The source of randomness to use to create this scheme
	 */
	public RSAScheme(final int securityParameter, final Random randomness)
	{
		this.securityParameter = securityParameter;

		BigInteger p, q, nt;

		do
		{
			p = BigInteger.probablePrime(securityParameter / 2, randomness);
			do q = BigInteger.probablePrime(securityParameter / 2 + securityParameter % 2, randomness); while(p
					.equals(q));
		} while((nt = p.multiply(q)).bitLength() != securityParameter);
		this.n = nt;

		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		final int eBitLength = phi.toString(2).length() - 1;

		BigInteger et;
		do et = BigInteger.probablePrime(eBitLength, randomness); while(!phi.gcd(et).equals(BigInteger.ONE));
		this.e = et;

		this.d = e.modInverse(phi);

		final String mod = this.n.toString(2);
		this.publicKey = mod + this.e.toString(2);
		this.privateKey = mod + this.d.toString(2);
	}

	/**
	 * Decrypts a value using only a private key and the security parameter used to build it
	 *
	 * @param securityParameter
	 * 		The security parameter used to build the public key
	 * @param privateKey
	 * 		The private key of the Scheme used to encrypt
	 * @param c
	 * 		The encryption of some value {@code m}
	 *
	 * @return The value {@code m}
	 */
	public static BigInteger decrypt(final int securityParameter, final String privateKey, final BigInteger c)
	{
		final BigInteger n = new BigInteger(privateKey.substring(0, securityParameter), 2);
		final BigInteger d = new BigInteger(privateKey.substring(securityParameter), 2);

		return c.modPow(d, n);
	}

	/**
	 * Encrypts a value using only a public key and the security parameter used to build it
	 *
	 * @param securityParameter
	 * 		The security parameter used to build the public key
	 * @param publicKey
	 * 		The public key of the Scheme used to encrypt
	 * @param m
	 * 		The number to encrypt
	 *
	 * @return The encryption of {@code m}
	 */
	public static BigInteger encrypt(final int securityParameter, final String publicKey, final BigInteger m)
	{
		final BigInteger n = new BigInteger(publicKey.substring(0, securityParameter), 2);
		final BigInteger e = new BigInteger(publicKey.substring(securityParameter), 2);

		return m.modPow(e, n);
	}

	/**
	 * Decrypt the given number under RSA, using n and d
	 *
	 * @param c
	 * 		The number to decrypt
	 *
	 * @return c^d mod n
	 */
	@Override
	public BigInteger decrypt(final BigInteger c)
	{
		return c.modPow(this.d, this.n);
	}

	/**
	 * Encrypts the given number under RSA, using n and e
	 *
	 * @param m
	 * 		The number to encrypt
	 *
	 * @return c^e mod n
	 */
	@Override
	public BigInteger encrypt(final BigInteger m)
	{
		return m.modPow(this.e, this.n);
	}

	/**
	 * Two RSA Schemes are equal if their n, e, and d values are all equal.
	 *
	 * @param o
	 * 		The object with which to compare equality
	 *
	 * @return Whether these objects are considered equal
	 */
	@Override
	public boolean equals(Object o)
	{
		if(o == this) return true;
		if(o == null) return false;
		if(o.getClass() != this.getClass()) return false;
		RSAScheme conv = (RSAScheme) o;
		return conv.n.equals(this.n) && conv.e.equals(this.e) && conv.d.equals(this.d);
	}

	/**
	 * All input bits of a message are hardcore in RSA. In particular, this one tests the second bit of messages.
	 *
	 * @param m
	 * 		Any BigInteger
	 *
	 * @return Whether the second bit of {@code m} is set.
	 */
	@Override
	public boolean hardcoreBit(final BigInteger m)
	{
		return m.testBit(1);
	}

	/**
	 * The bitwise XORs of the hashcodes of n, e, and d.
	 *
	 * @return A hash code representing this object
	 */
	@Override
	public int hashCode()
	{
		return this.n.hashCode() ^ this.e.hashCode() ^ this.d.hashCode();
	}

	/**
	 * The private key under RSA is the k-bit public modulus followed by the value d, entirely binary.
	 *
	 * @return the private key of this RSA scheme
	 */
	@Override
	public String privateKey()
	{
		return this.privateKey;
	}

	/**
	 * The public key under RSA is the k-bit public modulus followed by the value e, entirely in binary.
	 *
	 * @return the public key of this RSA scheme
	 */
	@Override
	public String publicKey()
	{
		return this.publicKey;
	}

	/**
	 * @return the security parameter used to create this RSA scheme
	 */
	@Override
	public int securityParameter()
	{
		return this.securityParameter;
	}

	/**
	 * @return A String representation of this RSAScheme
	 */
	@Override
	public String toString()
	{
		return "(" + this.publicKey() + "," + this.privateKey() + ")";
	}
}
