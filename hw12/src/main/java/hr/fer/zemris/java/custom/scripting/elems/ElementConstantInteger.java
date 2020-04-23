package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Storage for expressions that represent an <code>integer</code> constant.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementConstantInteger extends Element {

	/**
	 * Stores integer.
	 */
	private int value;

	/**
	 * Creates new instance of this class defined by given number.
	 * 
	 * @param value Integer constant value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Returns integer constant stored in this class.
	 * 
	 * @return int value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantInteger))
			return false;
		ElementConstantInteger other = (ElementConstantInteger) obj;
		return value == other.value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantInteger(this);
	}
}
