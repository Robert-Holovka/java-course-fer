package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provides methods for i18n.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface ILocalizationProvider {
	/**
	 * Registers new {@link ILocalizationListener}.
	 * 
	 * @param l ILocalizationListener
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes registered {@link ILocalizationListener}.
	 * 
	 * @param l ILocalizationListener
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Returns translation for a given key.
	 * 
	 * @param key Key for a text to be translated
	 * @return Translated text
	 */
	String getString(String key);

	/**
	 * Returns code for a current language.
	 * 
	 * @return language code
	 */
	String getCurrentLanguage();
}
