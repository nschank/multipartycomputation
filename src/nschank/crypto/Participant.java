package nschank.crypto;

/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A Participant in a protocol (e.g. Alice or Bob). Has a private input, the set of all other participants, a Protocol
 * that it is running, the history
 *
 * @author nschank, Brown University
 * @version 1.3
 */
public interface Participant
{
	public boolean containsKey(String informationKey);
	public boolean forget(String... informationKey);
	public Object get(String informationKey);
	public History getHistory();
	public String getKey();
	default public Iterable<String> getPersonalHistory()
	{
		return this.getHistory().eventsVisibleTo(this);
	}
	default public String give(Object information)
	{
		return this.give(information, information.toString());
	}
	public String give(Object information, String description);
}
