package nschank.util;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Created by Nicolas Schank for package nschank.util
 * Created on 26 Jun 2014
 * Last updated on 26 Jun 2014
 *
 * Utility class for Iterators
 *
 * @author nschank, Brown University
 * @version 1.3.1
 */
public final class NIterators
{
	/**
	 *
	 */
	private NIterators()
	{
		//Utility class
	}

	/**
	 * Creates an Iterator of a new type from an Iterator and a Function from the old type to the new type
	 *
	 * @param from
	 * 		An Iterator of any type
	 * @param to
	 * 		A function to apply piecewise to each element of {@code from}
	 * @param <T>
	 * 		Any type
	 * @param <S>
	 * 		Any type
	 *
	 * @return An Iterator of type S created from an Iterator of type T and a function that goes element by element
	 */
	public static <T, S> Iterator<? super S> map(final Iterator<? extends T> from, final Function<T, S> to)
	{
		return new Iterator<S>()
		{
			@Override
			public boolean hasNext()
			{
				return from.hasNext();
			}

			@Override
			public S next()
			{
				return to.apply(from.next());
			}
		};
	}

	/**
	 * Given an Iterator and a Predicate of the same type, returns an iterator which goes through the given iterator but
	 * only returns those elements which satisfy the predicate.
	 *
	 * @param universe
	 * 		Any iterator
	 * @param includeIf
	 * 		A predicate which may return true for some elements of {@code universe}
	 * @param <T>
	 * 		Any type
	 *
	 * @return An iterator which goes through all elements of {@code universe} which satisfy {@code includeIf}, in same order
	 */
	public static <T> Iterator<? super T> subiterator(final Iterator<? extends T> universe,
													  final Predicate<T> includeIf)
	{
		return new Iterator<T>()
		{
			private Optional<T> next = Optional.empty();
			private boolean empty = false;

			@Override
			public synchronized boolean hasNext()
			{
				if(!next.isPresent()) step();
				return !empty;
			}

			@Override
			public synchronized T next()
			{
				if(!next.isPresent()) step();
				if(empty) throw new NoSuchElementException();
				return next.get();
			}

			/**
			 *
			 */
			private synchronized void step()
			{
				while(universe.hasNext())
				{
					T possible = universe.next();
					if(includeIf.test(possible))
					{
						next = Optional.of(possible);
						return;
					}
				}
				empty = true;
			}
		};
	}
}
