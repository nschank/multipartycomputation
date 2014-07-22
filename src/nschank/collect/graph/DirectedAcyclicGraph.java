package nschank.collect.graph;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by Nicolas Schank for package nschank.collect.graph
 * Created on 22 Jul 2014
 * Last updated on 22 Jul 2014
 *
 * A DirectedAcyclicGraph is a specific type of Graph (which, coincidentally, is directed and acyclic). It is meant to
 *  deal with the majority of the problems associated with such Graphs in a general sense, as well as dealing with all
 *  the Collection-related methods. Acyclicity is maintained through equality; as such, this graph maintains many of the
 *  properties of a Set (i.e. only one of any element can be added). Loops are considered cycles and are not allowed.
 *
 * @author nschank, Brown University
 * @version 1.1
 */
public class DirectedAcyclicGraph<T> implements Graph<T>
{
	protected final Set<T> top;
	protected final Map<T,DAGNode> nodeMap;

	/**
	 *
	 */
	public DirectedAcyclicGraph()
	{
		this.nodeMap = new HashMap<>();
		this.top = new HashSet<>();
	}

	@Override
	public boolean connectedTo(final T from, final T to)
	{
		return this.nodeMap.get(from).getChildren().stream().anyMatch(e -> e.elem.equals(to));
	}

	@Override
	public Collection<T> allConnectedTo(final T element)
	{
		if(this.nodeMap.containsKey(element))
			return this.nodeMap.get(element).getChildren().stream().map(child -> child.elem).collect(Collectors.toList());
		else return new ArrayList<>();
	}

	@Override
	public boolean connect(final T a, final T b)
	{
		if(!nodeMap.containsKey(a) || !nodeMap.containsKey(b))
			return false;
		try //TODO move away from Error-based throwing
		{
			this.nodeMap.get(a).makeParentOf(this.nodeMap.get(b));
			this.top.remove(b);
			return true;
		}
		catch(Exception e)
		{
			throw new Error("We'll deal with this later! Feasibility results yay!");
		}
	}

	@Override
	public boolean disconnect(final T a, final T b)
	{
		return !(!nodeMap.containsKey(a) || !nodeMap.containsKey(b)) &&
				this.nodeMap.get(a).removeAsParentOf(this.nodeMap.get(b));
	}

	@Override
	public int size()
	{
		return nodeMap.size();
	}

	@Override
	public boolean isEmpty()
	{
		return nodeMap.isEmpty();
	}

	@Override
	public boolean contains(final Object o)
	{
		return this.nodeMap.containsKey(o);
	}

	@Override
	public Iterator<T> iterator()
	{
		return this.nodeMap.keySet().iterator();
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

	@Override
	public boolean add(final T t)
	{
		if(!this.nodeMap.containsKey(t))
		{
			this.nodeMap.put(t, new DAGNode(t));
			this.top.add(t);
		}
		return true;
	}

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
	public boolean containsAll(final Collection<?> c)
	{
		return c.stream().allMatch(this::contains);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c)
	{
		return !c.stream().allMatch(t -> !this.add(t));
	}

	@Override
	public boolean removeAll(final Collection<?> c)
	{
		return !c.stream().allMatch(t -> !this.remove(t));
	}

	@Override
	public boolean retainAll(final Collection<?> c)
	{
		return false;
	}

	@Override
	public void clear()
	{
		this.nodeMap.clear();
	}

	private class DAGNode
	{
		private final T elem;
		private final Set<DAGNode> children;
		private final Set<DAGNode> parents;
		private int layer;

		public Collection<DAGNode> getChildren()
		{
			return Collections.unmodifiableSet(this.children);
		}

		public Collection<DAGNode> getParents()
		{
			return Collections.unmodifiableSet(this.parents);
		}

		public void remove()
		{
			for(DAGNode child : this.children)
			{
				child.parents.remove(this);
				try
				{
					child.realign(null);
				} catch(CycleException e)
				{
					//Impossible
					e.printStackTrace();
				}
			}
			for(DAGNode parent : this.parents)
				parent.children.remove(this);

		}

		public boolean removeAsParentOf(DAGNode oldChild)
		{
			if(!this.children.contains(oldChild))
				return false;
			this.children.remove(oldChild);
			oldChild.parents.remove(this);
			try
			{
				oldChild.realign(null);
			} catch(CycleException e)
			{
				//Impossible. Just to be sure
				e.printStackTrace();
			}
			return true;
		}

		public void makeParentOf(DAGNode newChild) throws CycleException
		{
			this.children.add(newChild);
			newChild.parents.add(this);

			newChild.realign(this);
		}

		public DAGNode(final T elem)
		{
			this.elem = elem;
			this.children = new HashSet<>();
			this.parents = new HashSet<>();
			this.layer = 0;
		}

		/**
		 * TODO: improve the obvious inefficiency problems here (after feasibility results)
		 */
		private void realign(DAGNode aligningNode) throws CycleException
		{
			if(this == aligningNode)
				throw new CycleException(aligningNode.elem);

			int newlayer = 0;
			for(DAGNode parent : this.parents)
				if(parent.layer + 1 > newlayer)
					newlayer = parent.layer+1;
			if(newlayer != this.layer)
			{
				this.layer = newlayer;
				if(this.layer == 0) top.add(this.elem);
				else top.remove(this.elem);
				for(DAGNode child : this.children)
				{
					child.realign(aligningNode);
				}
			}
		}


	}

	private static class CycleException extends Exception
	{
		public CycleException(final Object aligningNode)
		{
			super("The element " + aligningNode + " was involved in a cycle.");
		}
	}
}
