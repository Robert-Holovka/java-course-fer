package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Base class for all graph nodes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class Node {

	/**
	 * Storage for children nodes.
	 */
	private ArrayList<Node> nodes;

	/**
	 * Adds given node as a child of this node.
	 * 
	 * @param child Node
	 * @throws {@link NullPointerException} if given child node is <code>null</code>
	 *         reference
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);
		if (nodes == null) {
			nodes = new ArrayList<>();
		}
		nodes.add(child);
	}

	/**
	 * Returns number of children of this node.
	 * 
	 * @return int number of child nodes
	 */
	public int numberOfChildren() {
		if (nodes == null) {
			return 0;
		}
		return nodes.size();
	}

	/**
	 * Returns child of this node positioned at given index.
	 * 
	 * @param index Position of a child node
	 * @return Node Child at specified position
	 * @throws {@link IndexOutOfBoundsException} if given index is outside range [0,
	 *         numberOfChildren] or if this node does'nt have any children
	 */
	public Node getChild(int index) {
		if (nodes == null || (index < 0 && index >= nodes.size())) {
			throw new IndexOutOfBoundsException();
		}
		return (Node) nodes.get(index);
	}

	/**
	 * Method that visits subclasses of this class.
	 * 
	 * @param visitor Concrete visitor
	 */
	public abstract void accept(INodeVisitor visitor);

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Node))
			return false;

		Node other = (Node) obj;
		if (this.nodes == null && other.numberOfChildren() == 0)
			return true;

		if (this.nodes.size() != other.numberOfChildren())
			return false;

		for (int i = 0; i < nodes.size(); i++) {
			if (!nodes.get(i).equals(other.getChild(i))) {
				return false;
			}
		}

		return true;
	}

}
