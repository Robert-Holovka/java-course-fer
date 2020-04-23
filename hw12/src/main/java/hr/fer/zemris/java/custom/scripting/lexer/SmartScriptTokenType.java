package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defines allowed {@link SmartScriptToken} types.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public enum SmartScriptTokenType {
	/**
	 * Integer.
	 */
	NUMBER,
	/**
	 * Number in decimal form.
	 */
	DECIMAL,
	/**
	 * A string of letters.
	 */
	TEXT,
	/**
	 * Special character.
	 */
	SYMBOL,
	/**
	 * Defines function token.
	 */
	FUNCTION,
	/**
	 * Defines variable token.
	 */
	VARIABLE,
	/**
	 * Defines beginning of a tag.
	 */
	BEGIN_TAG,
	/**
	 * Defines end of a tag.
	 */
	END_TAG,
	/**
	 * End of file.
	 */
	EOF
}
