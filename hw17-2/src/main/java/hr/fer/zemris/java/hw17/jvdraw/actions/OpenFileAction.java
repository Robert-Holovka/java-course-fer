package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.factories.GeometricalObjectFactory;

/**
 * Handles file opening and necessary interaction with the user. Files must have
 * extension ".jvd".
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class OpenFileAction extends AbstractAction {

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent        Root container(frame)
	 * @param drawingModel  Storage for geometrical elements
	 * @param documentModel Currently opened file
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public OpenFileAction(Container parent, DrawingModel drawingModel, DocumentModel documentModel) {
		super(parent, drawingModel, documentModel);
	}

	@Override
	public void execute() {
		// Ask to save work if drawing model is modified
		if (drawingModel.isModified()) {
			int res = JOptionPane.showConfirmDialog(parent,
					"There is some unsaved work. Do you want save it?",
					"Unsaved work!", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			// Delegate to save action
			if (res == JOptionPane.YES_OPTION) {
				SaveFileAction sfa = new SaveFileAction(parent, drawingModel, documentModel);
				sfa.execute();
			}
		}

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

		if (!path.toString().endsWith(".jvd")) {
			JOptionPane.showMessageDialog(parent, "File must be .jvd file.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			// Delete previously rendered picture
			drawingModel.clear();

			// Load new picture
			List<String> lines = Files.readAllLines(path);
			for (String line : lines) {
				if (line.trim().isBlank()) {
					continue;
				}
				GeometricalObject go = GeometricalObjectFactory.createGeometricalObject(line);
				drawingModel.add(go);
			}

			// Opened document is not a modified one
			drawingModel.clearModifiedFlag();
		} catch (IOException | IllegalArgumentException e) {
			JOptionPane.showMessageDialog(parent, "File does not have a valid syntax for .jvd.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Update document model
		documentModel.setCurrentFile(path);
	}

}
