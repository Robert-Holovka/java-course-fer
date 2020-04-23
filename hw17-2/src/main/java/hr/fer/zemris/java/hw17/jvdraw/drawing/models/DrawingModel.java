package hr.fer.zemris.java.hw17.jvdraw.drawing.models;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Defines actions which every storage for {@link GeometricalObject} must have.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface DrawingModel extends Iterable<GeometricalObject> {
	/**
	 * @return number of stored {@link GeometricalObject}
	 */
	int getSize();

	/**
	 * @param index {@link GeometricalObject} index
	 * @return {@link GeometricalObject} stored at given index
	 */
	GeometricalObject getObject(int index);

	/**
	 * Adds new {@link GeometricalObject} to this model
	 * 
	 * @param object {@link GeometricalObject
	 */
	void add(GeometricalObject object);

	/**
	 * Removes given {@link GeometricalObject} from this model
	 * 
	 * @param object {@link GeometricalObject}
	 */
	void remove(GeometricalObject object);

	/**
	 * Changes order for a given {@link GeometricalObject}. Other elements are
	 * shifted for 1 place.
	 * 
	 * @param object {@link GeometricalObject} to be promoted/demoted
	 * @param offset order change size
	 */
	void changeOrder(GeometricalObject object, int offset);

	/**
	 * Returns index for a given {@link GeometricalObject}.
	 * 
	 * @param object {@link GeometricalObject}
	 * @return index
	 */
	int indexOf(GeometricalObject object);

	/**
	 * Deletes all stored {@link GeometricalObject} from this model.
	 */
	void clear();

	/**
	 * Resets modification flag of this model.
	 */
	void clearModifiedFlag();

	/**
	 * @return Flag which tells whether this model has been modified or not
	 */
	boolean isModified();

	/**
	 * Register listener on this model.
	 * 
	 * @param l observer
	 */
	void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Remove given listener from this model.
	 * 
	 * @param l observer
	 */
	void removeDrawingModelListener(DrawingModelListener l);
}