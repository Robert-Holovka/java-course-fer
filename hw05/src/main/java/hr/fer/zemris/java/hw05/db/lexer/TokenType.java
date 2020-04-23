package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Defines allowed Token types.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum TokenType {
	/**
	 * End of line
	 */
	EOL,
	/**
	 * Word enclosed by quotes
	 */
	STRING_LITERAL,
	/**
	 * Plain word
	 */
	WORD,
	/**
	 * Special characters
	 */
	SPECIAL_CHARACTERS
}
