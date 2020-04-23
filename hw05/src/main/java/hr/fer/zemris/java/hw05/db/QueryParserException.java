package hr.fer.zemris.java.hw05.db;

/**
 * Represents exception which occurs while parsing invalid queries.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class QueryParserException extends RuntimeException {

	/**
	 * Constructs instance of this class.
	 */
	public QueryParserException() {

	}

	/**
	 * Constructs instance of this class defined by a given message.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public QueryParserException(String message) {
		super(message);
	}
}
