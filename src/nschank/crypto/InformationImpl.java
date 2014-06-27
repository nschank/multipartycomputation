package nschank.crypto;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 27 Jun 2014
 * Last updated on 27 Jun 2014
 *
 * An implementation of the Information interface
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class InformationImpl<T> implements Information<T>
{
	private final Map<Participant, String> contexts;
	private String name;
	private final T value;

	/**
	 *
	 */
	public InformationImpl(final T value, final Participant p, final String context, final String name)
	{
		this.value = value;
		this.contexts = new HashMap<>();
		this.contexts.put(p, context);
		this.name = name;
	}

	public InformationImpl(final T value, final Participant p, final String context)
	{
		this(value, p, context, "null");
	}

	/**
	 *
	 */
	public static void main(String[] args)
	{

	}

	@Override
	public String getContext(final Participant participant)
	{
		if(this.contexts.containsKey(participant)) return this.contexts.get(participant);
		return Information.super.getContext(participant);
	}

	@Override
	public String getName()
	{
		return this.name;
	}

	@Override
	public T getValue()
	{
		return this.value;
	}

	@Override
	public void giveContext(final String context, final Participant... participant)
	{
		for(Participant p : participant)
			this.contexts.put(p, context);
	}

	/**
	 * @return A String representation of this InformationImpl
	 */
	@Override
	public String toString()
	{
		return this.value.toString();
	}
}
