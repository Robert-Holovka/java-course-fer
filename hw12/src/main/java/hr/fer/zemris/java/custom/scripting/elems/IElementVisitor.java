package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Visitor for subclasses of Element class.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IElementVisitor {

	/**
	 * Methods which {@link ElementVariable} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementVariable(ElementVariable element);

	/**
	 * Methods which {@link ElementConstantDouble} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementConstantDouble(ElementConstantDouble element);

	/**
	 * Methods which {@link ElementConstantInteger} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementConstantInteger(ElementConstantInteger element);

	/**
	 * Methods which {@link ElementString} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementString(ElementString element);

	/**
	 * Methods which {@link ElementOperator} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementOperator(ElementOperator element);

	/**
	 * Methods which {@link ElementFunction} calls when visited.
	 * 
	 * @param element
	 */
	void visitElementFunction(ElementFunction element);
}
