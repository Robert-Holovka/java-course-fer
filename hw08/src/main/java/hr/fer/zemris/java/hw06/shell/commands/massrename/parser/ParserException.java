package hr.fer.zemris.java.hw06.shell.commands.massrename.parser;

/**
 * Exception that can occur when parsing expressions.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class ParserException extends RuntimeException {

	/**
	 * Creates new instance of this class.
	 */
	public ParserException() {
		super();
	}

	/**
	 * Creates new instance of this class with defined message.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public ParserException(String message) {
		super(message);
	}
}
