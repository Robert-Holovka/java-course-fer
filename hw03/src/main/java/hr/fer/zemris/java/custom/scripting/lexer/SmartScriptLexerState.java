package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defines allowed states of the {@link SmartScriptLexer}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum SmartScriptLexerState {
	/**
	 * Normal mode: everything is treated as text.
	 */
	NORMAL,
	/**
	 * Extended mode: lexical analysis inside tags.
	 */
	TAG
}