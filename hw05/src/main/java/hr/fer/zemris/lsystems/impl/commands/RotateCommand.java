package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents rotate command. It rotates current state(vector) from the context
 * by a given angle.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RotateCommand implements Command {

	/**
	 * Angle of a vector.
	 */
	private double angle;

	/**
	 * Constructs new instance of this command defined by a given angle.
	 * 
	 * @param angle Angle of the vector
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
