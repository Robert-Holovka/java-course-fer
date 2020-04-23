package hr.fer.zemris.java.hw17.jvdraw.geometry.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Concrete {@link GeometricalObjectVisitor} implementation responsible for
 * calculating Bounding Box size of a visited. {@link GeometricalObject}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Bounding box start point value on x-axis.
	 */
	private int minX = Integer.MAX_VALUE;
	/**
	 * Bounding box start point value on y-axis.
	 */
	private int minY = Integer.MAX_VALUE;
	/**
	 * Bounding box end point value on x-axis.
	 */
	private int maxX = -1;
	/**
	 * Bounding box end point value on y-axis.
	 */
	private int maxY = -1;

	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		updateValues(start.x, start.y);
		Point end = line.getEndPoint();
		updateValues(end.x, end.y);
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getStartPoint();
		int radius = circle.getRadius();
		updateCircle(center, radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getStartPoint();
		int radius = filledCircle.getRadius();
		updateCircle(center, radius);
	}

	/**
	 * @return return bounding box size based on calculations performed on visited
	 *         {@link GeometricalObject} instances
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(minX, minY, (maxX - minX), (maxY - minY));
	}

	/**
	 * Calculates start and end points of a circle.
	 * 
	 * @param center Circle center coordinates
	 * @param radius Circle radius size
	 */
	private void updateCircle(Point center, int radius) {
		Point topLeftCorner = new Point((center.x - radius), (center.y - radius));
		updateValues(topLeftCorner.x, topLeftCorner.y);
		Point bottomRightCorner = new Point((center.x + radius), (center.y + radius));
		updateValues(bottomRightCorner.x, bottomRightCorner.y);
	}

	/**
	 * Updates fields defined in this class.
	 * 
	 * @param x Coordinate on x-axis
	 * @param y Coordinate on y-axis
	 */
	private void updateValues(int x, int y) {
		minX = (x < minX) ? x : minX;
		maxX = (x > maxX) ? x : maxX;
		minY = (y < minY) ? y : minY;
		maxY = (y > maxY) ? y : maxY;
	}

}
