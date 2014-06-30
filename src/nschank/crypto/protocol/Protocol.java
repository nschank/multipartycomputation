package nschank.crypto.protocol;

import nschank.crypto.player.Participant;

import java.util.Set;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Current version implemented 30 Jun 2014
 * Last updated on 30 Jun 2014
 *
 * A cryptographic protocol being run. Meant to be used both for sample protocols (which run with only fake participants,
 * for didactic purposes and demonstration) AND for REAL protocols, in which real-life participants can actually cooperate
 * using the {@code Protocol}'s {@code Instruction}s.
 *
 * A {@code Protocol} should be given a set of {@code Participant}s who want to participate, and should immediately send
 * all of them an {@code Iterable} of {@code Instruction}s to follow. So that {@code Participant}s in a real scenario are not
 * forced to trust the {@code Protocol}'s {@code Instruction} code, {@code Instruction}s must always allow a Participant
 * to supply their own randomness (if they wish); rerun the {@code Instruction}; double check that the value is
 * correct; and to see the exact calculation the {@code Instruction} is performing.
 *
 * Should not override equality or hashcode, as - like a function - equality is almost impossible to determine in a sensible
 * way.
 *
 * @author nschank, Brown University
 * @version 2.1
 * @see nschank.crypto.protocol.instruct.Instruction
 * @see nschank.crypto.player.Participant
 */
public interface Protocol
{
	public Set<Participant> getParticipants();
}
