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
 * @version 2.1
 */
public interface Participant
{
	public boolean forget(Information... infs);
	public History getHistory();
	default public Iterable<String> getPersonalHistory()
	{
		return this.getHistory().eventsVisibleTo(this);
	}
	public <T> Information<T> give(Information<T> information, LearningType learnedBy);
	public <T> Information<T> give(T object, String context, LearningType learnedBy);
	public <T> Information<T> give(T object, String context, LearningType learnedBy, String name);
	default public <T> Information<T> send(Information<T> information, Participant... to)
	{
		for(Participant p : to)
		{
			this.getHistory().add(this.toString() + " sent " + p.toString() + " " + information.getContext(this) + ", "
										  + information.getName(), this);
			p.give(information, LearningType.GIVEN);
		}
		return information;
	}
	default public <T> Information<T> send(Information<T> information, String context, Participant... to)
	{
		information.giveContext(context, to);
		for(Participant p : to)
		{
			this.getHistory().add(this.toString() + " sent " + p.toString() + " " + information.getContext(this) + ", "
										  + information.getName(), this);
			p.give(information, LearningType.GIVEN);
		}
		return information;
	}
}
