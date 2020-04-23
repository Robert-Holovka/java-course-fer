package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Concrete {@link ILocalizationProvider} implementation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Bundle which loads resources.
	 */
	private ResourceBundle bundle;
	/**
	 * Instance of this class.
	 */
	private static LocalizationProvider instance;
	/**
	 * Language code.
	 */
	private String language;

	/**
	 * Constructs new instance of this class with English as a default language.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}

	/**
	 * @return Single reference to this class.
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	/**
	 * Changes this provider language.
	 * 
	 * @param language Language code
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * @return Current language code.s
	 */
	public String getCurrentLanguage() {
		return language;
	}

}
