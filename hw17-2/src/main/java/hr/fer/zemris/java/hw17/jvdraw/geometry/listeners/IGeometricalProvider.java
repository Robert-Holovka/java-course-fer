package hr.fer.zemris.java.hw17.jvdraw.geometry.listeners;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Interfaces which models Subject - {@link GeometricalObject} who notifies its
 * observers that its internal state has changed.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IGeometricalProvider {

	/**
	 * Adds new listener to this subject.
	 * 
	 * @param l observer
	 */
	void addGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Removes given listener from this subject.
	 * 
	 * @param l observer
	 */
	void removeGeometricalObjectListener(GeometricalObjectListener l);

	/**
	 * Notifies all observers about this subject internal state change.
	 */
	void notifyListeners();

}
