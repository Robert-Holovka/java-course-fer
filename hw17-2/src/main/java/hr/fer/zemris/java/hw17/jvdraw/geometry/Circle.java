package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectVisitor;

/**
 * Models circle with empty background.
 * 
 * @see AbstractCircle
 * @see GeometricalObject
 * @author Robert Holovka
 * @version 1.0
 */
public class Circle extends AbstractCircle {

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundColor Circle outer line color
	 * @param startPoint      Circle center
	 * @param radius          circle radius size
	 */
	public Circle(Color foregroundColor, Point startPoint, int radius) {
		super(foregroundColor, startPoint, radius);
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Circle (");
		sb.append(getStartPoint().x);
		sb.append(",");
		sb.append(getStartPoint().y);
		sb.append("), ");
		sb.append(getRadius());

		return sb.toString();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}
}
