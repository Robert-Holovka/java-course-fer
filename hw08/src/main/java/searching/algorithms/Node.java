package searching.algorithms;

import java.util.Objects;

/**
 * Holds some details about one move made in the puzzle.
 * 
 * @author Robert Holovka
 * @version 1.0
 *
 * @param <S> Type of the node state
 */
public class Node<S> {

	/**
	 * State after this move has performed.
	 */
	private S state;
	/**
	 * Reference to the parent node.
	 */
	private Node<S> parent;
	/**
	 * Cost of this move.
	 */
	private double cost;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param parent Reference to the parent node
	 * @param state  State after this move has performed
	 * @param cost   Cost of this move
	 */
	public Node(Node<S> parent, S state, double cost) {
		Objects.requireNonNull(state);
		this.state = state;
		this.parent = parent;
		this.cost = cost;
	}

	/**
	 * Returns state after this move has performed.
	 * 
	 * @return S
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns cost of this move.
	 * 
	 * @return
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * Returns reference to the parent node.
	 * 
	 * @return
	 */
	public Node<S> getParent() {
		return parent;
	}
}
