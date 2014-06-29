package nschank.crypto;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A Participant who doesn't attempt to lie.
 *
 * @author nschank, Brown University
 * @version 2.1
 */
public class HonestParticipant implements Participant
{
	private String key = "A";
	private final Set<Information> known;
	private final String name;
	private final History view;

	/**
	 *
	 */
	public HonestParticipant(History view, String name)
	{
		this.name = name;
		this.known = new HashSet<>();
		this.view = view;
	}

	private static String increment(final String key)
	{
		String prefix = key.substring(0, Math.max(0, key.length() - 1));
		if("".equals(key)) return "A";
		if(key.charAt(key.length() - 1) == 'Z') return prefix + "a";
		if(key.charAt(key.length() - 1) == 'z') return increment(prefix) + "A";
		return prefix + Character.toString((char) (key.charAt(key.length() - 1) + 1));
	}

	@Override
	public boolean forget(final Information... infs)
	{
		for(Information s : infs)
			if(this.known.remove(s))
				;//this.getHistory().add(this.toString() + " no longer needs " + s.getName(), this);
		return true;
	}

	@Override
	public History getHistory()
	{
		return this.view;
	}

	private String getKey()
	{
		String ret = key;
		key = increment(key);
		return name + "_" + ret;
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public <T> Information<T> give(final Information<T> information, final LearningType learnedBy)
	{
		this.known.add(information);
		this.getHistory().add(
				this.toString() + learnedBy.verb() + information.getName() + "=" + information.getValue() + "; "
						+ information.getContext(this), this);
		return information;
	}

	@Override
	public <T> Information<T> give(final T object, final String context, final LearningType learnedBy)
	{
		return this.give(object, context, learnedBy, this.getKey());
	}

	@Override
	public <T> Information<T> give(final T object, final String context, final LearningType learnedBy, String name)
	{
		return this.give(new InformationImpl<>(object, this, context, name), learnedBy);
	}

	/**
	 * @return A String representation of this HonestParticipant
	 */
	@Override
	public String toString()
	{
		return name;
	}
}
