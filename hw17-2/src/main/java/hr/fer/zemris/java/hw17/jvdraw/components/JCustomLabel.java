package hr.fer.zemris.java.hw17.jvdraw.components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw17.jvdraw.components.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.components.listeners.IColorProvider;

/**
 * Custom label that registers on color state providers. Displays info about
 * providers currently stored colors.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JCustomLabel extends JLabel implements ColorChangeListener {

	/**
	 * Default font size.
	 */
	private static final int DEFAULT_FONT_SIZE = 20;
	/**
	 * Foreground color provider.
	 */
	private JColorArea foregroundArea;
	/**
	 * Background color provider.
	 */
	private JColorArea backgroundArea;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param foregroundArea Foreground color provider
	 * @param backgroundArea Background color provider
	 */
	public JCustomLabel(JColorArea foregroundArea, JColorArea backgroundArea) {
		this.foregroundArea = foregroundArea;
		this.backgroundArea = backgroundArea;

		foregroundArea.addColorChangeListener(this);
		backgroundArea.addColorChangeListener(this);
		setFont(new Font("Arial", Font.BOLD, DEFAULT_FONT_SIZE));
		updateText();
	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		updateText();
	}

	/**
	 * Updates this label text.
	 */
	private void updateText() {
		Color background = backgroundArea.getCurrentColor();
		Color foreground = foregroundArea.getCurrentColor();

		StringBuilder sb = new StringBuilder();

		sb.append("Foreground color: (");
		sb.append(foreground.getRed() + ", ");
		sb.append(foreground.getGreen() + ", ");
		sb.append(foreground.getBlue() + "), ");

		sb.append("background color: (");
		sb.append(background.getRed() + ", ");
		sb.append(background.getGreen() + ", ");
		sb.append(background.getBlue() + ").");

		setText(sb.toString());
	}

}
