package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.util.Objects;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Calculates file statistics: number of characters, number of non-blank
 * characters and number of lines.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Statistics implements IAction {

	/**
	 * Root container.
	 */
	private Container parent;
	/**
	 * Model which stores multiple documents.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent   Root container.
	 * @param model Model which stores multiple documents
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public Statistics(Container parent, MultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
	}

	@Override
	public void execute() {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		int lines = textArea.getLineCount();

		char[] data = textArea.getText().toCharArray();
		int numOfChars = data.length;

		int nonBlankChars = 0;
		for (char c : data) {
			if (!Character.isWhitespace(c)) {
				nonBlankChars++;
			}
		}

		String info = String.format("Your document has %d characters, %d non-blank characters and %d lines.",
				numOfChars, nonBlankChars, lines);
		JOptionPane.showMessageDialog(parent, info, "Statistics", JOptionPane.INFORMATION_MESSAGE);
	}

}
