package hr.fer.zemris.java.hw17.jvdraw.components.listeners;

import java.awt.Color;

/**
 * Functional interface that notifies observers when color from provider has
 * been updated.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface ColorChangeListener {
	/**
	 * Notifies observers that color has changed.
	 * 
	 * @param source   Color provider
	 * @param oldColor Old color value
	 * @param newColor New color value
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}