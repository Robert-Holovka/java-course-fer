package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Stores all states of lines(vectors) that where rendered on a Painter.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Context {

	/**
	 * Storage for a vectors states.
	 */
	private ObjectStack<TurtleState> states;

	/**
	 * Constructs new instance of this class.
	 */
	public Context() {
		states = new ObjectStack<>();
	}

	/**
	 * Returns state of the lastly processed vector.
	 * 
	 * @return TurtleState
	 */
	public TurtleState getCurrentState() {
		return states.peek();
	}

	/**
	 * Adds new state of the vector.
	 * 
	 * @param state TurtleState
	 */
	public void pushState(TurtleState state) {
		states.push(state);
	}

	/**
	 * Removes state of the lastly processed vector.
	 */
	public void popState() {
		states.pop();
	}
}
