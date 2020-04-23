package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;

/**
 * Renders its current color state. On click, opens a dialog where user can
 * select new color state. After color has changed, notifies its observers about
 * it.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default component width.
	 */
	public static final int DEFAULT_WIDTH = 15;
	/**
	 * Default component height;
	 */
	public static final int DEFAULT_HEIGHT = 15;

	/**
	 * Observers registered on this subject.
	 */
	private Set<ColorChangeListener> listeners;
	/**
	 * Currently stored color.
	 */
	private Color selectedColor;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param selectedColor Default color state
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		listeners = new CopyOnWriteArraySet<>();

		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setOpaque(false);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick();
			}
		});
		repaint();
	}

	/**
	 * Opens a {@link JColorChooser} on a mouse click inside this component. Updates
	 * color state if user has selected new color.
	 */
	private void onClick() {
		Color newColor = JColorChooser.showDialog(this, "Select new color", selectedColor);
		if (newColor != null && !newColor.equals(selectedColor)) {
			Color oldColor = selectedColor;
			selectedColor = newColor;
			repaint();
			notifyListeners(oldColor);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Dimension dimension = getSize();
		Insets insets = getInsets();

		g.setColor(selectedColor);
		g.fillRect(insets.left, insets.top, dimension.width, dimension.height);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies observer that color has changed.
	 * 
	 * @param oldColor Old Color value
	 */
	private void notifyListeners(Color oldColor) {
		for (var listener : listeners) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}
	}
}
