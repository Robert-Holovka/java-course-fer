package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Lexical analyzer. Analyzer breaks down given expression into characters and
 * groups them together into tokens of different types and values.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Lexer {

	/**
	 * Chunks of the document.
	 */
	private char[] data;
	/**
	 * Lastly formed token.
	 */
	private Token token;
	/**
	 * Index of the next unprocessed character.
	 */
	private int currentIndex;
	/**
	 * State of this lexer.
	 */
	private LexerState state;

	/**
	 * Creates new instance of this class.
	 * 
	 * @param text Expression to be processed in lexical analysis
	 * @throws NullPointerException if a given text is {@code null} reference.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Returns lastly evaluated token.
	 * 
	 * @return Token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Groups first set of unprocessed characters and returns it as an instance of
	 * the Token class.
	 * 
	 * @return Token with value(characters) and type
	 * @throws LexerException if processed characters do not represent any group of
	 *                        valid token types
	 */
	public Token nextToken() {
		if (currentIndex > data.length) {
			throw new LexerException();
		}
		// Return EOL
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOL, null);
			currentIndex++;
			return token;
		}

		char c = data[currentIndex];
		// Beginning of instruction
		if (c == '$') {
			if (data[currentIndex + 1] != '{') {
				throw new LexerException();
			}
			token = new Token(TokenType.BEGIN_TAG, "${");
			currentIndex += 2;
			return token;
		}

		// BASIC mode
		if (state == LexerState.BASIC) {
			token = new Token(TokenType.TEXT, extract((arg) -> arg != '$'));
			return token;
		}

		// EXTENDED MODE
		skipWhitespace();
		c = data[currentIndex];

		// End of instruction
		if (c == '}') {
			token = new Token(TokenType.END_TAG, "}");
			currentIndex++;
			return token;
		}

		// Extract special characters
		if (c == ',') {
			token = new Token(TokenType.SPECIAL_CHARACTERS, String.valueOf(c));
			currentIndex++;
			return token;
		}

		// Number
		if (Character.isDigit(c)) {
			if (c == '0') {
				token = new Token(TokenType.NUMBER, String.valueOf(c));
				currentIndex++;
			} else {
				token = new Token(TokenType.NUMBER, extract((arg) -> Character.isDigit(arg)));
			}
			return token;
		}

		// Illegal characters in extended mode
		throw new LexerException();
	}

	/**
	 * Extracts sequence of characters while given test evaluates true.
	 * 
	 * @param predicate Test for a specific character
	 * @return String sequence of characters that passed given test
	 */
	private String extract(Predicate<Character> predicate) {
		String s = "";
		while (currentIndex < data.length && predicate.test(data[currentIndex])) {
			s += data[currentIndex++];
		}
		return s;
	}

	/**
	 * Skips all occurrence of whitespace in the line till the next non-whitespace
	 * character.
	 */
	private void skipWhitespace() {
		while (currentIndex < data.length && Character.toString(data[currentIndex]).matches("\\s+")) {
			currentIndex++;
		}
	}

	/**
	 * Sets new state of this lexer to a given value.
	 * 
	 * @param state
	 */
	public void setState(LexerState state) {
		this.state = state;
	}

}