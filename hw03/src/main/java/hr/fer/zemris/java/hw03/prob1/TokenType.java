package hr.fer.zemris.java.hw03.prob1;

/**
 * Defines allowed {@link Token} types.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum TokenType {
	/**
	 * End of file
	 */
	EOF,
	/**
	 * A string of letters
	 */
	WORD,
	/**
	 * Number
	 */
	NUMBER,
	/**
	 * Special characters
	 */
	SYMBOL
}
