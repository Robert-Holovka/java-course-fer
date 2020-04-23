package hr.fer.zemris.java.hw17.jvdraw.actions;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Models one {@link JVDraw} action.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IAction {
	/**
	 * Trigger for action execution.
	 */
	void execute();
}
