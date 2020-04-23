package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectDocumentFormat;

/**
 * Handles file saving to a new location and necessary interaction with the
 * user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SaveFileAsAction extends AbstractAction {

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent        Root container(frame)
	 * @param drawingModel  Storage for geometrical elements
	 * @param documentModel Currently opened file
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public SaveFileAsAction(Container parent, DrawingModel drawingModel, DocumentModel documentModel) {
		super(parent, drawingModel, documentModel);
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

		// Invalid path extension
		if (!path.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(parent,
					"Wrong file extension, must be .jvd!",
					"Wrong file extension.", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Path leads to the currently opened file?
		if (path.equals(documentModel.getCurrentFile())) {
			JOptionPane.showMessageDialog(parent,
					"Can not perform 'Save As' on a currenlty opened file location.",
					"Invalid file path.", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Overwrite file?
		if (Files.exists(path)) {
			int res = JOptionPane.showConfirmDialog(parent,
					"File with a given name already exists, do you want to overwrite it?",
					"Name already exists.", JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(path))) {
			GeometricalObjectDocumentFormat godf = new GeometricalObjectDocumentFormat();
			for (var object : drawingModel) {
				object.accept(godf);
			}

			String text = godf.getTextRepresentation();
			os.write(text.getBytes());

			// Update models
			drawingModel.clearModifiedFlag();
			documentModel.setCurrentFile(path);
			JOptionPane.showMessageDialog(parent, String.format("File saved to: %s", path.toString()), "Info",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
