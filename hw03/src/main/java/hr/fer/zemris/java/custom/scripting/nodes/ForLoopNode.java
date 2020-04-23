package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for-loop construct.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ForLoopNode extends Node {
	/**
	 * Stores for-loop variable.
	 */
	private ElementVariable variable;
	/**
	 * Stores begin expression of a for-loop.
	 */
	private Element startExpression;
	/**
	 * Stores end expression of a for-loop.
	 */
	private Element endExpression;
	/**
	 * Stores step of a for-loop.
	 */
	private Element stepExpression;

	/**
	 * Creates new instance of a for-loop node.
	 * 
	 * @param variable        For-loop variable
	 * @param startExpression For-loop start expression
	 * @param endExpression   For-loop end expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
		this(variable, startExpression, endExpression, null);
	}

	/**
	 * Creates new instance of a for-loop node.
	 * 
	 * @param variable        For-loop variable
	 * @param startExpression For-loop start expression
	 * @param endExpression   For-loop end expression
	 * @param stepExpression  For-loop step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Returns variable of the for-loop node.
	 * 
	 * @return ElementVariable variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Returns start expression of the for-loop node.
	 * 
	 * @return Element start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Returns end expression of the for-loop node.
	 * 
	 * @return Element end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Returns step of the for-loop node.
	 * 
	 * @return Element step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof ForLoopNode))
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		boolean isTrue = Objects.equals(endExpression, other.endExpression)
				&& Objects.equals(startExpression, other.startExpression)
				&& Objects.equals(stepExpression, other.stepExpression) && Objects.equals(variable, other.variable);
		return isTrue;
	}
}
