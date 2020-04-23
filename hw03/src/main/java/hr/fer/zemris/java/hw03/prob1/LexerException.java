package hr.fer.zemris.java.hw03.prob1;

/**
 * Represents exception which occurs during lexical analysis.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LexerException extends RuntimeException {

	/**
	 * Creates new instance of this class.
	 */
	public LexerException() {
	}

	/**
	 * Creates new instance of this class with given message.
	 * 
	 * @param message Explanation why exception was thrown.
	 */
	public LexerException(String message) {
		super(message);
	}

}
