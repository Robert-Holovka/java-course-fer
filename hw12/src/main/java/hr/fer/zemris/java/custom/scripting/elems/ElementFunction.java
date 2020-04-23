package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Stores expressions that represent functions.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementFunction extends Element {

	/**
	 * Name of the function.
	 */
	private String name;

	/**
	 * Creates new instance of a function defined by the given name.
	 * 
	 * @param name Name of the function
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns name of this function
	 * 
	 * @return String name of the function
	 */
	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ElementFunction))
			return false;
		ElementFunction other = (ElementFunction) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "@" + name;
	}

	@Override
	public void accept(IElementVisitor visitor) {
		visitor.visitElementFunction(this);
	}
}
