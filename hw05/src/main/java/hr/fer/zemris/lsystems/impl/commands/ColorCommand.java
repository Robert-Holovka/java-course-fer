package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents color command.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ColorCommand implements Command {

	/**
	 * Color used for changing state of Context.
	 */
	private Color color;

	/**
	 * Constructs new instance of this command defined by given color.
	 * 
	 * @param color Color
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
