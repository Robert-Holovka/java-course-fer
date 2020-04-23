package hr.fer.zemris.java.models;

/**
 * Model that represent one Integer with its sine and cosine value.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Trigonometric {

	/**
	 * Base Integer number.
	 */
	private int x;
	/**
	 * Sine value of a {@link #x}.
	 */
	private double sin;
	/**
	 * Cosine value of a {@link #x}.
	 */
	private double cos;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param x   Base Integer number
	 * @param sin Sine value of a {@link #x}
	 * @param cos Cosine value of a {@link #x}
	 */
	public Trigonometric(int x, double sin, double cos) {
		this.x = x;
		this.sin = sin;
		this.cos = cos;
	}

	/**
	 * @return Base Integer number
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return Sine value of a {@link #x}
	 */
	public double getSin() {
		return sin;
	}

	/**
	 * @return Cosine value of a {@link #x}
	 */
	public double getCos() {
		return cos;
	}

}
