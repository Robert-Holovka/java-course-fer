package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectVisitor;

/**
 * Models circle with non-empty background.
 * 
 * @see AbstractCircle
 * @see GeometricalObject
 * @author Robert Holovka
 * @version 1.0
 */
public class FilledCircle extends AbstractCircle {

	/**
	 * Circle background color.
	 */
	private Color backgroundColor;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundColor Circle outer line color
	 * @param backgroundColor Circle background color
	 * @param startPoint      Circle center
	 * @param radius          Circle radius size
	 */
	public FilledCircle(Color foregroundColor, Color backgroundColor, Point startPoint, int radius) {
		super(foregroundColor, startPoint, radius);
		this.backgroundColor = backgroundColor;
	}

	/**
	 * @return circle background color
	 */
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Sets new circle background color.
	 * 
	 * @param backgroundColor
	 */
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
		notifyListeners();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		Color bgColor = getBackgroundColor();

		sb.append("Filled circle (");
		sb.append(getStartPoint().x);
		sb.append(",");
		sb.append(getStartPoint().y);
		sb.append("), ");
		sb.append(getRadius());
		sb.append(", ");
		sb.append(String.format("#%02x%02x%02x", bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue()));

		return sb.toString();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

}
