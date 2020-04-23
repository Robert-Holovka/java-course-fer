package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class can be used in two ways. It can represent a point in two
 * dimensional space. Also, it can represent an end-point of a vector whose
 * origin point is always at {@code (0, 0)}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Vector2D {

	/**
	 * Maximum allowed difference between two real numbers. If difference is less
	 * than specified, two real numbers are considered equal.
	 */
	private static final double MAX_DIFFERENCE = 0.00001;

	/**
	 * Position of a point on a horizontal axis.
	 */
	private double x;
	/**
	 * Position of a point on a vertical axis.
	 */
	private double y;

	/**
	 * Creates new instance of a point/vector with given position in the Cartesian
	 * 2D coordinate system.
	 * 
	 * @param x Position of a point on a horizontal axis
	 * @param y Position of a point on a vertical axis
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns position of a point on a horizontal axis.
	 * 
	 * @return Position of a point on a horizontal axis
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns position of a point on a vertical axis.
	 * 
	 * @return Position of a point on a vertical axis
	 */
	public double getY() {
		return y;
	}

	/**
	 * Translates this point/vector by a given point/vector.
	 * 
	 * @param offset point/vector in the Cartesian 2D coordinate system
	 * @throws {@link NullPointerException} if given point/vector is equal to a
	 *         {@code null} reference
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		x += offset.getX();
		y += offset.getY();
	}

	/**
	 * Returns new instance of this class after performing translation on this
	 * point/vector by a given offset. Original point/vector remains same.
	 * 
	 * @param offset point/vector in the Cartesian 2D coordinate system.
	 * @throws {@link NullPointerException} if given point/vector is equal to a
	 *         {@code null} reference
	 * @return new instance of this class after translation
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset);
		var vector = new Vector2D(x, y);
		vector.translate(offset);
		return vector;
	}

	/**
	 * Rotates this point/vector by a given angle.
	 * 
	 * @param angle in radians
	 */
	public void rotate(double angle) {
		x = Math.cos(angle) * x - Math.sin(angle) * y;
		y = Math.sin(angle) * x + Math.cos(angle) * y;
	}

	/**
	 * Returns new instance of this class after performing rotation on this
	 * point/vector by a given angle. Original point/vector remains same.
	 * 
	 * @param angle in radians
	 * @return new instance of this class after rotation
	 */
	public Vector2D rotated(double angle) {
		var vector = new Vector2D(x, y);
		vector.rotate(angle);
		return vector;
	}

	/**
	 * Scales this point/vector by a given value. In context of vectors, if given
	 * value is in range {@code <0.0, 1.0>} scaling is considered as shrinking, if
	 * value is greater than {@code 1} it is considered as stretching. If value is
	 * less than {@code 0} it rotates vector by 180 degrees and then performs
	 * shrinking or stretching. If given value is equal to {@code 1} vector/point
	 * remains same.
	 * 
	 * @param scale
	 */
	public void scale(double scaler) {
		x *= scaler;
		y *= scaler;
	}

	/**
	 * Returns new instance of this class after performing scaling on this
	 * point/vector by a given value. In context of vectors, if given value is in
	 * range {@code <0.0, 1.0>} scaling is considered as shrinking, if value is
	 * greater than {@code 1} it is considered as stretching. If value is less than
	 * {@code 0} it rotates vector by 180 degrees and then performs shrinking or
	 * stretching. If given value is equal to {@code 1} vector/point remains same.
	 * 
	 * @param scale
	 * @return new instance of this class
	 */
	public Vector2D scaled(double scaler) {
		var vector = new Vector2D(x, y);
		vector.scale(scaler);
		return vector;
	}

	/**
	 * Returns new instance of this class which is equal to this instance.
	 * 
	 * @return new instance of this class
	 */
	public Vector2D copy() {
		return new Vector2D(x, y);
	}

	/**
	 * Checks whether two point/vectors are equal. They are considered equal if
	 * maximum difference between their positions on axis is not greater than the
	 * value specified in {@link #MAX_DIFFERENCE}.
	 * 
	 * @param obj Object to be compared with an instance of this class
	 * @return True if vector/points are equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;
		if (!(obj instanceof Vector2D))
			return false;

		Vector2D other = (Vector2D) obj;
		return (Math.abs((this.x - other.x)) < MAX_DIFFERENCE) && (Math.abs(this.y - other.y) < MAX_DIFFERENCE);
	}

}
