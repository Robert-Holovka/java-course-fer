package hr.fer.zemris.java.hw17.jvdraw.geometry.visitors;

import java.awt.Color;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Concrete {@link GeometricalObjectVisitor} implementation responsible for
 * generating textual JVD representation of {@link GeometricalObject}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GeometricalObjectDocumentFormat implements GeometricalObjectVisitor {

	/**
	 * Object responsible for building String instances.
	 */
	private StringBuilder sb;

	/**
	 * Constructs new instance of this class.
	 */
	public GeometricalObjectDocumentFormat() {
		sb = new StringBuilder();
	}

	@Override
	public void visit(Line line) {
		sb.append("LINE ");
		sb.append(line.getStartPoint().x + " " + line.getStartPoint().y + " ");
		sb.append(line.getEndPoint().x + " " + line.getEndPoint().y + " ");
		Color color = line.getForegroundColor();
		sb.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
		sb.append("\n");
	}

	@Override
	public void visit(Circle circle) {
		sb.append("CIRCLE ");
		sb.append(circle.getStartPoint().x + " " + circle.getStartPoint().y + " ");
		sb.append(circle.getRadius() + " ");
		Color color = circle.getForegroundColor();
		sb.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
		sb.append("\n");
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		sb.append("FCIRCLE ");
		sb.append(filledCircle.getStartPoint().x + " " + filledCircle.getStartPoint().y + " ");
		sb.append(filledCircle.getRadius() + " ");
		Color color = filledCircle.getForegroundColor();
		sb.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue() + " ");
		color = filledCircle.getBackgroundColor();
		sb.append(color.getRed() + " " + color.getGreen() + " " + color.getBlue());
		sb.append("\n");
	}

	/**
	 * @return JVD text representation of all visited objects
	 */
	public String getTextRepresentation() {
		return sb.toString();
	}
}
