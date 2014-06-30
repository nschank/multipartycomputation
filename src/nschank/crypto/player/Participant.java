package nschank.crypto.player;

import nschank.crypto.protocol.instruct.Instruction;

import java.util.Collection;
import java.util.Map;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Current version implemented 3 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * A {@code Participant} is anything that wishes to participate in a {@code Protocol} in order to determine the answer to
 * something. This interface is meant to represent both real participants (who may be on the same or different machines/
 * networks), as well as more didactic examples of participants who will write to {@code History}s in order to demonstrate
 * the run of a Protocol.
 *
 * Real participants in a protocol should be cautious in ensuring that the {@code List} of {@code Instruction}s received
 * by the {@code receive} method was indeed sent by the intended {@code Protocol}. This should be made easier by an intended
 * future implementation which will use only local versions of a {@code Protocol} with networked other {@code Participant}s.
 *
 * Extends {@code Runnable} so that it is easy for sample participants to be run in different {@code Thread}s.
 *
 * @author nschank, Brown University
 * @version 3.1
 * @see nschank.crypto.protocol.instruct.Instruction
 * @see nschank.crypto.protocol.Protocol
 */
public interface Participant extends Runnable
{

	/**
	 * Intended only for use by a {@code Protocol} to inform this {@code Participant} what values it should expect from
	 * other {@code Participant}s.
	 *
	 * @param expectedValues
	 * 		A mapping from {@code Participant}s to the values expected to receive from them
	 */
	public void expect(Map<Participant, Collection<String>> expectedValues);
	/**
	 * A name by which to refer to this Participant
	 *
	 * @return A unique String
	 */
	public String getName();
	/**
	 * Intended for use mostly by {@code Instruction}s, to give other {@code Participant}s values.
	 *
	 * @param valueName
	 * 		The name of a value the {@code Participant} is expecting
	 * @param value
	 * 		The value under that name
	 * @param from
	 * 		The Participant sending the value
	 *
	 * @return True iff both: this {@code Participant} was expecting the value, and the {@code Participant} agreed
	 * with the value provided (that is, accepted the value as correct).
	 */
	public boolean provide(String valueName, Object value, Participant from);
	/**
	 * Intended only for use by a {@code Protocol} to provide this {@code Participant} with an ordered list of
	 * {@code Instruction}s to follow.
	 *
	 * @param protocol
	 * 		A list of {@code Instruction}s to follow.
	 */
	public void receive(Iterable<Instruction> protocol);
}
