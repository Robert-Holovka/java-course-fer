package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Represents output of the {@link SmartScriptLexer}
 * 
 * @author Robert Holovka
 * @version 1.0
 *
 */
public class SmartScriptToken {

	/**
	 * Stores one of available token types defined in {@link SmartScriptTokenType}.
	 */
	private SmartScriptTokenType type;
	/**
	 * Stores value of a <code>token</code>
	 */
	private Object value;

	/**
	 * Creates new instance of a <code>token</code> defined by given
	 * <code>type</code> and <code>value</code>.
	 * 
	 * @param type  Type of the token.
	 * @param value Token value.
	 * @throws {@link NullPointerException} if given <code>type</code> is
	 *         <code>null</code> reference.
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		Objects.requireNonNull(type);
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns token value.
	 * 
	 * @return value of the token.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Returns token type.
	 * 
	 * @return type of the token.
	 */
	public SmartScriptTokenType getType() {
		return type;
	}
}
