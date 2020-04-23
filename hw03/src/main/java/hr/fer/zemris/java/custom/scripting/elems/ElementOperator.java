package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Stores expressions that represent operators.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementOperator extends Element {

	/**
	 * Stores operator symbol.
	 */
	private String symbol;

	/**
	 * Creates new instance of the operator defined by its symbol.
	 * 
	 * @param symbol Operator identity
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Returns symbol of this operator.
	 * 
	 * @return String symbol of operator
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementOperator))
			return false;
		ElementOperator other = (ElementOperator) obj;
		return Objects.equals(symbol, other.symbol);
	}

}
