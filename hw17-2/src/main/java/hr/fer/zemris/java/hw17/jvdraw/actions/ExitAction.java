package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Container;

import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;

/**
 * Action responsible for terminating the program.
 *
 * @author Robert Holovka
 * @version 1.0
 */
public class ExitAction extends AbstractAction {

	/**
	 * Flag which tells is it safe to terminate the program.
	 */
	public boolean shouldClose;

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param parent        Root container(frame)
	 * @param drawingModel  Storage for geometrical elements
	 * @param documentModel Currently opened file
	 * @throws NullPointerException if any of arguments is a {@code null} reference
	 */
	public ExitAction(Container parent, DrawingModel drawingModel, DocumentModel documentModel) {
		super(parent, drawingModel, documentModel);
		shouldClose = false;
	}

	@Override
	public void execute() {
		if (drawingModel.isModified()) {
			int res = JOptionPane.showConfirmDialog(parent,
					"There is some unsaved work. Do you want save it?",
					"Unsaved work!", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			if (res == JOptionPane.CANCEL_OPTION) {
				return;
			}
			if (res == JOptionPane.NO_OPTION) {
				shouldClose = true;
				return;
			}
			shouldClose = true;
			SaveFileAction sfa = new SaveFileAction(parent, drawingModel, documentModel);
			sfa.execute();
		}
		shouldClose = true;
	}

	/**
	 * Returns flag that tells is it safe to terminate the program.
	 * 
	 * @return {@link #shouldClose()}
	 */
	public boolean shouldClose() {
		return shouldClose;
	}

}
