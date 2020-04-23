package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.util.Objects;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Action responsible for closing file and handling necessary interaction with
 * the user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CloseFile implements IAction {

	/**
	 * Document root container.
	 */
	private Container parent;
	/**
	 * Model which stores documents.
	 */
	private DefaultMultipleDocumentModel model;

	/**
	 * Flag which specifies operation cancellation.
	 */
	private boolean operationCanceled;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent Document root container
	 * @param model  Model which stores documents
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public CloseFile(Container parent, DefaultMultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
		this.operationCanceled = false;
	}

	@Override
	public void execute() {
		operationCanceled = false;
		SingleDocumentModel document = model.getCurrentDocument();

		if (document.isModified()) {
			int result = JOptionPane.showConfirmDialog(parent, "Do you want to save file before closing?",
					"Closing unsaved file...",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

			if (result == JOptionPane.CANCEL_OPTION) {
				operationCanceled = true;
				return;
			}

			if (result == JOptionPane.YES_OPTION) {
				new SaveFile(parent, model).execute();
			}
		}

		model.closeDocument(document);
	}

	/**
	 * @return Returns information about whether this closing operation has been
	 *         prosecuted
	 */
	public boolean isOperationCanceled() {
		return operationCanceled;
	}

}
