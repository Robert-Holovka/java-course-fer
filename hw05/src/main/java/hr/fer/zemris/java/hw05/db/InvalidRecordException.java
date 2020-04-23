package hr.fer.zemris.java.hw05.db;

/**
 * Exception which occurs when invalid student record has been inserted in
 * StudentDatabase.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InvalidRecordException extends RuntimeException {

	/**
	 * Constructs new instance of this class.
	 */
	public InvalidRecordException() {
		super();
	}

	/**
	 * Constructs new instance of this class defined by a given message.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public InvalidRecordException(String message) {
		super(message);
	}
}
