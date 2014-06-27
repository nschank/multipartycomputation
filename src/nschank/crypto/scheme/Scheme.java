package nschank.crypto.scheme;

/**
 * Created by Nicolas Schank for package nschank.crypto.scheme
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A Scheme represents a way of encrypting and decrypting data
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public interface Scheme<MessageSpace, CipherSpace>
{
	public MessageSpace decrypt(CipherSpace c);
	public CipherSpace encrypt(MessageSpace m);
	public String privateKey();
	default public String publicKey()
	{
		return "no public key";
	}
}
