package hr.fer.zemris.java.gui.calc.components;

import java.util.HashSet;

import javax.swing.JCheckBox;

/**
 * Calculator check box which is a Subject in Observer pattern.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CalcCheckBox extends JCheckBox {

	/**
	 * Storage of observers.
	 */
	private HashSet<InverseListener> listeners = new HashSet<>();

	/**
	 * Constructs instance of this class defined by a given text.
	 * 
	 * @param text
	 */
	public CalcCheckBox(String text) {
		super();
		setText(text);

		addActionListener(a -> {
			notifyObservers();
		});
	}

	/**
	 * Notifies all observers that check box value has been changed.
	 */
	private void notifyObservers() {
		listeners.forEach(l -> l.inverse());
	}

	/**
	 * Registers new observer.
	 * 
	 * @param l listener
	 */
	public void addListener(InverseListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	/**
	 * Removes specified observer.
	 * 
	 * @param l
	 */
	public void removeListener(InverseListener l) {
		listeners.remove(l);
	}
}
