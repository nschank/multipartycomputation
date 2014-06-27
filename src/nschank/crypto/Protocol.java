package nschank.crypto;

import java.util.Set;
import java.util.concurrent.Callable;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A cryptographic Protocol being run. Has a set of Participants.
 *
 * Should not override equality or hashcode.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public interface Protocol<R> extends Callable<R>
{
	public Set<Participant> getParticipants();
}
