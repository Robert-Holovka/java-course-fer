package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * Concrete implementation of {@link Tester} interface.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
class EvenIntegerTester implements Tester {

	/**
	 * Tests given object whether is it even number stored in <code>Integer</code>
	 * type.
	 * 
	 * @param obj Object to be tested
	 * @return True if object passed test, false otherwise.
	 */
	public boolean test(Object obj) {

		if (!(obj instanceof Integer))
			return false;

		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

}