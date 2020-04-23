package hr.fer.zemris.java.hw05.db;

/**
 * Storage of concrete implementations for getters. Getters know how to access
 * some attributes from StudentRecord.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class FieldValueGetters {

	/**
	 * Getter for first name of a student.
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	/**
	 * Getter for last name of a student.
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;
	/**
	 * Getter for identifier of a student.
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;

}
