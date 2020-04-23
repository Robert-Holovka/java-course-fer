package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception which occurs during parsing document.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * Creates new instance of this class.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Creates new instance of this class with defined message.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}
}
