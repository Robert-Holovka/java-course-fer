package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents skip command. Calculates new start position of a vector that is
 * defined by start position, direction and length. Length is scaled by a given
 * value.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SkipCommand implements Command {

	/**
	 * Scaler of a line length.
	 */
	private double step;

	/**
	 * Constructs new instance of this command defined by a given step.
	 * 
	 * @param step Scaler of a line length.
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D tempPosition = currentState.getPosition();

		double lineLength = currentState.getMovementLength() * step;
		Vector2D newPosition = tempPosition.copy();

		Vector2D scaledDirection = currentState.getDirection().scaled(lineLength);
		newPosition.translate(scaledDirection);

		currentState.setPosition(newPosition);
	}

}
