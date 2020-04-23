package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Abstract {@link ILocalizationProvider} provider. Implements only methods
 * necessary for achieving observer pattern where this class is subject.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Storage for subscribers.
	 */
	private Set<ILocalizationListener> listeners = new CopyOnWriteArraySet<>();

	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies subscribers about change.
	 */
	protected void fire() {
		for (var listener : listeners) {
			listener.localizationChanged();
		}
	}

}
