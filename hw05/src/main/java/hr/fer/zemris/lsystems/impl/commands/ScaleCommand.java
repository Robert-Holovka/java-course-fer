package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents scale command. Scales vector that represents current state of
 * Context.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ScaleCommand implements Command {

	/**
	 * Scale factor.
	 */
	private double factor;

	/**
	 * Constructs new instance of this command defined by a given scale factor.
	 * 
	 * @param factor for scaling vectors
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		double prev = ctx.getCurrentState().getMovementLength();
		ctx.getCurrentState().setMovementLength(prev * factor);
	}

}
