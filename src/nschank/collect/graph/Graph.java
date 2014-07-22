package nschank.collect.graph;

import java.util.Collection;


/**
 * Created by Nicolas Schank for package nschank.collect.graph
 * Created 1 Mar 2013
 * Last updated 25 Mar 2014
 * Moved into identical package in unrelated repository multipartycomputation on 22 Jul 2014
 *
 * A Graph is a Collection of items that can be connected to any other item in
 * the Graph.
 *
 * @param <T>
 * 		Any type
 *
 * @author Nicolas Schank
 * @version 1.2
 */
public interface Graph<T> extends Collection<T>
{
	/**
	 * Given two elements, this procedure must return whether or not the two are
	 * connected in the Graph. To support unidirectionality, connectedTo(a,b)
	 * does not necessarily need to be equivalent to connectedTo(b,a). If either element is not present, must return false.
	 *
	 * @param from
	 * 		The element in the graph a connection must start from, in
	 * 		order for this procedure to return true.
	 * @param to
	 * 		The element in the graph a connection must reach, in order for
	 * 		this procedure to return true.
	 *
	 * @return Whether such a connection exists.
	 */
	public boolean connectedTo(T from, T to);

	/**
	 * @param element
	 * 		A single element in this Graph.
	 *
	 * @return all elements that can be reached from this element using a single
	 * connection.
	 */
	public Collection<T> allConnectedTo(T element);

	/**
	 * Attempts to connect two elements in this graph. Should return true if the elements either were already connected
	 * or are now connected, and false otherwise. Implementations should (read: must) specify whether:
	 * - Elements are added (and then connected) if not already present.
	 * - Elements given to this method are connected unconditionally, or only if certain conditions are met.
	 *
	 * Default implementation is identical to connectedTo.
	 *
	 * @param a
	 * 		Any element
	 * @param b
	 * 		Any other element
	 *
	 * @return {@literal true} if and only if {@code a} and {@code b} are now both in the graph and connected to each other.
	 */
	default public boolean connect(T a, T b)
	{
		return connectedTo(a,b);
	}

	/**
	 * Attempts to disconnect two elements in this graph. Should return true if both elements are in the graph, were connected
	 * 	 before this method was called, and now are not. This particular definition is intended to reflect {@code remove}.
	 *
	 * Default implementation does nothing and returns false.
	 *
	 * @param a
	 * 		Any element
	 * @param b
	 * 		Any other element
	 * @return {@literal true} if and only if {@code a} was connected to {@code b} before this method call, but no longer does afterward.
	 */
	default public boolean disconnect(T a, T b)
	{
		return false;
	}
}
