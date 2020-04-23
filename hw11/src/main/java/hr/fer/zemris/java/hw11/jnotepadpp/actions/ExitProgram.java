package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.util.Objects;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;

/**
 * Action responsible for terminating the program.
 *
 * @author Robert Holovka
 * @version 1.0
 */
public class ExitProgram implements IAction {

	/**
	 * Root container(frame).
	 */
	private Container parent;
	/**
	 * Model which stores multiple documents.
	 */
	private DefaultMultipleDocumentModel model;

	/**
	 * Flag which tells should program be closed.
	 */
	private boolean shouldClose;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent Root container(frame)
	 * @param model  Model which stores multiple documents
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public ExitProgram(Container parent, DefaultMultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
	}

	@Override
	public void execute() {
		shouldClose = false;
		int docNum = model.getNumberOfDocuments();
		for (int i = 0; i < docNum; i++) {
			CloseFile closeFileOperation = new CloseFile(parent, model);
			closeFileOperation.execute();
			if (closeFileOperation.isOperationCanceled()) {
				return;
			}
		}
		shouldClose = true;
	}

	/**
	 * @return Flag which tells should program be closed.
	 */
	public boolean shouldClose() {
		return shouldClose;
	}
}
