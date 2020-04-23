package searching.algorithms;

/**
 * Describes single transition.
 * 
 * @author Robert Holovka
 * @version 1.0
 *
 * @param <S> Type of state
 */
public class Transition<S> {

	/**
	 * State after this transition has been made.
	 */
	private S state;
	/*
	 * Cost of this transition.
	 */
	private double cost;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param state state after this transition has been made
	 * @param cost  cost of this transition
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Returns state.
	 * 
	 * @return S
	 */
	public S getState() {
		return state;
	}

	/**
	 * Returns cost.
	 * 
	 * @return double
	 */
	public double getCost() {
		return cost;
	}

}
