package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents single command. Command is defined by its {@link #execute} method.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface Command {

	/**
	 * Performs some specific action on a given instance of Context and Painter.
	 * 
	 * @param ctx     Context
	 * @param painter Painter
	 */
	void execute(Context ctx, Painter painter);
}
