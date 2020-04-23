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
 * Handles file opening and necessary interaction with the user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class OpenFile implements IAction {

	/**
	 * Root container.
	 */
	private Container parent;
	/**
	 * Model which stores multiple documents.
	 */
	private DefaultMultipleDocumentModel model;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent Root container
	 * @param model  Model which stoers multiple documents
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public OpenFile(Container parent, DefaultMultipleDocumentModel model) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(model);
		this.parent = parent;
		this.model = model;
	}

	@Override
	public void execute() {
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(parent);
		// Operation has been canceled
		if (chooser.getSelectedFile() == null) {
			return;
		}
		Path path = Paths.get(chooser.getSelectedFile().toString());
		// Just in case, should not happen
		if (Files.isDirectory(path)) {
			JOptionPane.showMessageDialog(parent, "Selected document is not a file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!Files.isReadable(path)) {
			JOptionPane.showMessageDialog(parent, "Selected file is not readable.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.loadDocument(path);
	}

}
