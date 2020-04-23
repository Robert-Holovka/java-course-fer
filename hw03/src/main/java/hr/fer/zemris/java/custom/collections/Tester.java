package hr.fer.zemris.java.custom.collections;

/**
 * Defines method for testing whether object is acceptable or not.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface Tester {

	/**
	 * Tests if given object meets some certain conditions specified by
	 * implementation of this method.
	 * 
	 * @param obj Object to be tested.
	 * @return True if given object is acceptable, false otherwise.
	 */
	boolean test(Object obj);
}
