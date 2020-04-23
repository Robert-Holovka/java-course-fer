package hr.fer.zemris.java.hw17.jvdraw.tools;

import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;

/**
 * Concrete state implementation which defines fields and methods necessary for manipulating
 * with a state of the {@link Line} context..
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LineTool extends AbstractTool {

	private boolean isFirstClick;
	private Line currentLine;

	public LineTool(DrawingModel model, IColorProvider foregroundColorProvider) {
		super(model, foregroundColorProvider);
		isFirstClick = true;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isFirstClick) {
			currentLine = new Line(foregroundColorProvider.getCurrentColor(), e.getPoint(), e.getPoint());
			model.add(currentLine);
		} else {
			currentLine.setEndPoint(e.getPoint());
			currentLine = null;
		}
		isFirstClick = !isFirstClick;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (currentLine != null) {
			currentLine.setEndPoint(e.getPoint());
		}
	}

}
