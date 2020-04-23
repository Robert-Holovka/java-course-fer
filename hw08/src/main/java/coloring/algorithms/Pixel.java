package coloring.algorithms;

import java.util.Objects;

/**
 * Represents single pixel from a picture.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Pixel {
	/**
	 * Coordinate on x-axis
	 */
	private int x;
	/**
	 * Coordinate on y-axis
	 */
	private int y;

	/**
	 * Constructs new instance of this class
	 * 
	 * @param x Coordinate on x-axis
	 * @param y Coordinate on y-axis
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns coordinate on x-axis
	 * 
	 * @return int
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns coordinate on y-axis
	 * 
	 * @return int
	 */
	public int getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

}
