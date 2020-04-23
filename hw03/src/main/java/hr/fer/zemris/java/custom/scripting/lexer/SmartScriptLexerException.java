package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Represents exception which occurs during lexical analysis in {@link SmartScriptLexer}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * Creates new instance of this class.
	 */
	public SmartScriptLexerException() {
		super();
	}
	
	/**
	 * Creates new instance of this class with given message.
	 * 
	 * @param message Explanation why exception was thrown.
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}
}
