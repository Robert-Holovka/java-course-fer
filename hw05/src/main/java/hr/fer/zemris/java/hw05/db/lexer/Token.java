package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Represents output of the Token.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Token {

	/**
	 * Stores one of available token types defined in TokenType.
	 */
	private TokenType type;
	/**
	 * Stores value of a token.
	 */
	private String value;

	/**
	 * Creates new instance of a token defined by given type and value.
	 * 
	 * @param type  Type of the token.
	 * @param value Token value.
	 * @throws NullPointerException if given type is <code>null</code> reference.
	 */
	public Token(TokenType type, String value) {
		Objects.requireNonNull(type);
		this.type = type;
		this.value = value;
	}

	/**
	 * Returns token value.
	 * 
	 * @return value of the token.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Returns token type.
	 * 
	 * @return type of the token.
	 */
	public TokenType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Token))
			return false;
		Token other = (Token) obj;
		return type == other.type && Objects.equals(value, other.value);
	}

}