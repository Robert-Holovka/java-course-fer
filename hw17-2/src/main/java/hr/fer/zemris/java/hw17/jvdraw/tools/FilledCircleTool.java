package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.utils.GeometryUtils;

/**
 * Concrete state implementation which defines fields and methods necessary for manipulating
 * with a state of the {@link FilledCircle} context..
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class FilledCircleTool extends AbstractTool {

	private IColorProvider backgroundColorProvider;
	private FilledCircle currentCircle;

	public FilledCircleTool(DrawingModel model, IColorProvider foregroundColorProvider,
			IColorProvider backgroundColorProvider) {
		super(model, foregroundColorProvider);
		this.backgroundColorProvider = backgroundColorProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isFirstClick) {
			Point startPoint = e.getPoint();
			int radius = (int) GeometryUtils.distance(startPoint, startPoint);

			currentCircle = new FilledCircle(foregroundColorProvider.getCurrentColor(),
					backgroundColorProvider.getCurrentColor(), startPoint, radius);
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
