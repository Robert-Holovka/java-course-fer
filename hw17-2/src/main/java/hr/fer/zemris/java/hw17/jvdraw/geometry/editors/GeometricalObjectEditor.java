package hr.fer.zemris.java.hw17.jvdraw.geometry.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.utils.GeometryUtils;

/**
 * Base geometry shape object editor. Renders form for editing concrete shape.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * Default padding size.
	 */
	private static final int DEFAULT_PADDING = 10;

	/**
	 * Default dimension of form components.
	 */
	private static final Dimension DEFAULT_DIMENSION = new Dimension(30, 20);

	/**
	 * Reference to the {@link GeometricalObject} that this class edits.
	 */
	protected GeometricalObject geometricalObject;
	/**
	 * Title label.
	 */
	private JLabel title;
	/**
	 * Containers for input forms.
	 */
	private JPanel inputForms;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param geometricalObject
	 */
	public GeometricalObjectEditor(GeometricalObject geometricalObject) {
		this.geometricalObject = geometricalObject;

		this.setLayout(new BorderLayout());

		// Initialize title
		title = new JLabel();
		this.add(title, BorderLayout.PAGE_START);
		this.setVisible(true);

		// Initialize space for input forms
		inputForms = new JPanel();
		inputForms.setLayout(new BoxLayout(inputForms, BoxLayout.PAGE_AXIS));
		inputForms.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.add(inputForms, BorderLayout.CENTER);
	}

	/**
	 * Validates result of edit operation.
	 */
	public abstract void checkEditing();

	/**
	 * Accepts edit operation, changes internal state of {@link #geometricalObject}
	 */
	public abstract void acceptEditing();

	/**
	 * Updates title of this editor.
	 * 
	 * @param text New editor title
	 */
	public void setTitle(String text) {
		title.setText(text);
	}

	/**
	 * Adds provided components to this input form.
	 * 
	 * @param forms various components
	 */
	public void addInputForm(JComponent... forms) {
		JPanel newForm = new JPanel();
		newForm.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		newForm.setLayout(new BoxLayout(newForm, BoxLayout.LINE_AXIS));

		for (var f : forms) {
			if (f instanceof JTextField) {
				f.setPreferredSize(DEFAULT_DIMENSION);

			}
			newForm.add(f);
			newForm.add(Box.createHorizontalStrut(DEFAULT_PADDING));
		}

		inputForms.add(newForm);
	}

	/**
	 * Checks whether given coordinate is valid.
	 * 
	 * @param x value on x-axis
	 * @param y value on y-axis
	 * @return True if coordinate is valid, false otherwise
	 */
	public boolean isValidCoordinate(JTextField x, JTextField y) {
		return GeometryUtils.isValidCoordinate(x.getText(), y.getText());
	}

	/**
	 * Checks whether given RGB color components are valid.
	 * 
	 * @param r Red color integer value
	 * @param g Green color component integer value
	 * @param b Blue color component integer value
	 * @return True if color is valid, false otherwise
	 */
	public boolean isValidColor(JTextField r, JTextField g, JTextField b) {
		return GeometryUtils.isValidColorComponent(r.getText()) && GeometryUtils.isValidColorComponent(g.getText())
				&& GeometryUtils.isValidColorComponent(b.getText());
	}

}
