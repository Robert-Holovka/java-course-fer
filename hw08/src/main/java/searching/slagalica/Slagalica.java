package searching.slagalica;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Defines strategies that are necessary to solve the puzzle.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Slagalica implements Predicate<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Supplier<KonfiguracijaSlagalice> {

	/**
	 * Size of a row in this puzzle.
	 */
	public static final int ROW_SIZE = 3;
	/**
	 * Numeric value for whitespace.
	 */
	public static final int SPACE_VALUE = 0;

	/**
	 * Initial order of elements.
	 */
	private KonfiguracijaSlagalice initialConfig;
	/**
	 * Final, wanted order of elements.
	 */
	private int[] solution = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param initialConfig Initial configuration
	 */
	public Slagalica(KonfiguracijaSlagalice initialConfig) {
		this.initialConfig = initialConfig;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return initialConfig;
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice t) {
		List<Transition<KonfiguracijaSlagalice>> transitions = new LinkedList<Transition<KonfiguracijaSlagalice>>();

		// Left
		switchPlaces(t, (c) -> (c.indexOfSpace() % ROW_SIZE != 0), -1, transitions);
		// Up
		switchPlaces(t, (c) -> ((c.indexOfSpace() - ROW_SIZE) >= 0), -ROW_SIZE, transitions);
		// Right
		switchPlaces(t, (c) -> (c.indexOfSpace() % ROW_SIZE != 2), +1, transitions);
		// Down
		switchPlaces(t, (c) -> ((c.indexOfSpace() + ROW_SIZE) < c.getPolje().length), +ROW_SIZE, transitions);

		return transitions;
	}

	/**
	 * Performs single transition and switches position of 2 elements in the puzzle.
	 * 
	 * @param config         Temporary order of elements
	 * @param t              Strategy for testing is neighbor index is valid
	 * @param neighborOffset Offset in an array to the neighbor
	 * @param transitions    List of neighbors for given configuration
	 */
	private void switchPlaces(KonfiguracijaSlagalice config, Predicate<KonfiguracijaSlagalice> t,
			int neighborOffset, List<Transition<KonfiguracijaSlagalice>> transitions) {

		if (t.test(config)) {
			int[] elements = config.getPolje();
			int spaceIndex = config.indexOfSpace();
			elements[spaceIndex] = elements[spaceIndex + neighborOffset];
			elements[spaceIndex + neighborOffset] = SPACE_VALUE;
			transitions.add(new Transition<KonfiguracijaSlagalice>(new KonfiguracijaSlagalice(elements), 1.0));
		}
	}

	@Override
	public boolean test(KonfiguracijaSlagalice t) {
		return Arrays.equals(solution, t.getPolje());
	}

}
