package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.editors.GeometricalObjectEditor;

/**
 * Custom list which displays state of {@link DrawingModel}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JCustomList extends JList<GeometricalObject> {

	/**
	 * Default list width.
	 */
	public static final int DEFAULT_WIDTH = 200;
	/**
	 * Default list height.
	 */
	public static final int DEFAULT_HEIGHT = 100;
	/**
	 * Object which holds all geometrical objects.
	 */
	private DrawingModel model;
	/**
	 * Root container.
	 */
	private JComponent topLevelContainer;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param model             Object which holds all geometrical objects
	 * @param topLevelContainer Root container
	 */
	public JCustomList(DrawingModel model, JComponent topLevelContainer) {
		super(new DrawingObjectListModel(model));
		this.model = model;
		this.topLevelContainer = topLevelContainer;

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initListeners();
	}

	/**
	 * Initializes this component listeners.
	 */
	private void initListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int index = getSelectedIndex();
				if (e.getClickCount() != 2 || index == -1) {
					return;
				}
				GeometricalObject clicked = model.getObject(index);
				GeometricalObjectEditor editor = clicked.createGeometricalObjectEditor();
				if (JOptionPane.showConfirmDialog(topLevelContainer, editor, "Edit geometrical object",
						JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

					try {
						editor.checkEditing();
						editor.acceptEditing();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(topLevelContainer, ex.getMessage());
					}
				}
			}
		});

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int index = getSelectedIndex();
				if (index == -1) {
					return;
				}

				switch (e.getKeyCode()) {
				case KeyEvent.VK_DELETE:
					model.remove(model.getObject(index));
					break;

				case KeyEvent.VK_PLUS:
					model.changeOrder(model.getObject(index), -1);
					break;

				case KeyEvent.VK_MINUS:
					model.changeOrder(model.getObject(index), 1);
					break;
				}
			}
		});
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
}
