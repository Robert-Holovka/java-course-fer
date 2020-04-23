package hr.fer.zemris.java.hw17.jvdraw.drawing.models;

/**
 * Interface which defines methods necessary to implement in order to observer
 * {@link DrawingModel} changes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface DrawingModelListener {

	/**
	 * Action when {@link DrawingModel} has added new object.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Action when {@link DrawingModel} has removed some object.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Action when {@link DrawingModel} has changed some objects.
	 * 
	 * @param source
	 * @param index0
	 * @param index1
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
}