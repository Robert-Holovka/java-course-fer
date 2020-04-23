package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectPainter;

/**
 * Action responsible for rendering the picture which represents current state
 * of the {@link #drawingModel}. Allowed formats are: .png, .gif, .jpg.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ExportAction implements IAction {

	/**
	 * Root container(frame).
	 */
	private Container parent;
	/**
	 * Storage for geometrical elements.
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent       Root container(frame)
	 * @param drawingModel Storage for geometrical elements
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public ExportAction(Container parent, DrawingModel drawingModel) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(drawingModel);
		this.parent = parent;
		this.drawingModel = drawingModel;
	}

	@Override
	public void execute() {
		// If drawing model is empty
		if (drawingModel.getSize() == 0) {
			JOptionPane.showMessageDialog(parent, "There is nothing to render!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Initialize and show save dialog
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "PNG, GIF, JPG";
			}

			@Override
			public boolean accept(File f) {
				if (f.isDirectory()) {
					return true;
				}
				String name = f.getName();
				return name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".png");
			}
		});
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.showSaveDialog(parent);

		// Operation has been canceled
		if (chooser.getSelectedFile() == null) {
			return;
		}

		Path selectedPath = Paths.get(chooser.getSelectedFile().toString());

		// Just in case, should not happen
		if (Files.isDirectory(selectedPath)) {
			JOptionPane.showMessageDialog(parent, "Selected document is not a file.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!selectedPath.toString().endsWith(".png") && !selectedPath.toString().endsWith(".gif") &&
				!selectedPath.toString().endsWith(".jpg")) {
			JOptionPane.showMessageDialog(parent, "File extension must be .gif, .jpg, or .png!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Overwrite file?
		if (Files.exists(selectedPath)) {
			int res = JOptionPane.showConfirmDialog(parent,
					"File with a given name already exists, do you want to overwrite it?",
					"Name already exists.", JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}

		String extension = selectedPath.toString().split("\\.")[1];
		export(extension, selectedPath);
	}

	/**
	 * Performs image rendering based on {@link #drawingModel} state and exports
	 * that image.
	 * 
	 * @param extension    Image extension
	 * @param selectedPath Image path
	 */
	private void export(String extension, Path selectedPath) {
		// Export operation
		// Calculate Bounding Box
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (var object : drawingModel) {
			object.accept(bbcalc);
		}
		Rectangle box = bbcalc.getBoundingBox();

		// Create Image Buffer
		BufferedImage image = new BufferedImage(
				box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = image.createGraphics();

		// Initialize drawing box
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, box.width, box.height);
		g.translate(-box.x, -box.y);

		// Paint image
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
		for (var object : drawingModel) {
			object.accept(painter);
		}
		g.dispose();

		File file = new File(selectedPath.toString());
		try {
			ImageIO.write(image, extension, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Something went wrong.", "Error.", JOptionPane.ERROR_MESSAGE);
			return;
		}
		// Done!
		JOptionPane.showMessageDialog(parent, String.format("Image has been exported to: %s", selectedPath.toString()));
	}

}
