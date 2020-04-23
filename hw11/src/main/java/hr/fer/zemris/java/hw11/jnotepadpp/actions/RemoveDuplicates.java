package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Removes duplicates from a selected part of the text.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RemoveDuplicates implements IAction {

	/**
	 * Document model.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param model Model which stores multiple documents.
	 * @throws NullPointerException if document is a {@code null} reference
	 */
	public RemoveDuplicates(MultipleDocumentModel model) {
		Objects.requireNonNull(model);
		this.model = model;
	}

	@Override
	public void execute() {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		HashSet<String> duplicates = new HashSet<>();
		LinkedList<String> newText = new LinkedList<>();

		try {
			// Index starts at 0!
			int startLine = textArea.getLineOfOffset(textArea.getSelectionStart());
			int endLine = textArea.getLineOfOffset(textArea.getSelectionEnd());
			int lines = textArea.getLineCount();

			for (int line = 0; line < lines; line++) {
				int startOffset = textArea.getLineStartOffset(line);
				int length = textArea.getLineEndOffset(line) - startOffset;
				String text = textArea.getText(startOffset, length);
				// Selected area
				if (line >= startLine && line <= endLine) {
					if (!duplicates.contains(text.replaceAll("\n", ""))) {
						// For comparing last line with the others, EOL is ignored
						duplicates.add(text.replaceAll("\n", ""));
						newText.add(text);
					}
				} else { // Unselected area
					newText.add(text);
				}
			}

			StringBuilder sb = new StringBuilder();
			for (String s : newText) {
				sb.append(s);
			}
			textArea.setText(sb.toString());
		} catch (BadLocationException ignorable) {
		}
	}

}
