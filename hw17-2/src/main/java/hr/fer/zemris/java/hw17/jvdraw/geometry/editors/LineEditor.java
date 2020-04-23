package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Editor for {@link Line} objects.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class LineEditor extends GeometricalObjectEditor {
	/**
	 * Input field for start point value on x-axis.
	 */
	private JTextField startX;
	/**
	 * Input field for start point value on y-axis.
	 */
	private JTextField startY;
	/**
	 * Input field for end point value on x-axis.
	 */
	private JTextField endX;
	/**
	 * Input field for start point value on y-axis.
	 */
	private JTextField endY;
	/**
	 * Input field for red component of Line color.
	 */
	private JTextField red;
	/**
	 * Input field for green component of Line color.
	 */
	private JTextField green;
	/**
	 * Input field for blue component of Line color.
	 */
	private JTextField blue;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param geometricalObject Line to be edited
	 */
	public LineEditor(Line geometricalObject) {
		super(geometricalObject);
		setTitle("Edit line:");
		initGUI();
	}

	/**
	 * Initializes graphical user interface of a form for editing provided Line.
	 */
	private void initGUI() {
		Line initialObject = (Line) geometricalObject;
		// Start point
		JLabel start = new JLabel("Start point");
		JLabel labelX = new JLabel("X:");
		JLabel labelY = new JLabel("Y:");

		startX = new JTextField(String.valueOf(initialObject.getStartPoint().x));
		startY = new JTextField(String.valueOf(initialObject.getStartPoint().y));
		addInputForm(start, labelX, startX, labelY, startY);

		// End point
		JLabel end = new JLabel("End point");
		labelX = new JLabel("X:");
		labelY = new JLabel("Y:");

		endX = new JTextField(String.valueOf(initialObject.getEndPoint().x));
		endY = new JTextField(String.valueOf(initialObject.getEndPoint().y));
		addInputForm(end, labelX, endX, labelY, endY);

		// Color
		JLabel color = new JLabel("Color");
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
		// Validate end point
		if (!isValidCoordinate(endX, endY)) {
			throw new IllegalArgumentException("Start coordinate must contain positive integer numbers only.");
		}

		// Validate color
		if (!isValidColor(red, green, blue)) {
			throw new IllegalArgumentException("Color components must be positive integers in range [0, 255].");
		}
	}

	@Override
	public void acceptEditing() {
		Line initialObject = (Line) geometricalObject;
		Point start = new Point(Integer.parseInt(startX.getText()), Integer.parseInt(startY.getText()));
		Point end = new Point(Integer.parseInt(endX.getText()), Integer.parseInt(endY.getText()));
		Color color = new Color(Integer.parseInt(red.getText()),
				Integer.parseInt(green.getText()),
				Integer.parseInt(blue.getText()));

		initialObject.setStartPoint(start);
		initialObject.setEndPoint(end);
		initialObject.setForegroundColor(color);
	}

}
