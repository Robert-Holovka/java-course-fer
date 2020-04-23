package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents draw command responsible for rendering lines in a given Painter.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DrawCommand implements Command {

	/**
	 * Scaler of a line length.
	 */
	private double step;
	/**
	 * Default line width.
	 */
	private static float DEFUALT_LINE_WIDTH = 1.0f;

	/**
	 * Constructs new instance of this command defined by a given step.
	 * 
	 * @param step Scaler of a line length.
	 */
	public DrawCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D tempPosition = currentState.getPosition();

		double x0 = tempPosition.getX();
		double y0 = tempPosition.getY();

		double lineLength = currentState.getMovementLength() * step;
		Vector2D newPosition = tempPosition.copy();

		Vector2D scaledDirection = currentState.getDirection().scaled(lineLength);
		newPosition.translate(scaledDirection);

		painter.drawLine(x0, y0, newPosition.getX(), newPosition.getY(), currentState.getColor(), DEFUALT_LINE_WIDTH);
		currentState.setPosition(newPosition);
	}

}
