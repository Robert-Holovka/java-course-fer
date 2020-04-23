package hr.fer.zemris.java.hw17.jvdraw.utils;

import java.awt.Point;

/**
 * Some tools useful for working with geometry shapes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GeometryUtils {

	/**
	 * Maximum value for a component from the Color.
	 */
	private static final int MAX_RGB_VALUE = 255;

	/**
	 * Calculates distances between two {@link Point} instances.
	 * 
	 * @param p1 First point
	 * @param p2 Second point
	 * @return distance
	 */
	public static double distance(Point p1, Point p2) {
		double a = Math.pow((p1.x - p2.x), 2);
		double b = Math.pow((p1.y - p2.y), 2);
		return Math.sqrt(a + b);
	}

	/**
	 * Checks whether given coordinate is valid.
	 * 
	 * @param x Value on x-axis
	 * @param y Value on y-axis
	 * @return True if given coordinates are valid, false otherwise
	 */
	public static boolean isValidCoordinate(String x, String y) {
		return CommonUtils.isPositiveInteger(x) && CommonUtils.isPositiveInteger(y);
	}

	/**
	 * Checks whether given String representation of a RGB Color component is valid.
	 * 
	 * @param s RGB component
	 * @return True if it is valid, false otherwise
	 */
	public static boolean isValidColorComponent(String s) {
		if (!CommonUtils.isPositiveInteger(s)) {
			return false;
		}
		int component = Integer.parseInt(s);
		return component <= MAX_RGB_VALUE;
	}
}
