package nschank.crypto.history;

/**
 * Created by Nicolas Schank for package nschank.crypto
 * Created on 27 Jun 2014
 * Last updated on 27 Jun 2014
 *
 * @author nschank, Brown University
 * @version 1.2
 */
public enum LearningType
{
	CALC("calculated"),
	GIVEN("was given"),
	CHOSEN("chose"),
	CREATED("created");

	private String verb;

	LearningType(String verb)
	{
		this.verb = " " + verb + " ";
	}

	public String verb()
	{
		return verb;
	}
}
