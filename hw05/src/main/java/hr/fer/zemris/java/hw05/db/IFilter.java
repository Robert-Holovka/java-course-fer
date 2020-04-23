package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface that defines method for filtering student records.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface IFilter {

	/**
	 * Checks whether given record is acceptable.
	 * 
	 * @param record StudentRecord
	 * @return True if record satisfies some criteria, false otherwise
	 */
	boolean accepts(StudentRecord record);
}
