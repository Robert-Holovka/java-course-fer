package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface that defines method for checking if given values match
 * some pattern.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface IComparisonOperator {
	/**
	 * Compares given values.
	 * 
	 * @param value1 String
	 * @param value2 String
	 * @return True if both values match some defined relationship, false otherwise
	 */
	boolean satisfied(String value1, String value2);
}
