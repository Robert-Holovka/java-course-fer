package hr.fer.zemris.java.hw17.jvdraw.drawing.models;

import javax.swing.AbstractListModel;
import javax.swing.JList;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Adapter for providing necessary informations to the {@link JList} components
 * that want to display collection of {@link GeometricalObject}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DrawingObjectListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * {@link DrawingModel} instance.
	 */
	private DrawingModel model;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param model {@link DrawingModel} instance.
	 */
	public DrawingObjectListModel(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
	}

	@Override
	public int getSize() {
		return model.getSize();
	}

	@Override
	public GeometricalObject getElementAt(int index) {
		return model.getObject(index);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}

}
