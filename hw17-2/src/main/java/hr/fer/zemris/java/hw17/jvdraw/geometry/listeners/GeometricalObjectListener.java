package hr.fer.zemris.java.hw17.jvdraw.geometry.listeners;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Functional interface which every observer on {@link GeometricalObject}
 * changes must implement.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface GeometricalObjectListener {

	/**
	 * Action when {@link GeometricalObject} has changed.
	 * 
	 * @param o changed {@link GeometricalObject} object
	 */
	void geometricalObjectChanged(GeometricalObject o);
}