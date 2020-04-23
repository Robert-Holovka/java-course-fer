package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents pop command. It removes last state stacked in a Context.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PopCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}
}
