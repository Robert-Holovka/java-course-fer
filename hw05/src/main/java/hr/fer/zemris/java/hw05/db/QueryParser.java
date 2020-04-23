package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.Lexer;
import hr.fer.zemris.java.hw05.db.lexer.LexerException;
import hr.fer.zemris.java.hw05.db.lexer.Token;
import hr.fer.zemris.java.hw05.db.lexer.TokenType;

/**
 * Responsible for parsing given query and returning it as a list of
 * ConditionalExpression.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class QueryParser {

	/**
	 * Reference to Lexer.
	 */
	private Lexer lexer;
	/**
	 * List of a conditional expressions for some query.
	 */
	private LinkedList<ConditionalExpression> queries;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param query Query to be parsed
	 * @throws QueryParserException if given query is not valid
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new QueryParserException();
		}

		lexer = new Lexer(query);
		queries = new LinkedList<>();

		try {
			parseQuery();
		} catch (LexerException | QueryParserException e) {
			throw new QueryParserException("Invalid query!");
		}
	}

	/**
	 * Checks whether parsed query is direct. Query is considered as direct if it
	 * specifies following form: jmbag = "someID"
	 * 
	 * @return True if parsed query is direct, false otherwise.
	 */
	public boolean isDirectQuery() {
		if (queries.size() != 1)
			return false;

		ConditionalExpression query = queries.getFirst();
		return query.getFieldGetter() == FieldValueGetters.JMBAG
				&& query.getComparisonOperator() == ComparisonOperators.EQUALS;
	}

	/**
	 * Returns identifier of a student. This method can only be called if given
	 * query was direct.
	 * 
	 * @return String
	 * @throws IllegalStateException if parsed query is not direct
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException();
		}
		return queries.getFirst().getStringLiteral();
	}

	/**
	 * Returns list of a ConditionalExpression after parsing given query.F
	 * 
	 * @return List<ConditionalExpression>
	 */
	public List<ConditionalExpression> getQuery() {
		return queries;
	}

	/**
	 * Responsible for parsing given query and retrieving tokens from Lexer.
	 * 
	 * @throws QueryParserException if given query is not valid
	 */
	private void parseQuery() {
		String tokenValue = null;
		do {
			queries.add(new ConditionalExpression(parseArgument(), parseOperator(), parseStringLiteral()));
			tokenValue = lexer.nextToken().getValue();
		} while ((tokenValue) != null && (tokenValue.toUpperCase().equals("AND")));

		if (lexer.getToken().getType() != TokenType.EOL) {
			throw new QueryParserException();
		}
	}

	/**
	 * Responsible for parsing string enclosed by quotes.
	 * 
	 * @return String without quotes
	 * @throws QueryParserException if next token is not valid string literal
	 */
	private String parseStringLiteral() {
		Token token = lexer.nextToken();

		if (token.getType() != TokenType.STRING_LITERAL) {
			throw new QueryParserException();
		}

		String literal = token.getValue();
		int countWildcards = literal.length() - literal.replace("*", "").length();
		if (countWildcards > 1) {
			throw new QueryParserException();
		}

		return literal;
	}

	/**
	 * Responsible for parsing some of valid operators defined in
	 * ComparisonOperators.
	 * 
	 * @return IComparisonOperator concrete implementation of a specific operator
	 * @throws QueryParserException if next token is not valid operator
	 */
	private IComparisonOperator parseOperator() {
		Token token = lexer.nextToken();
		String operator = token.getValue();

		if ((token.getType() != TokenType.WORD) && (token.getType() != TokenType.SPECIAL_CHARACTERS)) {
			throw new QueryParserException();
		}

		switch (operator) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException();
		}
	}

	/**
	 * Responsible for parsing arguments. Valid arguments are jmbag, firstName and
	 * lastName.
	 * 
	 * @return IFieldValueGetter getter for argument
	 * @throws QueryParserException if next token is not valid argument
	 */
	private IFieldValueGetter parseArgument() {
		Token token = lexer.nextToken();

		if ((token.getType() != TokenType.WORD)) {
			throw new QueryParserException();
		}

		String argument = token.getValue();
		switch (argument) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		default:
			throw new QueryParserException();
		}
	}

}
