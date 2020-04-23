package hr.fer.zemris.java.gui.charts;

/**
 * X, Y value pair.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class XYValue {
	/**
	 * X value.
	 */
	private int x;
	/**
	 * Y value.
	 */
	private int y;

	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return y
	 */
	public int getY() {
		return y;
	}

}
