package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Storage for expressions that represent a <code>decimal</code> constant.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementConstantDouble extends Element {
	/**
	 * Stores decimal number.
	 */
	private double value;

	/**
	 * Creates new instance of this class defined by a given decimal.
	 * 
	 * @param value Decimal constant value
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return String.valueOf(value);
	}

	/**
	 * Returns decimal constant stored in this class.
	 * 
	 * @return double value
	 */
	public double getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementConstantDouble))
			return false;
		ElementConstantDouble other = (ElementConstantDouble) obj;
		return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementConstantDouble(this);
	}
}
