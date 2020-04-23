package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Editor for {@link FilledCircle} objects.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class FilledCircleEditor extends CircleEditor {
	/**
	 * Input field for red component of Circle color.
	 */
	private JTextField red;
	/**
	 * Input field for green component of Circle color.
	 */
	private JTextField green;
	/**
	 * Input field for blue component of Circle color.
	 */
	private JTextField blue;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param geometricalObject {@link FilledCircle} to be edited
	 */
	public FilledCircleEditor(GeometricalObject geometricalObject) {
		super(geometricalObject);
	}

	@Override
	protected void initGUI() {
		super.initGUI();
		setTitle("Edit filled circle:");

		FilledCircle initialObject = (FilledCircle) geometricalObject;

		// Color
		JLabel color = new JLabel("Background color");
		JLabel redLabel = new JLabel("r:");
		JLabel greenLabel = new JLabel("g:");
		JLabel blueLabel = new JLabel("b:");

		red = new JTextField(String.valueOf(initialObject.getBackgroundColor().getRed()));
		green = new JTextField(String.valueOf(initialObject.getBackgroundColor().getGreen()));
		blue = new JTextField(String.valueOf(initialObject.getBackgroundColor().getBlue()));

		addInputForm(color, redLabel, red, greenLabel, green, blueLabel, blue);
	}

	@Override
	public void checkEditing() {
		super.checkEditing();
		// Validate color
		if (!isValidColor(red, green, blue)) {
			throw new IllegalArgumentException("Color components must be positive integers in range [0, 255].");
		}

	}

	@Override
	public void acceptEditing() {
		super.acceptEditing();

		FilledCircle initialObject = (FilledCircle) geometricalObject;
		Color color = new Color(Integer.parseInt(red.getText()),
				Integer.parseInt(green.getText()),
				Integer.parseInt(blue.getText()));

		initialObject.setBackgroundColor(color);
	}

}
