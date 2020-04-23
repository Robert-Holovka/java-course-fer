package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Builds document node from a given file and writes back to the standard output
 * file content generated from document.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class TreeWriter {

	/**
	 * Concrete implementation of a {@link INodeVisitor} which defined actions upon
	 * visiting specific Node.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	public static class WriterVisitor implements INodeVisitor {

		private StringBuilder sb;

		public WriterVisitor() {
			sb = new StringBuilder();
		}

		@Override
		public void visitTextNode(TextNode node) {
			sb.append(node.getText());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			sb.append("{$ FOR ");
			sb.append(" " + node.getVariable());
			sb.append(" " + node.getStartExpression());
			sb.append(" " + node.getEndExpression());
			sb.append(" " + node.getStepExpression());
			sb.append(" $}");

			for (int i = 0, size = node.numberOfChildren(); i < size; i++) {
				node.getChild(i).accept(this);
			}

			sb.append("{$ END $}");
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			sb.append("{$=");
			for (Element e : node.getElements()) {
				sb.append(" " + e);
			}
			sb.append(" $}");
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			sb.setLength(0);
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
			System.out.println(sb.toString());
		}

	}

	/**
	 * Entry point of the program. Expected single path to the file.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected file name as only argument");
			return;
		}

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Could not open given file.");
			return;
		}

		SmartScriptParser p;
		try {
			p = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Could not parse given document.");
			return;
		}
		WriterVisitor visitor = new WriterVisitor();
		p.getDocumentNode().accept(visitor);
	}
}
