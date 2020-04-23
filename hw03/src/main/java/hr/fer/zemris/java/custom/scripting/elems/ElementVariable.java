package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Stores expressions that represent variables.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ElementVariable extends Element {
	/**
	 * Name of the variable.
	 */
	private String name;

	/**
	 * Creates new instance of a variable defined by the given name.
	 * 
	 * @param name Name of the variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}

	/**
	 * Returns name of this variable.
	 * 
	 * @return String which contains variable name
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
		if (!(obj instanceof ElementVariable))
			return false;
		ElementVariable other = (ElementVariable) obj;
		return Objects.equals(name, other.name);
	}
}
