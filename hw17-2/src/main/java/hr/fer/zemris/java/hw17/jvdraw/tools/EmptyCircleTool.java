package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.utils.GeometryUtils;

/**
 * Concrete state implementation which defines fields and methods necessary for manipulating
 * with a state of the {@link EmptyCircleTool} context..
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EmptyCircleTool extends AbstractTool {

	private Circle currentCircle;

	public EmptyCircleTool(DrawingModel model, IColorProvider foregroundColorProvider) {
		super(model, foregroundColorProvider);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isFirstClick) {
			Point startPoint = e.getPoint();
			int radius = (int) GeometryUtils.distance(startPoint, startPoint);
			currentCircle = new Circle(foregroundColorProvider.getCurrentColor(), startPoint,
					radius);
			model.add(currentCircle);
		} else {
			int radius = (int) GeometryUtils.distance(currentCircle.getStartPoint(), e.getPoint());
			currentCircle.setRadius(radius);
			currentCircle = null;
		}
		isFirstClick = !isFirstClick;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentCircle != null) {
			int radius = (int) GeometryUtils.distance(currentCircle.getStartPoint(), e.getPoint());
			currentCircle.setRadius(radius);
		}
	}

}
