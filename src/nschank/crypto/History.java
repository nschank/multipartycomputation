package nschank.crypto;

import java.util.Collection;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * The entire History of a protocol, as a list of Strings describing what has occurred. Any event (as a String) may be
 * added to the history as a whole (which means it was an event that everyone saw), or added to a Participant or group
 * of Participants' private history.
 *
 * @author nschank, Brown University
 * @version 1.2
 */
public interface History extends Iterable<String>
{
	/**
	 * Adds an event to the History of a Protocol
	 *
	 * @param event
	 * 		A String description of an event that has occurred in the Protocol which every participant witnessed
	 *
	 * @return true
	 */
	public boolean add(String event);

	/**
	 * Adds an event to the History of a Protocol
	 *
	 * @param event
	 * 		A String description of an event that has occurred in the Protocol
	 * @param visibleTo
	 * 		What Participants in the Protocol witnessed this event
	 *
	 * @return true
	 */
	public boolean add(String event, Collection<Participant> visibleTo);

	/**
	 * An Iterable of events which are all visible to all of the given participants
	 *
	 * @param participants
	 * 		The group of participants who must have all seen every event returned.
	 *
	 * @return An Iterable of event descriptions, in correct order.
	 */
	public Iterable<String> eventsFullyVisibleTo(Collection<Participant> participants);

	/**
	 * An Iterable of events which are all visible to at least one of the given participants
	 *
	 * @param participants
	 * 		The group of participants who have, as a whole, seen every event
	 *
	 * @return An Iterable of event descriptions, in correct order.
	 */
	public Iterable<String> eventsVisibleTo(Collection<Participant> participants);
}
