package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.geometry.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

/**
 * Responsible for creating new geometry objects and rendering them.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Canvas current state (State pattern)
	 */
	private Tool currentState;
	/**
	 * Object which holds all geometrical objects.
	 */
	private DrawingModel model;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param currentState Canvas current state.
	 * @param model        Object which holds all geometrical objects
	 */
	public JDrawingCanvas(Tool currentState, DrawingModel model) {
		this.model = model;
		this.currentState = currentState;
		model.addDrawingModelListener(this);

		initMouseListeners();
		repaint();
	}

	/**
	 * Initializes mouse listeners on this component.
	 */
	private void initMouseListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				currentState.mouseClicked(e);
			}
		});

		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				currentState.mouseMoved(e);
			}
		});
	}

	/**
	 * Return current state.
	 * 
	 * @return {@link #currentState}
	 */
	public Tool getCurrentState() {
		return currentState;
	}

	/**
	 * Sets new state of this component.
	 * 
	 * @param currentState new state
	 */
	public void setCurrentState(Tool currentState) {
		this.currentState = currentState;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Draw white background
		Dimension dimension = getSize();
		Insets insets = getInsets();

		g2d.setColor(Color.WHITE);
		g2d.fillRect(insets.left, insets.top, dimension.width - insets.left - insets.right,
				dimension.height - insets.top - insets.bottom);

		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for (var object : model) {
			object.accept(painter);
		}
		currentState.paint((Graphics2D) g);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
