package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.util.Objects;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;

/**
 * Handles file saving and necessary interaction with the user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SaveFile implements IAction {

	/**
	 * Root container.
	 */
	private Container parent;
	/**
	 * Model which stores multiple documents.
	 */
	private DefaultMultipleDocumentModel model;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param parent Root container
	 * @param model  Model which stores multiple documents
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public SaveFile(Container parent, DefaultMultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
	}

	@Override
	public void execute() {
		SingleDocumentModel document = model.getCurrentDocument();
		// Save to existing location
		if (document.getFilePath() != null) {
			model.saveDocument(document, document.getFilePath());
			return;
		}

		// Save to new location
		SaveFileAs sfa = new SaveFileAs(parent, model);
		sfa.execute();
	}

}
