package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Represents action whose properties text is internationalized.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param name     Key for a internationalized action name
	 * @param desc     Key for a internationalized action description
	 * @param provider i18n provider
	 */
	public LocalizableAction(String name, String desc, ILocalizationProvider provider) {
		this.putValue(Action.NAME, provider.getString(name));
		if (desc != null) {
			this.putValue(Action.SHORT_DESCRIPTION, provider.getString(desc));
		}

		provider.addLocalizationListener(() -> {
			this.putValue(Action.NAME, provider.getString(name));
			if (desc != null) {
				this.putValue(Action.SHORT_DESCRIPTION, provider.getString(desc));
			}
		});
	}

}
