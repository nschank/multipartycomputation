package nschank.collect.graph;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Nicolas Schank for package nschank.collect.graph
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A DirectedAcyclicGraph is a specific type of Graph (which, coincidentally, is directed and acyclic). It is meant to
 * deal with the majority of the problems associated with such Graphs in a general sense, as well as dealing with all
 * the Collection-related methods. Acyclicity is maintained through equality; as such, this graph maintains many of the
 * properties of a Set (i.e. only one of any element can be added). Loops are considered cycles and are not allowed.
 *
 * @author nschank, Brown University
 * @version 2.1
 */
public class DirectedAcyclicGraph<T> implements Graph<T>
{
	protected final Map<T, DAGNode> nodeMap;
	protected final Set<T> top;

	/**
	 * Creates a DirectedAcyclicGraph with 0 elements.
	 */
	public DirectedAcyclicGraph()
	{
		this.nodeMap = new HashMap<>();
		this.top = new HashSet<>();
	}

	/**
	 * Adds the given element to this DAG.
	 *
	 * @param t
	 * 		Any value
	 *
	 * @return {@literal true} if this element wasn't already in this DAG
	 */
	@Override
	public boolean add(final T t)
	{
		if(this.nodeMap.containsKey(t)) return false;

		this.nodeMap.put(t, new DAGNode(t));
		this.top.add(t);
		return true;
	}

	@Override
	public boolean addAll(final Collection<? extends T> c)
	{
		return c.stream().map(this::add).reduce(false, (a, b) -> a || b);
	}

	/**
	 * @param element
	 * 		A single element in this Graph.
	 *
	 * @return A Collection of all the elements {@code x} such that {@code connectedTo(element, x)} is true.
	 */
	@Override
	public Collection<T> allConnectedTo(final T element)
	{
		if(this.nodeMap.containsKey(element))
			return this.nodeMap.get(element).getChildren().stream().map(child -> child.elem)
					.collect(Collectors.toList());
		else return new ArrayList<>();
	}

	@Override
	public void clear()
	{
		this.nodeMap.clear();
		this.top.clear();
	}

	/**
	 * Causes two elements in this DAG to connect, given they are both already in the DAG AND it would not
	 * create a cycle.
	 *
	 * @param a
	 * 		Any element
	 * @param b
	 * 		Any other element
	 *
	 * @return Whether {@code a} and {@code b} are now connected.
	 */
	@Override
	public boolean connect(final T a, final T b)
	{
		if(!nodeMap.containsKey(a) || !nodeMap.containsKey(b)) return false;
		return this.nodeMap.get(a).makeParentOf(this.nodeMap.get(b));
	}

	/**
	 * @param from
	 * 		The element in the graph a connection must start from, in
	 * 		order for this procedure to return true.
	 * @param to
	 * 		The element in the graph a connection must reach, in order for
	 * 		this procedure to return true.
	 *
	 * @return {@literal true} if {@code from} and {@code to} are both elements in this DAG and {@code from}
	 * is connected to {@code to}
	 */
	@Override
	public boolean connectedTo(final T from, final T to)
	{
		return this.nodeMap.get(from).getChildren().stream().anyMatch(e -> e.elem.equals(to));
	}

	@Override
	public boolean contains(final Object o)
	{
		return this.nodeMap.containsKey(o);
	}

	@Override
	public boolean containsAll(final Collection<?> c)
	{
		return c.stream().allMatch(this::contains);
	}

	@Override
	public boolean disconnect(final T a, final T b)
	{
		return this.nodeMap.containsKey(a) && this.nodeMap.containsKey(b) &&
				this.nodeMap.get(a).removeAsParentOf(this.nodeMap.get(b));
	}

	@Override
	public boolean isEmpty()
	{
		return nodeMap.isEmpty();
	}

	@Override
	public Iterator<T> iterator()
	{
		return this.nodeMap.keySet().iterator();
	}

