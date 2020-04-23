package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Registers window with the i18n provider. Window is automatically
 * connected/disconnected to/from provider.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructs new instance of this form.
	 * 
	 * @param window   Window to be connected
	 * @param provider i18n provider
	 */
	public FormLocalizationProvider(JFrame window, ILocalizationProvider provider) {
		super(provider);

		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
				super.windowOpened(e);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
				super.windowClosed(e);
			}
		});
	}

}
