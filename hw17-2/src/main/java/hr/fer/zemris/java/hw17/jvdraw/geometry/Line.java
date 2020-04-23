package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectVisitor;

/**
 * Models single line.
 * 
 * @see GeometricalObject
 * @author Robert Holovka
 * @version 1.0
 */
public class Line extends GeometricalObject {

	/**
	 * Line end point coordinates.
	 */
	private Point endPoint;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundColor Line color
	 * @param startPoint      Line start point coordinates
	 * @param endPoint        Line end point coordinates
	 */
	public Line(Color foregroundColor, Point startPoint, Point endPoint) {
		super(foregroundColor, startPoint);
		this.endPoint = endPoint;
	}

	/**
	 * @return line end point coordinates
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * Sets new line end point coordinates.
	 * 
	 * @param endPoint
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
		notifyListeners();
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Line (");
		sb.append(getStartPoint().x);
		sb.append(",");
		sb.append(getStartPoint().y);
		sb.append(")-(");
		sb.append(endPoint.x);
		sb.append(",");
		sb.append(endPoint.y + ")");

		return sb.toString();
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

}
