package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EchoNode extends Node {
	/**
	 * Storage for elements which this node contains.
	 */
	private Element[] elements;

	/**
	 * Creates new instance of a <code>EchoNode</code> with its elements.
	 * 
	 * @param elements of this node
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Returns elements of this node.
	 * 
	 * @return Element[] elements
	 */
	public Element[] getElements() {
		return elements;
	}
}
