package nschank.crypto.history;

import nschank.crypto.player.Participant;
import nschank.note.Immutable;
import nschank.util.NIterators;

import java.util.*;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * An implementation of the History interface.
 *
 * @author nschank, Brown University
 * @version 1.3
 */
public class HistoryImpl implements History
{
	private final List<EventDescription> events;
	private String tabs = "";

	/**
	 * Creates a History for a Protocol to add to.
	 */
	public HistoryImpl()
	{
		this.events = new ArrayList<>();
	}

	/**
	 * Adds an event to the History of a Protocol
	 *
	 * @param event
	 * 		A String description of an event that has occurred in the Protocol which every participant witnessed
	 *
	 * @return true
	 */
	@Override
	public synchronized boolean add(final String event)
	{
		this.events.add(new EventDescription(tabs + event));
		return true;
	}

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
	@Override
	public synchronized boolean add(final String event, final Collection<Participant> visibleTo)
	{
		this.events.add(new EventDescription(tabs + event, visibleTo));
		return true;
	}

	/**
	 * An Iterable of events which are all visible to all of the given participants
	 *
	 * @param participants
	 * 		The group of participants who must have all seen every event returned.
	 *
	 * @return An Iterable of event descriptions, in correct order.
	 */
	@Override
	public synchronized Iterable<String> eventsFullyVisibleTo(final Collection<Participant> participants)
	{
		return () -> NIterators.map(NIterators.subiterator(this.events.iterator(), (EventDescription e) -> e.visibleTo(
				participants)), EventDescription::toString);
	}

	/**
	 * An Iterable of events which are all visible to at least one of the given participants
	 *
	 * @param participants
	 * 		The group of participants who have, as a whole, seen every event
	 *
	 * @return An Iterable of event descriptions, in correct order.
	 */
	@Override
	public synchronized Iterable<String> eventsVisibleTo(final Collection<Participant> participants)
	{
		return () -> NIterators.map(NIterators.subiterator(this.events.iterator(),
														   (EventDescription f) -> f.visibleToAny(participants)),
									EventDescription::toString);
	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an Iterator.
	 */
	@Override
	public synchronized Iterator<String> iterator()
	{
		return NIterators.map(this.events.iterator(), EventDescription::toString);
	}

	@Override
	public synchronized void tabDown()
	{
		tabs = tabs.substring(0, Math.max(0, tabs.length() - 1));
	}

	@Override
	public synchronized void tabUp()
	{
		tabs = tabs + "\t";
	}

	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();
		for(String s : this)
			b.append(s).append("\n");
		return b.toString();
	}

	@Immutable
	private static class EventDescription
	{
		private final String eventDescription;
		//Empty refers to everyone, for space and runtime considerations
		private final Optional<? extends Collection<? extends Participant>> participants;

		/**
		 * @param description
		 * 		A description of an event visible to everyone
		 */
		public EventDescription(String description)
		{
			this.eventDescription = description;
			this.participants = Optional.empty();
		}

		/**
		 * @param description
		 * 		A description of an event visible to everyone
		 * @param viewers
		 * 		All Participants who witnessed the event
		 */
		public EventDescription(String description, Collection<? extends Participant> viewers)
		{
			this.eventDescription = description;
			this.participants = Optional.of(new HashSet<>(viewers));
		}

		/**
		 * @return The description of the event
		 */
		@Override
		public String toString()
		{
			return eventDescription;
		}

		/**
		 * Whether this event was visible to any of the given witnesses
		 *
		 * @param witnesses
		 * 		Any Participants who may have seen this event
		 *
		 * @return true iff any of the witnesses viewed this event
		 */
		public boolean visibleTo(Collection<? extends Participant> witnesses)
		{
			if(!this.participants.isPresent()) return true;
			for(Participant p : witnesses)
				if(this.participants.get().contains(p)) return true;
			return false;
		}

		/**
		 * Whether this event was visible to a particular witness
		 *
		 * @param witness
		 * 		A Participant who may have seen this event
		 *
		 * @return true iff the witness viewed this event
		 */
		public boolean visibleTo(Participant witness)
		{
			return !this.participants.isPresent() || this.participants.get().contains(witness);
		}

		/**
		 * Whether this event was visible to all of the given witnesses
		 *
		 * @param witnesses
		 * 		Any Participants who may have seen this event
		 *
		 * @return true iff all of the witnesses viewed this event
		 */
		public boolean visibleToAny(Collection<? extends Participant> witnesses)
		{
			return !this.participants.isPresent() || this.participants.get().containsAll(witnesses);
		}
	}
}
