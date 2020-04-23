package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Base class for the representation of expressions.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class Element {

	/**
	 * Returns stored expression as text.
	 * 
	 * @return empty String
	 */
	public abstract String asText();

	/**
	 * Method that visits subclasses of this class.
	 * 
	 * @param visitor Concrete visitor
	 */
	public abstract void accept(IElementVisitor visitor);

}
