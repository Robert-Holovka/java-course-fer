package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;

/**
 * Abstract state which defines fields and methods necessary for manipulating
 * with a state of a context.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class AbstractTool implements Tool {
	/**
	 * {@link DrawingModel} instance
	 */
	protected DrawingModel model;
	/**
	 * Provider for a currently selected foreground color.
	 */
	protected IColorProvider foregroundColorProvider;
	/**
	 * {@link GeometricalObject} start point coordinates.
	 */
	protected Point startPoint;
	/**
	 * 
	 */
	protected boolean isFirstClick;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param model
	 * @param foregroundColorProvider
	 */
	public AbstractTool(DrawingModel model, IColorProvider foregroundColorProvider) {
		this.model = model;
		this.foregroundColorProvider = foregroundColorProvider;
		this.isFirstClick = true;
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void paint(Graphics2D g2d) {
	}
}
