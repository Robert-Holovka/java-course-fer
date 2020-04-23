package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.MultipleDocumentModel;

/**
 * Sorts selected part of the text in a specified order.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Sort implements IAction {

	/**
	 * Code for ascending order.
	 */
	public static final int ASCENDING = 0;
	/**
	 * Code for descending order.
	 */
	public static final int DESCENDING = 1;

	/**
	 * Model which stores multiple documents.
	 */
	private MultipleDocumentModel model;
	/**
	 * Language code.
	 */
	private String language;
	/**
	 * Sort order.
	 */
	private int order;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param model Model which stores multiple documents.
	 * @param language Language code
	 * @param order    Sort order
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public Sort(MultipleDocumentModel model, String language, int order) {
		Objects.requireNonNull(model);
		Objects.requireNonNull(language);
		Objects.requireNonNull(order);
		this.model = model;
		this.language = language;
		this.order = order;
	}

	@Override
	public void execute() {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		Locale locale = new Locale(language);
		Collator collator = Collator.getInstance(locale);
		LinkedList<String> selectedLines = new LinkedList<>();
		LinkedList<String> unselectedLines = new LinkedList<>();

		try {
			// Index starts at 0!
			int startLine = textArea.getLineOfOffset(textArea.getSelectionStart());
			int endLine = textArea.getLineOfOffset(textArea.getSelectionEnd());
			int lines = textArea.getLineCount();

			// Isolate selected text
			for (int line = 0; line < lines; line++) {
				int startOffset = textArea.getLineStartOffset(line);
				int length = textArea.getLineEndOffset(line) - startOffset;
				String text = textArea.getText(startOffset, length);

				if (line >= startLine && line <= endLine) { // selected part
					selectedLines.add(text);
				} else { // unselected part
					unselectedLines.add(text);
				}
			}

			// Sort selected text
			switch (order) {
			case DESCENDING:
				selectedLines.sort((s1, s2) -> collator.compare(s2, s1));
				break;
			default:
				selectedLines.sort((s1, s2) -> collator.compare(s1, s2));
			}

			// Glue unselected text with sorted selected text
			StringBuilder sb = new StringBuilder();
			int i = 0, j = 0;

			for (int line = 0; line < lines; line++) {
				String text = (line >= startLine && line <= endLine) ? selectedLines.get(i++)
						: unselectedLines.get(j++);

				if ((i + j) < lines) {
					text = text.endsWith("\n") ? text : text + "\n";
				} else {
					text = text.replaceAll("\n", "");
				}
				sb.append(text);
			}

			textArea.setText(sb.toString());
		} catch (BadLocationException ignorable) {
		}
	}

}
