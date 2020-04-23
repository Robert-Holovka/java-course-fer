package hr.fer.zemris.java.hw06.shell.commands.massrename.parser;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.Lexer;
import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.LexerException;
import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.LexerState;
import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.Token;
import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.TokenType;

/**
 * Class responsible for parsing expression that defines new name of a file.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class NameBuilderParser {

	/**
	 * Lexical analyzer.
	 */
	private Lexer lexer;
	/**
	 * Strategy for building new name.
	 */
	private NameBuilder nameBuilder;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param expression Rules for creating new file name
	 */
	public NameBuilderParser(String expression) {
		lexer = new Lexer(expression);
		try {
			parse();
		} catch (LexerException e) {
			throw new ParserException("Could not parse given expression.");
		}
	}

	/**
	 * Parses given expression.
	 */
	private void parse() {
		List<NameBuilder> builders = new LinkedList<>();
		Token token;
		do {
			token = lexer.nextToken();
			if (token.getType() == TokenType.EOL)
				break;
			if (token.getType() == TokenType.BEGIN_TAG) {
				lexer.setState(LexerState.EXTENDED);
				continue;
			}
			if (token.getType() == TokenType.END_TAG) {
				lexer.setState(LexerState.BASIC);
				continue;
			}

			if (token.getType() == TokenType.TEXT) {
				builders.add(text(token.getValue()));
			} else {
				parseInstruction(token, builders);
			}
		} while (token.getType() != TokenType.EOL);

		// Construct final builder (composite)
		nameBuilder = (file, sb) -> {
			builders.forEach((builder) -> builder.execute(file, sb));
		};
	}

	/**
	 * Responsible for parsing instructions inside expression.
	 * 
	 * @param token    Lastly generated token
	 * @param builders List of strategies
	 */
	private void parseInstruction(Token token, List<NameBuilder> builders) {
		// First must be positive number
		if (token.getType() != TokenType.NUMBER || Integer.valueOf(token.getValue()) < 0) {
			throw new ParserException("Failed to parse given expression");
		}
		int index = Integer.parseInt(token.getValue());
		token = lexer.nextToken();

		// Group without padding
		if (token.getType() == TokenType.END_TAG) {
			lexer.setState(LexerState.BASIC);
			builders.add(group(index));
			return;
		}

		// Group with padding
		// Next character must be ','
		if (token.getType() != TokenType.SPECIAL_CHARACTERS) {
			throw new ParserException("Failed to parse given expression");
		}

		token = lexer.nextToken();

		// Second argument must be positive number
		if (token.getType() != TokenType.NUMBER || Integer.valueOf(token.getValue()) < 0) {
			throw new ParserException("Failed to parse given expression");
		}

		int secondArgument = Integer.parseInt(token.getValue());
		token = lexer.nextToken();

		if (secondArgument != 0) {
			builders.add(group(index, ' ', secondArgument));
		} else {
			if (token.getType() != TokenType.NUMBER || Integer.valueOf(token.getValue()) <= 0) {
				throw new ParserException("Failed to parse given expression");
			}
			int minWidth = Integer.parseInt(token.getValue());
			builders.add(group(index, '0', minWidth));
		}

		if (secondArgument == 0) {
			token = lexer.nextToken();
		}
		// Group without padding
		if (token.getType() == TokenType.END_TAG) {
			lexer.setState(LexerState.BASIC);
			return;
		}

		throw new ParserException();
	}

	/**
	 * Returns strategy for constructing new file name.
	 * 
	 * @return NameBuilder
	 */
	public NameBuilder getNameBuilder() {
		return nameBuilder;
	}

	/**
	 * Returns strategy for processing plain text from expression.
	 * 
	 * @param t text
	 * @return NameBuilder
	 */
	public static NameBuilder text(String t) {
		return (file, sb) -> sb.append(t);
	}

	/**
	 * Returns strategy for inserting result of a {@link RegularExpression} inside a
	 * new name of the file.
	 * 
	 * @param index Index of the matched group
	 * @return NameBuilder
	 */
	public static NameBuilder group(int index) {
		return (file, sb) -> sb.append(file.group(index));
	}

	/**
	 * Returns strategy for inserting result of a {@link RegularExpression} inside a
	 * new name of the file.
	 * 
	 * @param index    Index of the matched group
	 * @param padding  Character for padding
	 * @param minWidth Minimum group width
	 * @return NameBuilder
	 */
	public static NameBuilder group(int index, char padding, int minWidth) {
		return (file, sb) -> {
			int actualWidth = file.group(index).length();
			if (actualWidth < minWidth) {
				sb.append(String.valueOf(padding).repeat(minWidth - actualWidth));
			}
			sb.append(file.group(index));
		};
	}
}
