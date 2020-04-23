package hr.fer.zemris.java.webserver;

/**
 * Exception which is thrown when HTTP request is not valid.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {

	/**
	 * Constructs new instance of this exception.
	 */
	public InvalidRequestException() {
		super();
	}

	/**
	 * Constructs new instance of this exception.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public InvalidRequestException(String message) {
		super(message);
	}
}
