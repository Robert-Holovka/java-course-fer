package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;

/**
 * Handles file saving to a new location and necessary interaction with the
 * user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SaveFileAs implements IAction {

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
	public SaveFileAs(Container parent, DefaultMultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
	}

	@Override
	public void execute() {
		JFileChooser chooser = new JFileChooser();
		chooser.showSaveDialog(parent);
		// Operation has been canceled
		if (chooser.getSelectedFile() == null) {
			return;
		}

		Path path = Paths.get(chooser.getSelectedFile().toString());
		// Overwrite file?
		if (Files.exists(path)) {
			int res = JOptionPane.showConfirmDialog(parent,
					"File with a given name already exists, do you want to overwrite it?",
					"Name already exists.", JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		try {
			model.saveDocument(model.getCurrentDocument(), path);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(parent, e.getMessage(), "File already opened", JOptionPane.ERROR_MESSAGE);
		}
	}

}
