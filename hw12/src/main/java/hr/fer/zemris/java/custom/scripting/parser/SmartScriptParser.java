package hr.fer.zemris.java.custom.scripting.parser;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Class responsible for building document tree model which contains parsed
 * tokens generated from {@link SmartScriptLexer}. Tree contains multiple
 * instances of class {@link Node}. Each <code>Node</code> can contain multiple
 * instances of class {@link Element}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptParser {

	/**
	 * Reference to a class responsible for generating/providing tokens on demand.
	 */
	private SmartScriptLexer lexer;
	/**
	 * Assistant in the tree construction.
	 */
	private Stack<Object> stack;
	/**
	 * Root node of the document.
	 */
	private DocumentNode documentNode;

	/**
	 * Creates a new instance of this class, initializes lexer and starts parsing
	 * immediately.
	 * 
	 * @param documentBody Document for whom this class will generate tree structure
	 * @throws {@link SmartScriptParserException} if given document is
	 *         <code>null</code> reference
	 */
	public SmartScriptParser(String documentBody) {
		if (documentBody == null) {
			throw new SmartScriptParserException();
		}
		lexer = new SmartScriptLexer(documentBody);
		stack = new Stack<>();
		parseDocument();
	}

	/**
	 * Switches states of the lexer based on the received tokens and has
	 * responsibility for maintaining correct form of document structure.
	 * 
	 */
	private void parseDocument() {
		documentNode = new DocumentNode();
		stack.push(documentNode);

		try {
			SmartScriptToken token = lexer.nextToken();
			SmartScriptTokenType type = token.getType();
			while (!type.equals(SmartScriptTokenType.EOF)) {
				if (type.equals(SmartScriptTokenType.BEGIN_TAG)) {
					lexer.setState(SmartScriptLexerState.TAG);
					token = lexer.nextToken();
					if (token.getValue().toString().toUpperCase().equals("FOR")) {
						parseForLoop();
						lexer.setState(SmartScriptLexerState.NORMAL);
					} else if (token.getValue().equals("=")) {
						parseEcho();
						lexer.setState(SmartScriptLexerState.NORMAL);
					} else if (token.getValue().toString().toUpperCase().equals("END")) {
						token = lexer.nextToken();
						if (token.getType().equals(SmartScriptTokenType.END_TAG)) {
							lexer.setState(SmartScriptLexerState.NORMAL);
							stack.pop();
						} else {
							throw new SmartScriptParserException();
						}
					} else {
						throw new SmartScriptParserException();
					}

				} else {
					parseText(token);
				}
				token = lexer.nextToken();
				type = token.getType();
			}

			if (!(stack.peek() instanceof DocumentNode)) {
				throw new SmartScriptParserException();
			}

		} catch (Exception e) {
			throw new SmartScriptParserException("Unable to parse given document.");
		}
	}

	/**
	 * Creates new {@link TextNode} from given token and adds it to the document
	 * model structure.
	 * 
	 * @param token Token which contains text
	 */
	private void parseText(SmartScriptToken token) {
		Node parent = (Node) stack.peek();
		String text = (String) token.getValue();
		parent.addChildNode(new TextNode(text));
	}

	/**
	 * Creates new {@link EchoNode} and stores its elements inside.
	 * 
	 */
	private void parseEcho() {
		ArrayList<Element> elements = new ArrayList<>();

		SmartScriptToken token = lexer.nextToken();
		SmartScriptTokenType type = token.getType();
		while (!type.equals(SmartScriptTokenType.END_TAG)) {

			switch (type) {
			case SYMBOL:
				if (token.getValue().equals("=")) {
					throw new SmartScriptParserException();
				}
				elements.add(new ElementOperator((String) token.getValue()));
				break;
			case NUMBER:
				elements.add(new ElementConstantInteger((int) token.getValue()));
				break;
			case DECIMAL:
				elements.add(new ElementConstantDouble((double) token.getValue()));
				break;
			case EOF:
				throw new SmartScriptParserException();
			default:
				String text = (String) token.getValue();
				// Text
				if (isVariable(text)) {
					elements.add(new ElementVariable(text));
				} else if (isFunction(text)) {
					elements.add(new ElementFunction(text.replaceFirst("@", "")));
				} else {
					elements.add(new ElementString(text));
				}
				break;
			}
			token = lexer.nextToken();
			type = token.getType();
		}

		Node parent = (Node) stack.peek();
		Element[] el = new Element[elements.size()];
		for (int i = 0; i < elements.size(); i++) {
			el[i] = (Element) elements.get(i);
		}
		parent.addChildNode(new EchoNode(el));
	}

	/**
	 * Checks whether given string is a valid function name.
	 * 
	 * @param text
	 * @return True if a given text is valid function name, false otherwise.
	 */
	private boolean isFunction(String text) {
		Pattern pattern = Pattern.compile("^@[a-zA-Z_$][a-zA-Z_0-9]*$");
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * Creates new {@link ForLoopNode} and stores its elements inside.
	 * 
	 */
	private void parseForLoop() {
		// Fetch variable
		String text = (String) lexer.nextToken().getValue();
		SmartScriptToken token;
		if (!isVariable(text)) {
			throw new SmartScriptParserException("1st element in for loop must be a variable.");
		}
		ElementVariable variable = new ElementVariable(text);

		// Fetch start expression
		token = lexer.nextToken();
		Element startExpression = parseExpression(token);

		// Fetch end expression
		token = lexer.nextToken();
		Element endExpression = parseExpression(token);

		// Fetch step expression
		token = lexer.nextToken();
		Element stepExpression = null;
		if (!token.getType().equals(SmartScriptTokenType.END_TAG)) {
			stepExpression = parseExpression(token);
			token = lexer.nextToken();
			// For tag must be closed with an end tag ("$}")
			if (!token.getType().equals(SmartScriptTokenType.END_TAG)) {
				throw new SmartScriptParserException();
			}
		}

		Node parent = (Node) stack.peek();
		Node child = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		parent.addChildNode(child);
		stack.push(child);
	}

	/**
	 * Extracts correct expression from the given token and saves it as an instance
	 * of a {@link Element} class.
	 * 
	 * @param token Token which contains expression
	 * @return Element stores expression
	 */
	private Element parseExpression(SmartScriptToken token) {
		Element expression = null;
		SmartScriptTokenType type = token.getType();
		if (type.equals(SmartScriptTokenType.DECIMAL)) {
			expression = new ElementConstantDouble((double) token.getValue());
		} else if (type.equals(SmartScriptTokenType.NUMBER)) {
			expression = new ElementConstantInteger((int) token.getValue());
		} else if (isVariable((String) token.getValue())) {
			expression = new ElementVariable((String) token.getValue());
		} else {

			String text = (String) token.getValue();
			Pattern pattern = Pattern.compile("^[\"][-]?[0-9]+[.][0-9]+[\"]$");
			Matcher matcher = pattern.matcher(text);

			// Check for decimals contained in string
			if (matcher.matches()) {
				text = text.replaceAll("\"", "");
				return new ElementConstantDouble(Double.parseDouble(text));
			}

			// Check for integers contained in string
			pattern = Pattern.compile("^[\"][-]?[0-9]+[\"]$");
			matcher = pattern.matcher(text);
			if (matcher.matches()) {
				text = text.replaceAll("\"", "");
				return new ElementConstantInteger(Integer.parseInt(text));
			}

			throw new SmartScriptParserException();
		}
		return expression;
	}

	/**
	 * Checks whether given string is a valid variable name.
	 * 
	 * @param text
	 * @return True if a given text is valid variable name, false otherwise.
	 */
	private boolean isVariable(String text) {
		Pattern pattern = Pattern.compile("^[a-zA-Z_$][a-zA-Z_0-9]*$");
		Matcher matcher = pattern.matcher(text);
		return matcher.matches();
	}

	/**
	 * Returns root of the document structure.
	 * 
	 * @return root of the document.
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}
}
