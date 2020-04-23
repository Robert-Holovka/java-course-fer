package hr.fer.zemris.java.hw17.jvdraw.components.listeners;

import java.awt.Color;

/**
 * Subject who registers observers and notifies them when its color state
 * changes.
 * 
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IColorProvider {

	/**
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Register new observer.
	 * 
	 * @param l observer
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Remove observer.
	 * 
	 * @param l observer
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}