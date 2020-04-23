package hr.fer.zemris.java.hw05.db;

/**
 * Functional interface that defines method for retrieving attributes from
 * student records.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface IFieldValueGetter {
	/**
	 * Returns specific attribute form record
	 * 
	 * @param record StudentRecord
	 * @return attribute
	 */
	String get(StudentRecord record);
}