	/**
	 * Removes the given element from this DAG
	 *
	 * @param o
	 * 		Any value
	 *
	 * @return {@literal true} if this element was in this DAG and was removed correctly
	 */
	@Override
	public boolean remove(final Object o)
	{
		if(this.nodeMap.containsKey(o))
		{
			this.nodeMap.get(o).remove();
			this.nodeMap.remove(o);
			this.top.remove(o);
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(final Collection<?> c)
	{
		return c.stream().map(this::remove).reduce(false, (a, b) -> a || b);
	}

	@Override
	public boolean retainAll(final Collection<?> c)
	{
		return false;
	}

	@Override
	public int size()
	{
		return nodeMap.size();
	}

	@Override
	public Object[] toArray()
	{
		return this.nodeMap.keySet().toArray();
	}

	@Override
	public <T1> T1[] toArray(final T1[] a)
	{
		return this.nodeMap.keySet().toArray(a);
	}

	/**
	 * A DAGNode is a single node within a DAG. It knows its own parents and children, as well as its
	 * 'layer', which is exactly one greater than the maximum layer of its parents.
	 */
	protected class DAGNode
	{
		private final Set<DAGNode> children;
		private final T elem;
		private int layer;
		private final Set<DAGNode> parents;

		/**
		 * Creates a DAGNode with the given element. Has no children or parents and a layer of 0.
		 *
		 * @param elem
		 * 		The element to add to the DAG
		 */
		public DAGNode(final T elem)
		{
			this.elem = elem;
			this.children = new HashSet<>();
			this.parents = new HashSet<>();
			this.layer = 0;
			DirectedAcyclicGraph.this.top.add(elem);
		}

		private boolean ancestorOf(final DAGNode possibleDescendant)
		{
			if(this.layer >= possibleDescendant.layer) return false;
			Set<DAGNode> checked = new HashSet<>();
			Set<DAGNode> toCheck = Collections.unmodifiableSet(this.children);
			checked.add(this);
			while(!toCheck.isEmpty())
			{
				if(toCheck.contains(possibleDescendant)) return true;
				checked.addAll(toCheck);
				toCheck = toCheck.stream().flatMap(node -> node.children.stream())
						.filter(node -> node.layer <= possibleDescendant.layer).collect(Collectors.toSet());
			}
			return false;
		}

		/**
		 * @return An (unmodifiable) collection of children
		 */
		public Collection<DAGNode> getChildren()
		{
			return Collections.unmodifiableSet(this.children);
		}

		/**
		 * @return An (unmodifiable) collection of parents
		 */
		public Collection<DAGNode> getParents()
		{
			return Collections.unmodifiableSet(this.parents);
		}

		/**
		 * @param newChild
		 * 		Some DAGNode to connect to this node
		 *
		 * @return {@literal true} if the connection took place, {@literal false} if the nodes were
		 * already connected or if the connection would cause a cycle
		 */
		public boolean makeParentOf(DAGNode newChild)
		{
			if(this.children.contains(newChild) || newChild.ancestorOf(this)) return false;
			this.children.add(newChild);
			newChild.parents.add(this);

			if(newChild.layer <= this.layer) newChild.realign();
			return true;
		}

		private void realign()
		{
			int newlayer = 0;

			for(DAGNode parent : this.parents)
				if(parent.layer + 1 > newlayer) newlayer = parent.layer + 1;

			if(newlayer != this.layer)
			{
				this.layer = newlayer;
				if(this.layer == 0) top.add(this.elem);
				else top.remove(this.elem);

				this.children.stream().filter(child -> child.layer < this.layer + 1).forEach(DAGNode::realign);
			}
		}

		/**
		 * Removes this DAGNode from the tree. Must:
		 * - disconnect from its parents and children
		 * - tell its children to realign, if their layer may not be accurate
		 */
		public void remove()
		{
			for(DAGNode child : this.children)
			{
				child.parents.remove(this);
				if(child.layer == this.layer + 1) child.realign();
			}
			for(DAGNode parent : this.parents)
				parent.children.remove(this);
		}

		/**
		 * @param oldChild
		 * 		Some DAGNode which was may have been a child of this node
		 *
		 * @return {@literal true} if the removal needed to take place
		 */
		public boolean removeAsParentOf(DAGNode oldChild)
		{
			if(!this.children.contains(oldChild)) return false;
			this.children.remove(oldChild);
			oldChild.parents.remove(this);
			if(oldChild.layer == this.layer + 1) oldChild.realign();
			return true;
		}
	}
}
