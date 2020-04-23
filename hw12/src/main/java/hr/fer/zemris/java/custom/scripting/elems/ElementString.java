package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Storage for expressions that represent a text.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementString extends Element {

	/**
	 * Stores text.
	 */
	private String value;

	/**
	 * Creates new instance of this class defined by given text.
	 * 
	 * @param value String which contains text representation of expression
	 */
	public ElementString(String value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

	/**
	 * Returns text stored in this class.
	 * 
	 * @return String text
	 */
	public String getValue() {
		return value.replaceAll("\"", "");
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementString))
			return false;
		ElementString other = (ElementString) obj;
		return Objects.equals(value, other.value);
	}
	
	@Override
	public String toString() {
		return value;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementString(this);
	}

}
