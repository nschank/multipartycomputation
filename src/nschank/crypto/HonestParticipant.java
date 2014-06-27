package nschank.crypto;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * A Participant who doesn't attempt to lie.
 *
 * @author nschank, Brown University
 * @version 1.2
 */
public class HonestParticipant implements Participant
{
	private String key = "A";
	private final Map<String, Object> known;
	private final String name;
	private final History view;

	/**
	 *
	 */
	public HonestParticipant(History view, String name)
	{
		this.name = name;
		this.known = new HashMap<>();
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
	public boolean containsKey(final String informationKey)
	{
		return this.known.containsKey(informationKey);
	}

	@Override
	public boolean forget(final String... informationKey)
	{
		for(String s : informationKey)
			this.forget(s);
		return true;
	}

	@Override
	public Object get(final String informationKey)
	{
		return this.known.get(informationKey);
	}

	@Override
	public History getHistory()
	{
		return this.view;
	}

	@Override
	public String getKey()
	{
		String ret = key;
		key = increment(key);
		return ret;
	}

	@Override
	public String give(final Object information, final String description)
	{
		this.getHistory().add(name + " now knows " + description, this);
		String thisKey = this.getKey();
		this.known.put(thisKey, information);
		return thisKey;
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
