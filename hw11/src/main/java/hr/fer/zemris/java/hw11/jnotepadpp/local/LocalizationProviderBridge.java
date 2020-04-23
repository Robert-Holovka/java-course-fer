package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Bridge between user and the {@link ILocalizationProvider}. Prevents memory
 * leakage. User connects to this bridge and this class will hold all of its
 * component references. User can disconnect this class from provider which will
 * make this class collectible for GC.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Flag which tells whether this class is connected to the provider.
	 */
	private boolean connected;
	/**
	 * Reference to the provider.
	 */
	private ILocalizationProvider provider;
	/**
	 * Current language code.
	 */
	private String currentLanguage;

	/**
	 * Creates new instance of this bridge.
	 * 
	 * @param provider Reference to the i18n provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		connected = false;
		this.provider = provider;
		currentLanguage = "en";
	}

	/**
	 * Reaction on localization settings change.
	 */
	private final ILocalizationListener onLocalizationChange = new ILocalizationListener() {

		@Override
		public void localizationChanged() {
			if (currentLanguage != provider.getCurrentLanguage()) {
				currentLanguage = provider.getCurrentLanguage();
				fire();
			}
		}
	};

	/**
	 * Connects to the provider.
	 */
	public void connect() {
		if (!connected) {
			provider.addLocalizationListener(onLocalizationChange);
			connected = true;
			currentLanguage = provider.getCurrentLanguage();
		}
	}

	/**
	 * Disconnects from the provider.
	 */
	public void disconnect() {
		if (connected) {
			provider.removeLocalizationListener(onLocalizationChange);
			connected = false;
			currentLanguage = provider.getCurrentLanguage();
		}
	}

	@Override
	public String getString(String key) {
		return provider.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return currentLanguage;
	}

}
