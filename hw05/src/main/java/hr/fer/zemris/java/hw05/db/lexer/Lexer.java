package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Lexical analyzer. Analyzer breaks down given document into characters and
 * groups them together into tokens of different types and values.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Lexer {

	/**
	 * List of characters that this lexer treats as special.
	 */
	private static final List<Character> specialCharacters = Arrays.asList('<', '=', '>');

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
	 * Creates new instance of this class.
	 * 
	 * @param text Document to be processed in lexical analysis
	 * @throws NullPointerException if a given text is {@code null} reference.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
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

		skipWhitespace();

		// Return EOL
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOL, null);
			currentIndex++;
			return token;
		}

		token = findNextToken();
		return token;
	}

	/**
	 * Extracts next group of characters that satisfies some type of a Token.
	 * 
	 * @return Token
	 * @throws LexerException if processed characters do not represent any group of
	 *                        valid token types
	 */
	private Token findNextToken() {
		char c = data[currentIndex];

		// Extract string literal
		if (c == '\"') {
			// Skip beginning quote
			currentIndex++;
			token = new Token(TokenType.STRING_LITERAL, extract((arg) -> arg != '\"'));
			// Skip closing quote
			currentIndex++;
			return token;
		}

		// Extract word
		if (Character.isLetter(c)) {
			return new Token(TokenType.WORD, extract((arg) -> Character.isLetter(arg)));
		}

		// Extract special characters
		if (specialCharacters.contains(c)) {
			return new Token(TokenType.SPECIAL_CHARACTERS, extract((arg) -> specialCharacters.contains(arg)));
		}

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

}