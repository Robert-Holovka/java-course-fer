package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Represents a piece of textual data.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class TextNode extends Node {
	/**
	 * Stores piece of textual data.
	 */
	private String text;

	/**
	 * Creates new instance of this class and stores given text.
	 * 
	 * @param text piece of textual data.
	 * @throws {@link NullPointerException} if given text is <code>null</code>
	 *         reference
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text);
		this.text = text;
	}

	/**
	 * Returns text stored in this node.
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

}
