package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.AbstractCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Editor for {@link Circle} objects.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CircleEditor extends GeometricalObjectEditor {
	/**
	 * Input field for center point value on x-axis.
	 */
	private JTextField startX;
	/**
	 * Input field for center point value on y-axis.
	 */
	private JTextField startY;
	/**
	 * Input field for circle radius value.
	 */
	private JTextField radius;
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
	 * @param geometricalObject Circle to be edited
	 */
	public CircleEditor(GeometricalObject geometricalObject) {
		super(geometricalObject);
		setTitle("Edit circle:");
		initGUI();
	}

	/**
	 * Initializes graphical user interface of a form for editing provided Circle.
	 */
	protected void initGUI() {
		AbstractCircle initialObject = (AbstractCircle) geometricalObject;
		// Center
		JLabel start = new JLabel("Center");
		JLabel labelX = new JLabel("X:");
		JLabel labelY = new JLabel("Y:");

		startX = new JTextField(String.valueOf(initialObject.getStartPoint().x));
		startY = new JTextField(String.valueOf(initialObject.getStartPoint().y));
		addInputForm(start, labelX, startX, labelY, startY);

		// Radius
		JLabel radiusLabel = new JLabel("Radius:");
		radius = new JTextField(String.valueOf((int) initialObject.getRadius()));
		addInputForm(radiusLabel, radius);

		// Color
		JLabel color = new JLabel("Circle color");
		JLabel redLabel = new JLabel("r:");
		JLabel greenLabel = new JLabel("g:");
		JLabel blueLabel = new JLabel("b:");

		red = new JTextField(String.valueOf(initialObject.getForegroundColor().getRed()));
		green = new JTextField(String.valueOf(initialObject.getForegroundColor().getGreen()));
		blue = new JTextField(String.valueOf(initialObject.getForegroundColor().getBlue()));

		addInputForm(color, redLabel, red, greenLabel, green, blueLabel, blue);
	}

	@Override
	public void checkEditing() {
		// Validate start point
		if (!isValidCoordinate(startX, startY)) {
			throw new IllegalArgumentException("Start coordinate must contain positive integer numbers only.");
		}

		// Validate color
		if (!isValidColor(red, green, blue)) {
			throw new IllegalArgumentException("Color components must be positive integers in range [0, 255].");
		}
	}

	@Override
	public void acceptEditing() {
		AbstractCircle initialObject = (AbstractCircle) geometricalObject;
		Point start = new Point(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
		int radiusValue = Integer.parseInt(radius.getText());
		Color color = new Color(Integer.parseInt(red.getText()),
				Integer.parseInt(green.getText()),
				Integer.parseInt(blue.getText()));

		// TODO: avoid triggering notify method 3 times
		initialObject.setStartPoint(start);
		initialObject.setRadius(radiusValue);
		initialObject.setForegroundColor(color);
	}

}
