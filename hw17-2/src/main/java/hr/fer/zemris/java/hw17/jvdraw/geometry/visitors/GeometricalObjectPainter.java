package hr.fer.zemris.java.hw17.jvdraw.geometry.visitors;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Concrete {@link GeometricalObjectVisitor} implementation responsible for
 * rendering visited {@link GeometricalObject} on a provided {@link Graphics2D}
 * object.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Object to be drawn on.
	 */
	private Graphics2D g2d;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param g2d Object for drawing
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}

	@Override
	public void visit(Line line) {
		g2d.setColor(line.getForegroundColor());
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void visit(Circle circle) {
		drawCircle(circle.getForegroundColor(), circle.getStartPoint(), (int) circle.getRadius(), false, 1);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point startPoint = filledCircle.getStartPoint();
		int radius = (int) filledCircle.getRadius();
		drawCircle(filledCircle.getBackgroundColor(), startPoint, radius, true, 1);
		drawCircle(filledCircle.getForegroundColor(), startPoint, radius, false, 1);
	}

	/**
	 * Draws basic circle.
	 * 
	 * @param color  Circle color
	 * @param start  Circle center
	 * @param radius Circle radius size
	 * @param fill   Flag which tells whether circle outer line will be rendered or
	 *               its background
	 * @param stroke Size of circle outer line
	 */
	private void drawCircle(Color color, Point start, int radius, boolean fill, int stroke) {
		g2d.setColor(color);

		int x = (int) (start.x - radius);
		int y = (int) (start.y - radius);
		int w = (int) (2 * radius);
		int h = w;

		if (fill) {
			g2d.fillOval(x, y, w, h);
		} else {
			Stroke savedStroke = g2d.getStroke();
			g2d.setStroke(new BasicStroke(stroke));
			g2d.drawOval(x, y, w, h);
			g2d.setStroke(savedStroke);
		}
	}
}
