package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor for subclasses of Node class.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface INodeVisitor {

	/**
	 * Methods which {@link TextNode} calls when visited.
	 * 
	 * @param node
	 */
	void visitTextNode(TextNode node);

	/**
	 * Methods which {@link ForLoopNode} calls when visited.
	 * 
	 * @param node
	 */
	void visitForLoopNode(ForLoopNode node);

	/**
	 * Methods which {@link EchoNode} calls when visited.
	 * 
	 * @param node
	 */
	void visitEchoNode(EchoNode node);

	/**
	 * Methods which {@link DocumentNode} calls when visited.
	 * 
	 * @param node
	 */
	void visitDocumentNode(DocumentNode node);
}
