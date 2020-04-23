package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.Objects;

import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Action which sets {@link LocalizationProvider} language.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ChangeLanguage implements IAction {

	/**
	 * New {@link LocalizationProvider} language.
	 */
	private String language;

	/**
	 * Constructs new instance of this action defined by a specified language.
	 * 
	 * @param language New {@link LocalizationProvider} language
	 * @throws NullPointerException if given language code is a {@code null}
	 *                              reference
	 */
	public ChangeLanguage(String language) {
		Objects.requireNonNull(language);
		this.language = language;
	}

	@Override
	public void execute() {
		LocalizationProvider.getInstance().setLanguage(language);
	}

}
