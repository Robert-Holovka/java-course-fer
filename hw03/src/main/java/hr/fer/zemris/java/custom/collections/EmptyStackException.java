package hr.fer.zemris.java.custom.collections;

/**
 * Class <code>EmptyStackException</code> represents an exception that occurs
 * when some methods were called on an empty stack.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * Creates instance of <code>EmptyStackException</code> class.
	 */
	public EmptyStackException() {
		this("");
	}

	/**
	 * Creates instance of <code>EmptyStackException</code> class with specified
	 * message.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Creates instance of <code>EmptyStackException</code> class with specified
	 * cause.
	 * 
	 * @param cause Reason why exception has been thrown
	 */
	public EmptyStackException(Throwable cause) {
		this("", cause);
	}

	/**
	 * Creates instance of <code>EmptyStackException</code> class with specified
	 * message and cause.
	 * 
	 * @param message Explanation why exception has been thrown
	 * @param cause   Reason why exception has been thrown
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
