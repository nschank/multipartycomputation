package nschank.util;

import com.google.common.base.Function;

import java.util.Iterator;

/**
 * Tou2
 *
 * @author Nicolas Schank
 * @version 2013 10 07
 * @since 2013 10 07 9:10 PM
 */
public final class NIterators
{

	public static <T,S> Iterator<S> map(final Iterator<T> input, final Function<T,S> map)
	{
		return new Iterator<S>(){
			@Override
			public boolean hasNext()
			{
				return input.hasNext();
			}

			@Override
			public S next()
			{
				return map.apply(input.next());
			}

			@Override
			public void remove()
			{
				input.remove();
			}
		};
	}

	public static <T> Iterable<T> toIterable(final Iterator<T> it)
	{
		return new Iterable<T>(){

			@Override
			public Iterator<T> iterator()
			{
				return it;
			}
		};
	}
}
