package hr.fer.zemris.java.hw17.jvdraw.geometry.visitors;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Methods for manipulating with visited objects.
 * 
 * @author Robert Holovka
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Action when {@link Line} is visited.
	 * 
	 * @param line
	 */
	abstract void visit(Line line);

	/**
	 * Action when {@link Circle} is visited.
	 * 
	 * @param circle
	 */
	abstract void visit(Circle circle);

	/**
	 * Action when {@link FilledCircle} is visited.
	 * 
	 * @param filledCircle
	 */
	abstract void visit(FilledCircle filledCircle);
}