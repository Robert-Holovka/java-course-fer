package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

/**
 * Defines allowed states of the Lexer.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum LexerState {
	/**
	 * Normal mode: everything is treated as text.
	 */
	BASIC,
	/**
	 * Extended mode: lexical analysis inside instructions.
	 */
	EXTENDED
}