package hr.fer.zemris.java.gui.charts;

import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * Rectangle which represents area available for drawing.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DrawingArea {
	/**
	 * Available drawing area.
	 */
	private Rectangle drawingRectangle;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param drawingRectangle available drawing area
	 */
	public DrawingArea(Rectangle drawingRectangle) {
		super();
		this.drawingRectangle = drawingRectangle;
	}

	/**
	 * @return vailable drawing area
	 */
	public Rectangle getDrawingRectangle() {
		return drawingRectangle;
	}

	/**
	 * Translates given point from the frame coordinate system to the coordinate
	 * system of this area.
	 * 
	 * @param x Horizontal location on a frame.
	 * @param y Vertical location on a frame.
	 * @return Point in this drawing area
	 */
	public Point translate(int x, int y) {
		return new Point(drawingRectangle.x + x, drawingRectangle.y + y);
	}

	/**
	 * Translates value x into this coordinate system and returns it.
	 * 
	 * @param x
	 * @return
	 */
	public int translatedX(int x) {
		return drawingRectangle.x + x;
	}

	/**
	 * Translates value y into this coordinate system and returns it.
	 * 
	 * @param y
	 * @return
	 */
	public int translatedY(int y) {
		return drawingRectangle.y + y;
	}

	/**
	 * @return width of available drawing area
	 */
	public int getWidth() {
		return drawingRectangle.width;
	}

	/**
	 * @return height of available drawing area
	 */
	public int getHeight() {
		return drawingRectangle.height;
	}

	/**
	 * Shrinks this drawing area and returns new instance of it.
	 * 
	 * @param insets Margins, shrinking values
	 * @return
	 */
	public DrawingArea indent(Insets insets) {
		return new DrawingArea(
				new Rectangle(
						drawingRectangle.x + insets.left,
						drawingRectangle.y + insets.top,
						drawingRectangle.width - insets.left - insets.right,
						drawingRectangle.height - insets.top - insets.bottom));
	}

}