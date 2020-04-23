package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.hw03.prob1.Lexer;
import hr.fer.zemris.java.hw03.prob1.Token;

/**
 * Class represents a lexical analyzer. Analyzer breaks down given document into
 * characters and groups them together into tokens of different types and
 * values.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptLexer {

	/**
	 * Backslash character.
	 */
	private static final char BACKSLASH = '\\';
	/**
	 * Represents all forms of whitespace.
	 */
	private static final String WHITESPACE = "\\s+";
	/**
	 * String which defines begging of a tag.
	 */
	private static final String BEGIN_TAG = "{$";
	/**
	 * String which defines end of a tag.
	 */
	private static final String END_TAG = "$}";
	/**
	 * An array of characters with special meaning.
	 */
	private static final char[] SYMBOLS = { '+', '-', '*', '/', '^', '=', '@' };

	/**
	 * Chunks of the document.
	 */
	private char[] data;
	/**
	 * Lastly formed token.
	 */
	private SmartScriptToken token;
	/**
	 * Index of the next unprocessed character.
	 */
	private int currentIndex;
	/**
	 * State of the lexer.
	 */
	private SmartScriptLexerState state;

	/**
	 * Creates new instance of this class in "NORMAL" mode.
	 * 
	 * @param text Document to be processed in lexical analysis
	 * @throws {@link NullPointerException} if given text is <code>null</code>
	 *         reference.
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text);
		data = text.toCharArray();
		state = SmartScriptLexerState.NORMAL;
	}

	/**
	 * Groups first set of unprocessed characters and returns it as an instance of
	 * the {@link SmartScriptToken} class.
	 * 
	 * @return SmartScriptToken with value(characters) and type
	 * @throws {@link SmartScriptLexerException} if processed characters do not
	 *         represent any group of valid token types
	 */
	public SmartScriptToken nextToken() {
		if (currentIndex > data.length) {
			throw new SmartScriptLexerException();
		}

		// EOF
		if (currentIndex == data.length) {
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			currentIndex++;
			return token;
		}

		if (state == SmartScriptLexerState.NORMAL) {
			String text = extractTextOutside();

			if (text.equals(BEGIN_TAG)) {
				token = new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, null);
				return token;
			}

			if (!text.isEmpty()) {
				token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
				return token;
			}

			// Token in "non-tag" mode can only be beginning tag or text
			throw new SmartScriptLexerException();
		} else { // "TAG" mode
			String text = extractTextFromTag();

			if (text.equals(END_TAG)) {
				token = new SmartScriptToken(SmartScriptTokenType.END_TAG, null);
				return token;
			}

			if (text.length() == 1) {
				char c = text.toCharArray()[0];
				if (isSymbol(c)) {
					token = new SmartScriptToken(SmartScriptTokenType.SYMBOL, text);
					currentIndex++;
					return token;
				} else if (Character.isDigit(c)) {
					token = new SmartScriptToken(SmartScriptTokenType.NUMBER, Integer.parseInt(String.valueOf(c)));
					return token;
				}
			}

			if (text.contains("\"")) {
				token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
				return token;
			}

			if (text.contains(".")) {
				try {
					double l = Double.parseDouble(text);
					token = new SmartScriptToken(SmartScriptTokenType.DECIMAL, l);
					return token;
				} catch (NumberFormatException e) {
					throw new SmartScriptLexerException();
				}
			}

			try {
				int n = Integer.parseInt(text);
				token = new SmartScriptToken(SmartScriptTokenType.NUMBER, n);
				return token;
			} catch (NumberFormatException e) {
				token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
				return token;
			}

		}
	}

	/**
	 * Returns currently stored token.
	 * 
	 * @return SmartScriptToken token
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Sets new state of this lexer.
	 * 
	 * @param state State of the {@link SmartScriptLexer}
	 * @throws {@link NullPointerException} if given state is equal to
	 *         <code>null</code>
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Extracts text from the document when this lexer is in tag mode.
	 * 
	 * @return String extracted text
	 */
	private String extractTextFromTag() {
		String text = "";
		skipWhitespace();
		
		while (currentIndex >= 0 && currentIndex < data.length) {

			// Return if text size is equal to two and it as a beginning tag
			boolean isEndTag = isEndTag();
			if (isEndTag && text.isEmpty()) {
				text = text + data[currentIndex] + data[currentIndex + 1];
				currentIndex += 2;
				return text;
			}

			if (isEndTag && !text.isEmpty()) {
				return text;
			}
			char c = data[currentIndex];
			if (isWhitespace(c)) {
				break;
			}

			if (text.isEmpty()) {
				if (c == '-') {
					text = extractNumber(c);
					if (text.isEmpty()) {
						text += c;
						return text;
					} else {
						return text;
					}
				}

				// Return symbol
				if (isSymbol(c)) {
					text += c;
					if (c != '@') {
						return text;
					} else {
						currentIndex++;
						continue;
					}
				}

				if (Character.isDigit(c)) {
					text = extractNumber(c);
					if (!text.isEmpty()) {
						return text;
					}
				}

				if (c == '"') {
					text = extractString(c);
					if (!text.isEmpty()) {
						return text;
					}
				}

			}
			// Text is not empty
			if (isSymbol(c)) {
				return text;
			}

			if (c == '-' || c == '"' || c == '@') {
				return text;
			}

			text += c;
			currentIndex++;
		}

		return text;
	}

	/**
	 * Extracts string enclosed by '"' characters.
	 * 
	 * @param c First character after opening quote
	 * @return string in pure form
	 */
	private String extractString(char c) {
		String text = String.valueOf(c);
		int index = 0;
		while ((index + currentIndex + 1) < data.length) {
			c = data[currentIndex + ++index];
			text += c;
			if (c == '"') {
				break;
			}
		}
		currentIndex += index + 1;
		return text;
	}

	/**
	 * Extracts first occurrence of a number(integer or decimal) from unprocessed
	 * part of the document.
	 * 
	 * @return String representation of a number
	 */
	private String extractNumber(char c) {
		String text = String.valueOf(c);
		int index = 0;

		while ((index + currentIndex + 1) < data.length) {
			c = data[currentIndex + ++index];
			if (Character.isDigit(c) || c == '.') {
				text += c;
			} else {
				break;
			}
		}

		currentIndex += index;
		return text;
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
	 * Checks whether given character has a special meaning.
	 * 
	 * @param c Character to be tested
	 * @return True if given character is special, false otherwise
	 */
	private boolean isSymbol(char c) {
		for (char symbol : SYMBOLS) {
			if (c == symbol) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Extracts text from the document when this lexer is non-tag mode.
	 * 
	 * @return String extracted text
	 */
	private String extractTextOutside() {
		String text = "";
		char c;

		while (currentIndex < data.length) {

			// Return if text size is equal to two and it as a beginning tag
			boolean isBeginTag = isBeginTag();
			if (isBeginTag && text.isEmpty()) {
				text = text + data[currentIndex] + data[currentIndex + 1];
				currentIndex += 2;
				return text;
			}
			if (isBeginTag && !text.isEmpty()) {
				return text;
			}

			// Check for escaping characters
			int counter = countBackslashChars(0);
			// No escape characters
			if (counter == 0) {
				try {
					c = data[currentIndex];
					// If next string is equal to begin tag, skip it and return found text
					if (c == '{' && data[currentIndex + 1] == '$') {
						break;
					} else {
						// Search for more text
						text += c;
						currentIndex++;
						continue;
					}
				} catch (IndexOutOfBoundsException e) {
					// EOF
					break;
				}
			} else { // Escape characters found
				text = handleBackslashChars(counter, text);
			}
		}

		return text;
	}

	/**
	 * Returns new text with correct content after escaping or throws exception if
	 * escaping rules were violated.
	 * 
	 * @param counter Number of backslash characters
	 * @param text    Piece of document
	 * @return String new text with correct representation
	 * @throws {@link SmartScriptLexerException} if escaping rules were violated in
	 *         given text
	 */
	private String handleBackslashChars(int counter, String text) {
		// Even number of backslash characters
		if (counter > 0 && counter % 2 == 0) {
			for (int i = 0; i < counter / 2; i++) {
				text += data[currentIndex];
				currentIndex += 2;
			}
		}

		// Odd number of backslash characters
		char c;
		if (counter > 0 && (counter % 2 == 1)) {
			for (int i = 0; i < counter / 2; i++) {
				text += data[currentIndex];
				currentIndex += 2;
			}

			try {
				c = data[++currentIndex];
			} catch (IndexOutOfBoundsException e) {
				// Invalid text, after odd number of backslashes must come character '{', not
				// EOF
				throw new SmartScriptLexerException();
			}
			// Escape character '{'
			if (c == '{') {
				text += c;
				currentIndex++;
			} else {
				// Invalid text, after odd number of backslashes must come character '{'
				throw new SmartScriptLexerException();
			}
		}

		return text;
	}

	/**
	 * Returns number of successive backslash characters starting from given offset
	 * from lastly generated token.
	 * 
	 * @param offset Position after lastly generated token
	 * @return Number of successive backslash characters
	 */
	private int countBackslashChars(int offset) {
		int counter = 0;
		if (currentIndex >= data.length) {
			return counter;
		}

		char c = data[currentIndex];
		while (c == BACKSLASH) {
			try {
				counter++;
				c = data[currentIndex + counter + offset];
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		return counter;
	}

	/**
	 * Checks if the next token has value of an end tag.
	 * 
	 * @return True if next token is end tag, false otherwise
	 */
	private boolean isEndTag() {
		if (data[currentIndex] == '$') {
			try {
				return data[currentIndex + 1] == '}';
			} catch (IndexOutOfBoundsException e) {
			}
		}
		return false;
	}

	/**
	 * Checks if the next token has value of a beginning tag.
	 * 
	 * @return True if next token is begin tag, false otherwise
	 */
	private boolean isBeginTag() {
		if (data[currentIndex] == '{') {
			try {
				return data[currentIndex + 1] == '$';
			} catch (IndexOutOfBoundsException e) {
			}
		}
		return false;
	}
}
