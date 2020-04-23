package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.Objects;

import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Contains methods for performing case change operations on a selected text.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CaseOperations implements IAction {

	/**
	 * Code for upper-case operation.
	 */
	public static final int UPPERCASE = 0;
	/**
	 * Code for lower-case operation
	 */
	public static final int LOWERCASE = 1;
	/**
	 * Code for operation which inverts letters case.
	 */
	public static final int INVERT = 2;

	/**
	 * Model which stores multiple documents
	 */
	private MultipleDocumentModel model;
	/**
	 * Operation code.
	 */
	private int operation;

	/**
	 * Constructs instance of this operation.
	 * 
	 * @param model     Model which stores multiple documents
	 * @param operation Operation code
	 * @throws NullPointerException if given {@link SingleDocumentModel} is a
	 *                              {@code null} reference
	 */
	public CaseOperations(MultipleDocumentModel model, int operation) {
		Objects.requireNonNull(model);
		this.model = model;
		this.operation = operation;
	}

	@Override
	public void execute() {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		String selectedText = textArea.getSelectedText();

		switch (operation) {
		case UPPERCASE:
			textArea.replaceSelection(selectedText.toUpperCase());
			break;

		case LOWERCASE:
			textArea.replaceSelection(selectedText.toLowerCase());
			break;

		case INVERT:
			textArea.replaceSelection(invertCase(selectedText));
			break;
		}

	}

	/**
	 * Inverts case of each letter from a given String.
	 * 
	 * @param text Text to be inverted
	 * @return Inverted text
	 */
	public static String invertCase(String text) {
		char[] data = text.toCharArray();
		for (int i = 0; i < data.length; i++) {
			char c = data[i];
			if (Character.isLowerCase(c)) {
				data[i] = Character.toUpperCase(c);
			} else {
				data[i] = Character.toLowerCase(c);
			}
		}
		return new String(data);
	}

}
