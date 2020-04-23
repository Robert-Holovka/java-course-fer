package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {

	@Test
	public void constructNullReference() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(null));
	}

	@Test
	public void constructEmptyBody() {
		SmartScriptParser parser = new SmartScriptParser("");
		Node expected = new DocumentNode();
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopIntegerExpressionsWithoutStep() {
		SmartScriptParser parser = new SmartScriptParser("{$ foR i -1 10 $}{$END$}");

		Node expected = new DocumentNode();

		ElementVariable variable = new ElementVariable("i");
		Element startExpression = new ElementConstantInteger(-1);
		Element endExpression = new ElementConstantInteger(10);

		expected.addChildNode(new ForLoopNode(variable, startExpression, endExpression));
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopIntegerExpressionsWithStep() {
		SmartScriptParser parser = new SmartScriptParser("{$ \n \r FOR i-1 10 200 \t $}{$END$}");

		Node expected = new DocumentNode();

		ElementVariable variable = new ElementVariable("i");
		Element startExpression = new ElementConstantInteger(-1);
		Element endExpression = new ElementConstantInteger(10);
		Element stepExpression = new ElementConstantInteger(200);

		expected.addChildNode(new ForLoopNode(variable, startExpression, endExpression, stepExpression));
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopDecimalExpressions() {
		SmartScriptParser parser = new SmartScriptParser("{$ \n \r FOR i35_3-1.25 score-200.3 \t $}{$END$}");

		Node expected = new DocumentNode();

		ElementVariable variable = new ElementVariable("i35_3");
		Element startExpression = new ElementConstantDouble(-1.25);
		Element endExpression = new ElementVariable("score");
		Element stepExpression = new ElementConstantDouble(-200.3);

		expected.addChildNode(new ForLoopNode(variable, startExpression, endExpression, stepExpression));
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopNumbersInStringExpression() {
		SmartScriptParser parser = new SmartScriptParser("{$ \n \r FOR i35_3-1.25\"10.3\"\"-200\" \t $}{$END$}");

		Node expected = new DocumentNode();

		ElementVariable variable = new ElementVariable("i35_3");
		Element startExpression = new ElementConstantDouble(-1.25);
		Element endExpression = new ElementConstantDouble(10.3);
		Element stepExpression = new ElementConstantInteger(-200);

		expected.addChildNode(new ForLoopNode(variable, startExpression, endExpression, stepExpression));
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopComplexExample() {
		SmartScriptParser parser = new SmartScriptParser("{$ FOR i-1.35bbb\"1\" $}{$END$}");

		Node expected = new DocumentNode();

		ElementVariable variable = new ElementVariable("i");
		Element startExpression = new ElementConstantDouble(-1.35);
		Element endExpression = new ElementVariable("bbb");
		Element stepExpression = new ElementConstantInteger(1);

		expected.addChildNode(new ForLoopNode(variable, startExpression, endExpression, stepExpression));
		assertEquals(expected, parser.getDocumentNode());
	}

	@Test
	public void forLoopInvalidVariableName() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR 3 1 10 1 $}"));
	}

	@Test
	public void forLoopInvalidVariableName2() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR * \"1\" -10 \"1\" $}"));
	}

	@Test
	public void forLoopFunctionInside() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year @sin 10 $}"));
	}

	@Test
	public void forLoopTooManyArguments() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year 1 10 \"1\" \"10\" $}"));
	}

	@Test
	public void forLoopTooFewArguments() {
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser("{$ FOR year $}"));
	}
	
	@Test
	public void echoComplexDemo() {
		
	}
	
	@Test
	public void twoPartDocument() {
		SmartScriptParser parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");

		Node expected = new DocumentNode();
		expected.addChildNode(new TextNode("Example {$=1$}. Now actually write one "));
		
		Element[] elements = new Element[2];
		elements[0] = new ElementConstantInteger(1);
		expected.addChildNode(new EchoNode(elements));

		assertEquals(expected, parser.getDocumentNode());
		
	}

}
