package hr.fer.zemris.java.hw03.prob1;

/**
 * Defines allowed states of the {@link Lexer}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum LexerState {
	/**
	 * Normal mode: numbers, text and symbols are separate groups of tokens.
	 */
	BASIC,
	/**
	 * Extended mode: everything is text except symbol '#'
	 */
	EXTENDED
}
