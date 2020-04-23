package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.elems.IElementVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Executes given {@link DocumentNode} and writes result to the
 * {@link RequestContext} output.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptEngine {

	/**
	 * Identifier for echo node stack.
	 */
	private static final String ECHO_STACK = "echo";
	/**
	 * Identifier for stack whose items are in reversed order.
	 */
	private static final String REVERSED_STACK = "reverse";
	/**
	 * Document to be executed.
	 */
	private DocumentNode documentNode;
	/**
	 * Context which contains parameters necessary for execution and
	 * {@link OutputStream} to write on.
	 */
	private RequestContext requestContext;
	/**
	 * Allows to work on multiple instances of stacks defined by its key.
	 */
	private ObjectMultistack multistack = new ObjectMultistack();

	/**
	 * Constructs instance of this engine.
	 * 
	 * @param documentNode   DocumentNode to be executed
	 * @param requestContext Context which contains parameters necessary for
	 *                       execution and {@link OutputStream} to write on.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Concrete implementation of {@link INodeVisitor} which defines actions that
	 * performs upon visiting specific Node subclass.
	 */
	private INodeVisitor nodeVisitor = new INodeVisitor() {

		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			String variable = node.getVariable().asText();
			ValueWrapper endExpression = new ValueWrapper(node.getEndExpression().asText());
			ValueWrapper stepValue = new ValueWrapper(
					(node.getStepExpression() == null) ? 1 : node.getStepExpression().asText());
			ValueWrapper currentValue = new ValueWrapper(node.getStartExpression().asText());
			multistack.push(variable, currentValue);

			while (multistack.peek(variable).numCompare(endExpression.getValue()) <= 0) {
				for (int i = 0; i < node.numberOfChildren(); i++) {
					node.getChild(i).accept(this);
				}
				// Increment by step value
				multistack.peek(variable).add(stepValue.getValue());
			}

			// Clear variable
			multistack.pop(variable);
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			for (Element element : node.getElements()) {
				element.accept(elementVisitor);
			}

			// Reverse elements from the stack
			while (!multistack.isEmpty(ECHO_STACK)) {
				var value = multistack.pop(ECHO_STACK);
				multistack.push(REVERSED_STACK, value);
			}

			// Write remaining values
			try {
				while (!multistack.isEmpty(REVERSED_STACK)) {
					String text = (multistack.pop(REVERSED_STACK).getValue().toString());
					// Text sanitization
					text = text.replaceAll("\\\\r", "\r");
					text = text.replaceAll("\\\\n", "\n");
					requestContext.write(text);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for (int i = 0; i < node.numberOfChildren(); i++) {
				node.getChild(i).accept(this);
			}
		}
	};

	/**
	 * Concrete implementation of {@link IElementVisitor} which defines actions that
	 * performs upon visiting specific ELement subclass.
	 */
	private IElementVisitor elementVisitor = new IElementVisitor() {

		@Override
		public void visitElementOperator(ElementOperator element) {
			Object arg2 = multistack.pop(ECHO_STACK).getValue();
			ValueWrapper arg1 = multistack.pop(ECHO_STACK);
			switch (element.asText()) {
			case "+":
				arg1.add(arg2);
				break;
			case "-":
				arg1.subtract(arg2);
				break;
			case "*":
				arg1.multiply(arg2);
				break;
			case "/":
				arg1.divide(arg2);
				break;
			}
			multistack.push(ECHO_STACK, arg1);
		}

		@Override
		public void visitElementFunction(ElementFunction element) {

			String function = element.asText();
			switch (function) {
			case "sin":
				Double x = Double.parseDouble(String.valueOf(multistack.pop(ECHO_STACK).getValue()));
				Double result = Math.sin(Math.toRadians(x));
				multistack.push(ECHO_STACK, new ValueWrapper(result));
				break;
			case "decfmt":
				String format = String.valueOf(multistack.pop(ECHO_STACK).getValue());
				DecimalFormat formatter = new DecimalFormat(format);
				Double number = Double.parseDouble(String.valueOf(multistack.pop(ECHO_STACK).getValue()));
				multistack.push(ECHO_STACK, new ValueWrapper(formatter.format(number)));
				break;
			case "dup":
				Object val = multistack.pop(ECHO_STACK).getValue();
				multistack.push(ECHO_STACK, new ValueWrapper(val));
				multistack.push(ECHO_STACK, new ValueWrapper(val));
				break;
			case "swap":
				var first = multistack.pop(ECHO_STACK);
				var second = multistack.pop(ECHO_STACK);
				multistack.push(ECHO_STACK, first);
				multistack.push(ECHO_STACK, second);
				break;
			case "setMimeType":
				String mime = String.valueOf(multistack.pop(ECHO_STACK).getValue());
				requestContext.setMimeType(mime);
				break;
			case "paramGet":
				getParameterFunction((name) -> requestContext.getParameter(name));
				break;
			case "pparamGet":
				getParameterFunction((name) -> requestContext.getPersistentParameter(name));
				break;
			case "tparamGet":
				getParameterFunction((name) -> requestContext.getTemporaryParameter(name));
				break;
			case "pparamSet":
				setParameterFunction((name, value) -> requestContext.setPersistentParameter(name, value));
				break;
			case "tparamSet":
				setParameterFunction((name, value) -> requestContext.setTemporaryParameter(name, value));
				break;
			case "pparamDel":
				deleteParameterFunction((name) -> requestContext.removePersistentParameter(name));
				break;
			case "tparamDel":
				deleteParameterFunction((name) -> requestContext.removeTemporaryParameter(name));
				break;
			}
		}

		@Override
		public void visitElementVariable(ElementVariable element) {
			Object value = multistack.peek(element.getName()).getValue();
			multistack.push(ECHO_STACK, new ValueWrapper(value));
		}

		@Override
		public void visitElementString(ElementString element) {
			multistack.push(ECHO_STACK, new ValueWrapper(element.getValue()));
		}

		@Override
		public void visitElementConstantInteger(ElementConstantInteger element) {
			multistack.push(ECHO_STACK, new ValueWrapper(element.getValue()));
		}

		@Override
		public void visitElementConstantDouble(ElementConstantDouble element) {
			multistack.push(ECHO_STACK, new ValueWrapper(element.getValue()));
		}

	};

	/**
	 * Deletes parameter from request context.
	 * 
	 * @param delete Strategy to for deletion
	 */
	private void deleteParameterFunction(Consumer<String> delete) {
		String name = String.valueOf(multistack.pop(ECHO_STACK).getValue());
		name = name.replaceAll("\"", "");
		delete.accept(name);
	}

	/**
	 * Adds new parameter to request context.
	 * 
	 * @param setter Strategy for setting new parameter
	 */
	private void setParameterFunction(BiConsumer<String, String> setter) {
		String name = String.valueOf(multistack.pop(ECHO_STACK).getValue());
		name = name.replaceAll("\"", "");
		String value = String.valueOf(multistack.pop(ECHO_STACK).getValue());
		setter.accept(name, value);
	}

	/**
	 * Retrieves parameter from request context.
	 * 
	 * @param setter Strategy for getting parameter
	 */
	private void getParameterFunction(Function<String, String> getter) {
		ValueWrapper defValue = multistack.pop(ECHO_STACK);
		String name = String.valueOf(multistack.pop(ECHO_STACK).getValue());
		name = name.replaceAll("\"", "");
		String param = getter.apply(name.strip());
		multistack.push(ECHO_STACK, (param == null) ? defValue : new ValueWrapper(param));
	}

	/**
	 * Triggers execution of this engine.
	 */
	public void execute() {
		documentNode.accept(nodeVisitor);
	}
}