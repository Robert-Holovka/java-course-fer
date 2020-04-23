package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Represents default action that does nothing.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DefaultAction extends LocalizableAction {

	/**
	 * Constructs new instance of this action.
	 * 
	 * @param name     Name of the action
	 * @param desc     Description of the action
	 * @param provider Localization provider
	 */
	public DefaultAction(String name, String desc, ILocalizationProvider provider) {
		super(name, desc, provider);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Do nothing
	}

}
