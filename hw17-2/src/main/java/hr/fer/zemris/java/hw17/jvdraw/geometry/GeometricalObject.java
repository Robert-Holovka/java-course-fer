package hr.fer.zemris.java.hw17.jvdraw.geometry;

import java.awt.Color;
import java.awt.Point;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geometry.listeners.GeometricalObjectListener;
import hr.fer.zemris.java.hw17.jvdraw.geometry.listeners.IGeometricalProvider;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectVisitor;

/**
 * Defines methods and fields that every geometry shape must have.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class GeometricalObject implements IGeometricalProvider {

	/**
	 * Shape foreground color.
	 */
	private Color foregroundColor;
	/**
	 * Shape start point coordinates.
	 */
	private Point startPoint;
	/**
	 * List of observers registered on this shape.
	 */
	private Set<GeometricalObjectListener> listeners;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundColor shape foreground color
	 * @param startPoint      shape start point coordinates
	 */
	public GeometricalObject(Color foregroundColor, Point startPoint) {
		this.foregroundColor = foregroundColor;
		this.startPoint = startPoint;
		listeners = new CopyOnWriteArraySet<>();
	}

	/**
	 * @return shape foreground color
	 */
	public Color getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Sets new shape foreground color.
	 * 
	 * @param foregroundColor
	 */
	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
		notifyListeners();
	}

	/**
	 * @return shape start point coordinates
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Sets new start point coordinates.
	 * 
	 * @param startPoint
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
		notifyListeners();
	}

	/**
	 * Method which allows to implement Visitor patter. Shape selects which method
	 * will execute based on its concrete subclass type.
	 * 
	 * @param v
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * @return tool for editing this shape fields
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	@Override
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.add(l);
	}

	@Override
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	@Override
	public void notifyListeners() {
		for (var listener : listeners) {
			listener.geometricalObjectChanged(this);
		}
	}
}
