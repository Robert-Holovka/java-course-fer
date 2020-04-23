package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;

/**
 * Contains all fields and methods which every circle like geometry shape must
 * have.
 * 
 * @see GeometricalObject
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class AbstractCircle extends GeometricalObject {

	/**
	 * Circle radius size.
	 */
	private int radius;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundColor Circle outer line color
	 * @param startPoint      Circle center
	 * @param radius          Circle radius size
	 */
	public AbstractCircle(Color foregroundColor, Point startPoint, int radius) {
		super(foregroundColor, startPoint);
		this.radius = radius;
	}

	/**
	 * @return radius size
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Sets new radius size.
	 * 
	 * @param radius
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyListeners();
	}

}
