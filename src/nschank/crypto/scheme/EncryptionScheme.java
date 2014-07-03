package nschank.crypto.scheme;

/**
 * Created by Nicolas Schank for package nschank.crypto.scheme
 * Created on 26 Jun 2014
 * Renamed from Scheme to EncryptionScheme on 2 Jul 2014
 * Last updated on 2 Jul 2014
 *
 * A Scheme represents a way of encrypting and decrypting data. It must include a method which encrypts any element within
 * its message space, and one that decrypts any element within its cipher space. It may also optionally have a hardcore
 * predicate; if one is not implemented, then every element of the message space will return {@literal true}.
 *
 * Most Schemes are generally expected to have at least a private key, if not also a public key. It is generally
 * recommended as well that a Scheme be buildable from the private (and, if applicable, public) key(s). Further, using
 * the public key, one should be able to encrypt any message; the same is true with the private key and decryption.
 *
 * @param <MessageSpace>
 * 		The type that is possible to encrypt
 * @param <CipherSpace>
 * 		The type that MessageSpace is mapped into using this encryption scheme
 *
 * @author nschank, Brown University
 * @version 1.3
 */
public interface EncryptionScheme<MessageSpace, CipherSpace>
{
	/**
	 * Decrypts a message {@code c} into its preimage. Should guarantee that using the output of decrypt on encrypt is
	 * the identity function (or something equivalent, e.g. the identity modulo the size of the MessageSpace)
	 *
	 * @param c
	 * 		Any value within CipherSpace
	 *
	 * @return A value within MessageSpace which maps to that value
	 */
	public MessageSpace decrypt(CipherSpace c);
	/**
	 * Encrypts a message {@code m} into its image. Should guarantee that calling decrypt on the output of this function
	 * should be the identity function (or something equivalent, e.g. the identity modulo the size of the MessageSpace)
	 *
	 * @param m
	 * 		Any value in the MessageSpace
	 *
	 * @return A value in the CipherSpace
	 */
	public CipherSpace encrypt(MessageSpace m);
	/**
	 * If implemented, must return a single bit (as a boolean) easy to determine from {@code m}, but hard to determine from
	 * {@code encrypt(m)}.
	 *
	 * If not implemented, always returns true.
	 *
	 * @param m
	 * 		Any value in the MessageSpace
	 *
	 * @return A hardcore bit for that value
	 */
	default public boolean hardcoreBit(MessageSpace m)
	{
		return true;
	}
	/**
	 * @return The private key defined for this Scheme
	 */
	public String privateKey();
	/**
	 * @return The public key defined for this Scheme
	 */
	public String publicKey();
	/**
	 * @return The security parameter used to create this Scheme. Generally has a very specific relationship
	 * with the length of the public and private keys.
	 */
	public int securityParameter();
}
