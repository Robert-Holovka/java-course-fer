package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Demonstrates usage of {@link SmartScriptParser}. Program receives single
 * argument from the command line which is a path to the document for which tree
 * structure will be constructed.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptTester {

	/**
	 * Entry point of the program. Program accepts single argument that represents
	 * path to the file.
	 * 
	 * @param args Path to the file
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments");
			return;
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		// content of docBody

		// create again
		try {
			parser = new SmartScriptParser(originalDocumentBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		System.out.printf("%n%nAgain%n%n");

		document = parser.getDocumentNode();
		originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody);

	}

	/**
	 * Creates original file content from document model.
	 * 
	 * @param document Document tree model
	 * @return String original file content
	 */
	private static String createOriginalDocumentBody(DocumentNode document) {
		String s = "";
		for (int i = 0; i < document.numberOfChildren(); i++) {
			Node node = document.getChild(i);
			if (node instanceof TextNode) {
				s += ((TextNode) node).getText();
			}

			if (node instanceof EchoNode) {
				s += " " + "{$= ";
				for (Element e : ((EchoNode) node).getElements()) {
					s += printByType(e);
				}
				s += " " + " $}";
			}
			if (node instanceof ForLoopNode) {
				s += "{$ FOR ";
				s += ((ForLoopNode) node).getVariable().getName();
				s += printByType(((ForLoopNode) node).getStartExpression());
				s += printByType(((ForLoopNode) node).getEndExpression());
				s += printByType(((ForLoopNode) node).getStepExpression());
				s += " " + " $}";

				for (int j = 0, size = node.numberOfChildren(); j < size; j++) {
					// TODO duplicirani kod
					Node temp = node.getChild(j);
					if (temp instanceof TextNode) {
						s += ((TextNode) temp).getText();
					}

					if (temp instanceof EchoNode) {
						s += "{$= ";
						for (Element e : ((EchoNode) temp).getElements()) {
							s += printByType(e);
						}
						s += " " + " $}";
					}
				}
				s += "{$ END $}";
			}
		}
		return s;
	}

	/**
	 * Extracts correct child of {@link Element} class and returns its value.
	 * 
	 * @param e Element which stores value
	 * @return String value in text format
	 */
	private static String printByType(Element e) {
		String s = "";
		if (e instanceof ElementConstantDouble) {
			s += " " + ((ElementConstantDouble) e).getValue();
		}
		if (e instanceof ElementConstantInteger) {
			s += " " + ((ElementConstantInteger) e).getValue();
		}
		if (e instanceof ElementFunction) {
			s += " @" + ((ElementFunction) e).getName();
		}
		if (e instanceof ElementOperator) {
			s += " " + ((ElementOperator) e).getSymbol();
		}
		if (e instanceof ElementString) {
			s += " " + ((ElementString) e).getValue();
		}
		if (e instanceof ElementVariable) {
			s += " " + ((ElementVariable) e).getName();
		}

		return s;
	}

}
