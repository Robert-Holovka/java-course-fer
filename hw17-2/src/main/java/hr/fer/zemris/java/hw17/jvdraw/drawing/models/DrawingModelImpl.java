package hr.fer.zemris.java.hw17.jvdraw.drawing.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.listeners.GeometricalObjectListener;

/**
 * Concrete {@link DrawingModel} implementation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * Storage for {@link GeometricalObject}.
	 */
	private List<GeometricalObject> objects;
	/**
	 * Flag which track whether this model has been modified.
	 */
	private boolean isModified;
	/**
	 * Storage for observers on this model.
	 */
	private Set<DrawingModelListener> listeners;

	/**
	 * Constructs default instance of this class.
	 */
	public DrawingModelImpl() {
		isModified = false;
		objects = new ArrayList<>();
		listeners = new CopyOnWriteArraySet<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		if (index < 0 || index >= getSize()) {
			throw new IllegalArgumentException("Invalid index range: " + index);
		}
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		notifyObjectAdded(getSize());
		object.addGeometricalObjectListener(this);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index = indexOf(object);
		object.removeGeometricalObjectListener(this);
		objects.remove(object);
		notifyObjectRemoved(index);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = indexOf(object);
		if (((index == getSize() - 1) && offset > 0) || ((index == 0) && offset < 0) || offset == 0) {
			return;
		}

		int newIndex = index;
		if (offset > 0) {
			newIndex += offset;
			objects.add(newIndex + 1, object);
			objects.remove(index);
		} else {
			newIndex += offset;
			objects.add(newIndex, object);
			objects.remove(index + 1);
		}

		// Because of drawing hierarchy
		notifyObjectsChanged(0, getSize());
	}

	@Override
	public int indexOf(GeometricalObject object) {
		return objects.indexOf(object);
	}

	@Override
	public void clear() {
		objects.forEach((object) -> object.removeGeometricalObjectListener(this));
		objects.clear();
		notifyListeners();
	}

	@Override
	public void clearModifiedFlag() {
		isModified = false;
	}

	@Override
	public boolean isModified() {
		return isModified;
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		notifyListeners();
	}

	/**
	 * Notifies all observers that this model has changed.
	 */
	private void notifyListeners() {
		isModified = true;
		for (var listener : listeners) {
			listener.objectsAdded(this, 0, 0);
		}
	}

	/**
	 * Notifies all observers that this model received new
	 * {@link GeometricalObject}.
	 * 
	 * @param index of new {@link GeometricalObject}
	 */
	private void notifyObjectAdded(int index) {
		isModified = true;
		for (var listener : listeners) {
			listener.objectsAdded(this, index, index);
		}
	}

	/**
	 * Notifies all observers that this model has removed {@link GeometricalObject}.
	 * 
	 * @param index of the removed {@link GeometricalObject}
	 */
	private void notifyObjectRemoved(int index) {
		isModified = true;
		for (var listener : listeners) {
			listener.objectsRemoved(this, index, index);
		}
	}

	/**
	 * Notifies all observers that this model has changed its collection of
	 * {@link GeometricalObject}.
	 * 
	 * @param from
	 * @param to
	 */
	private void notifyObjectsChanged(int from, int to) {
		isModified = true;
		for (var listener : listeners) {
			listener.objectsChanged(this, from, to);
		}
	}

	/**
	 * Object responsible for iterating thought the collection of
	 * {@link GeometricalObject} stored in this model.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private class GeometricalObjectsIterator implements Iterator<GeometricalObject> {
		/**
		 * Index of the current {@link GeometricalObject}.
		 */
		private int current;

		@Override
		public boolean hasNext() {
			return current < getSize();
		}

		@Override
		public GeometricalObject next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return objects.get(current++);
		}

	}

	@Override
	public Iterator<GeometricalObject> iterator() {
		return new GeometricalObjectsIterator();
	}

}
