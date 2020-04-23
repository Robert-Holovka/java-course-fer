package hr.fer.zemris.java.gui.calc.components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Basic calculator button with custom look.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CalcButton extends JButton {

	/**
	 * Default border line thickness.
	 */
	private static final int DEFAULT_BORDER_WIDTH = 2;
	/**
	 * Smokey light blue color.
	 */
	private static final Color LIGHT_SMOKEY_BLUE = new Color(184, 218, 215);
	/**
	 * Smokey, dark blue color.
	 */
	private static final Color DARK_SMOKEY_BLUE = new Color(99, 153, 163);
	/**
	 * Font size of a normal sized text.
	 */
	private static final float DEFAULT_TEXT_SIZE = 20f;

	/**
	 * Default constructor.
	 */
	public CalcButton() {
		this("");
	}

	/**
	 * Constructs instance of this class with given name.
	 * 
	 * @param text Button text
	 */
	public CalcButton(String text) {
		this(text, DEFAULT_TEXT_SIZE);
	}

	/**
	 * Constructs instance of this class with given name.
	 * 
	 * @param text     Button text
	 * @param fontSize
	 */
	public CalcButton(String text, float fontSize) {
		super(text);
		setBackground(LIGHT_SMOKEY_BLUE);
		setBorder(BorderFactory.createLineBorder(DARK_SMOKEY_BLUE, DEFAULT_BORDER_WIDTH));
		setFont(getFont().deriveFont(fontSize));
	}
}
