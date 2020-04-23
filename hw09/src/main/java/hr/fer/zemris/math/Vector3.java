package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * This class can be used in two ways. It can represent a point in three
 * dimensional space. Also, it can represent an end-point of a vector whose
 * origin point is always at {@code (0, 0, 0)}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Vector3 {

	/**
	 * Position of a point on a horizontal axis.
	 */
	private double x;
	/**
	 * Position of a point on a vertical axis.
	 */
	private double y;
	/**
	 * Position of a point on a z axis.
	 */
	private double z;

	/**
	 * Creates a new instance of a point/vector with given position in the 3D
	 * coordinate system.
	 * 
	 * @param x Position of a point on a horizontal axis
	 * @param y Position of a point on a vertical axis
	 * @param z Position of a point on a z axis
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Calculates norm of this vector.
	 * 
	 * @return norm
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Returns new Vector3 after normalizing this one.
	 * 
	 * @return normalized
	 */
	public Vector3 normalized() {
		double norm = norm();
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Translates this point/vector by a given point/vector in positive direction.
	 * 
	 * @param other point/vector in the 3D coordinate system
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Translates this point/vector by a given point/vector in negative direction.
	 * 
	 * @param other point/vector in the 3D coordinate system
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Calculates scalar product of this vector and a given one.
	 * 
	 * @param p other vector
	 * @return scalar product
	 */
	public double dot(Vector3 other) {
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Calculates vector product of this vector and a given one.
	 * 
	 * @param p other vector
	 * @return scalar product
	 */
	public Vector3 cross(Vector3 other) {
		return new Vector3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x);
	}

	/**
	 * Scales this point/vector by a given value. In context of vectors, if given
	 * value is in range {@code <0.0, 1.0>} scaling is considered as shrinking, if
	 * value is greater than {@code 1} it is considered as stretching. If value is
	 * less than {@code 0} it rotates vector by 180 degrees and then performs
	 * shrinking or stretching. If given value is equal to {@code 1} vector/point
	 * remains same.
	 * 
	 * @param scale factor
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Returns angle between this Vector3 and a given one.
	 * 
	 * @param other Vector3
	 * @return angle
	 */
	public double cosAngle(Vector3 other) {
		return (dot(other)) / (this.norm() * other.norm());
	}

	/**
	 * Returns position of a point/vector on a horizontal axis.
	 * 
	 * @return Position of a point/vector on a horizontal axis
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns position of a point/vector on a vertical axis.
	 * 
	 * @return Position of a point/vector on a vertical axis
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns position of a point/vector on a z axis.
	 * 
	 * @return Position of a point/vector on a z axis
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns 3D coordinates of this Vector3 as an array.
	 * 
	 * @return coordinates
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.000000");
		DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
		dfs.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(dfs);

		return "(" + df.format(x) + ", " + df.format(y) + ", " + df.format(z) + ")";
	}
}
