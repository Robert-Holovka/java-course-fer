package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Simple version of lexical analyzer. Analyzer breaks down given document into
 * characters and groups them together into tokens of different types and
 * values.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Lexer {

	/**
	 * Represents all forms of whitespace.
	 */
	private static final String WHITESPACE = "\\s+";
	/**
	 * Backslash character.
	 */
	private static final char BACKSLASH = '\\';

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
	 * State of the lexer.
	 */
	private LexerState state;

	/**
	 * Creates new instance of this class in "BASIC" mode.
	 * 
	 * @param text Document to be processed in lexical analysis
	 * @throws {@link NullPointerException} if given text is <code>null</code>
	 *         reference.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Groups first set of unprocessed characters and returns it as an instance of
	 * the {@link Token} class.
	 * 
	 * @return Token with value(characters) and type
	 * @throws {@link LexerException} if processed characters do not represent any
	 *         group of valid token types
	 */
	public Token nextToken() {
		if (currentIndex > data.length) {
			throw new LexerException();
		}

		skipWhitespace();

		// Return EOF
		if (currentIndex == data.length) {
			token = new Token(TokenType.EOF, null);
			currentIndex++;
			return token;
		}

		if (state == LexerState.BASIC) {

			String word = extractWord();
			if (!word.isEmpty()) {
				token = new Token(TokenType.WORD, word);
				return token;
			}

			// If it's not word or digit, return as symbol
			char c = data[currentIndex];
			if (!Character.isDigit(c)) {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, c);
				return token;
			}

			// If it's not word or symbol, try parse number
			String number = extractNumber();
			long l;
			try {
				l = Long.parseLong(number);
				token = new Token(TokenType.NUMBER, l);
				return token;
			} catch (NumberFormatException e) {
				// It's something else that is not allowed
				throw new LexerException();
			}
		} else {
			// EXTENDED MODE 
			String word = "";
			char c = data[currentIndex];
			
			// Extract word
			while (!isWhitespace(c) && c != '#') {
				word += c;
				try {
					c = nextChar();
				} catch (IndexOutOfBoundsException e) {
					break;
				}
			}
			
			// Symbol 
			if (word.isEmpty()) {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, c);
				return token;
			}
			
			// Word
			token = new Token(TokenType.WORD, word);
			return token;
		}
	}

	/**
	 * Returns currently stored token.
	 * 
	 * @return Token token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets new state of this lexer.
	 * 
	 * @param state State of the {@link Lexer}
	 * @throws {@link NullPointerException} if given state is equal to
	 *         <code>null</code>
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Skips all occurrence of whitespace in the document till the next
	 * non-whitespace character.
	 */
	private void skipWhitespace() {
		while (currentIndex < data.length && isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
	}

	/**
	 * Compares given character with whitespace.
	 * 
	 * @param c character to be compared
	 * @return True if given character is some form of a whitespace, false otherwise
	 */
	private boolean isWhitespace(char c) {
		return Character.toString(c).matches(WHITESPACE);
	}

	/**
	 * Returns first unprocessed character from the document.
	 * 
	 * @return char character from the document
	 * @throws IndexOutOfBoundsException if all characters were processed
	 */
	private char nextChar() {
		currentIndex++;
		if (currentIndex >= data.length) {
			throw new IndexOutOfBoundsException();
		}
		return data[currentIndex];
	}

	/**
	 * Extracts first occurrence of a word from unprocessed part of the document.
	 * Word can only contain letters or character <code>'\'</code>.
	 * 
	 * @return String representation of a word
	 * @throws {@link LexerException} if word contains characters who are not
	 *         letters or character <code>'\'</code>
	 */
	private String extractWord() {
		String word = "";
		char c = data[currentIndex];

		while (Character.isLetter(c) || c == BACKSLASH) {

			int counter = 0;
			// Count and skip '\'
			while (currentIndex < data.length && c == BACKSLASH) {
				counter++;
				try {
					c = nextChar();
				} catch (IndexOutOfBoundsException e) {
					break;
				}

			}

			// Invalid escaping
			if (counter > 2) {
				throw new LexerException();
			}

			// Escaping '\'
			if (counter == 2) {
				word += BACKSLASH;
				if (isWhitespace(c)) {
					break;
				}
			}

			// Invalid escaping
			if (counter == 1 && !Character.isDigit(c)) {
				throw new LexerException();
			}

			word += data[currentIndex];
			try {
				c = nextChar();
			} catch (IndexOutOfBoundsException e) {
				break;
			}

		}
		return word;
	}

	/**
	 * Extracts first occurrence of an <code>integer</code> from unprocessed part of
	 * the document.
	 * 
	 * @return String representation of a number
	 */
	private String extractNumber() {
		String s = "";
		char c = data[currentIndex];

		while (Character.isDigit(c)) {
			s += c;
			try {
				c = nextChar();
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		return s;
	}
}