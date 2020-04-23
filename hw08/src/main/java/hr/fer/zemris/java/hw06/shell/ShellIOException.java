package hr.fer.zemris.java.hw06.shell;

/**
 * Represents exception which occurs during reading/writing from Environment.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ShellIOException extends RuntimeException {

	/**
	 * Constructs default instance of this class.
	 */
	public ShellIOException() {
	}

	/**
	 * Constructs default instance of this class defined by a given message.
	 * 
	 * @param message Reason why exception has been thrown
	 */
	public ShellIOException(String message) {
		super(message);
	}
}
