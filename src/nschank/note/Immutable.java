package nschank.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created by Nicolas Schank for package nschank.note
 * Created on 25 Feb 2014
 * Added to repo mpccode from JavaOmega 26 Jun 2014
 *
 * Indicates that this class is not meant to be mutated once it is created.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Immutable
{

}
