package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Container;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectDocumentFormat;

/**
 * Handles file saving and necessary interaction with the user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SaveFileAction extends AbstractAction {

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent        Root container(frame)
	 * @param drawingModel  Storage for geometrical elements
	 * @param documentModel Currently opened file
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public SaveFileAction(Container parent, DrawingModel drawingModel, DocumentModel documentModel) {
		super(parent, drawingModel, documentModel);
	}

	@Override
	public void execute() {
		if (!drawingModel.isModified()) {
			JOptionPane.showMessageDialog(parent, "There is no new changes.", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path openedFile = documentModel.getCurrentFile();
		// Delegate to save as action, save to a new location
		if (openedFile == null) {
			SaveFileAsAction sfa = new SaveFileAsAction(parent, drawingModel, documentModel);
			sfa.execute();
			return;
		}

		// Save to existing location
		try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(openedFile))) {
			GeometricalObjectDocumentFormat godf = new GeometricalObjectDocumentFormat();
			for (var object : drawingModel) {
				object.accept(godf);
			}

			String text = godf.getTextRepresentation();
			os.write(text.getBytes());

			drawingModel.clearModifiedFlag();
			JOptionPane.showMessageDialog(parent, "File saved.", "Info", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Something went wrong.", "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

}
