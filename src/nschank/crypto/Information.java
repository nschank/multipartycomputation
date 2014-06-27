package nschank.crypto;

/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 27 Jun 2014
 * Last updated on 27 Jun 2014
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public interface Information<T>
{
	default public String getContext(Participant participant)
	{
		return "an unknown value";
	}
	public String getName();
	public T getValue();
	public void giveContext(String context, Participant... participant);

}
