package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

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
	 * Number
	 */
	NUMBER,
	/**
	 * Everything in normal mode is treated as text
	 */
	TEXT,
	/**
	 * Special characters
	 */
	SPECIAL_CHARACTERS,
	/**
	 * Beginning of instruction.
	 */
	BEGIN_TAG,
	/**
	 * Ending of instruction.
	 */
	END_TAG
}
