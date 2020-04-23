package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Container;
import java.util.Objects;

import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;

public abstract class AbstractAction implements IAction {
	/**
	 * Root container(frame).
	 */
	protected Container parent;
	/**
	 * Storage for geometrical elements.
	 */
	protected DrawingModel drawingModel;
	/**
	 * Currently opened file.
	 */
	protected DocumentModel documentModel;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent        Root container(frame)
	 * @param drawingModel  Storage for geometrical elements
	 * @param documentModel Currently opened file
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public AbstractAction(Container parent, DrawingModel drawingModel, DocumentModel documentModel) {
		Objects.requireNonNull(parent);
		Objects.requireNonNull(drawingModel);
		Objects.requireNonNull(documentModel);
		this.parent = parent;
		this.drawingModel = drawingModel;
		this.documentModel = documentModel;
	}
}
