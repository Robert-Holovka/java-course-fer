package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener for {@link ILocalizationProvider} changes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface ILocalizationListener {
	/**
	 * Fires on change.
	 */
	void localizationChanged();
}
