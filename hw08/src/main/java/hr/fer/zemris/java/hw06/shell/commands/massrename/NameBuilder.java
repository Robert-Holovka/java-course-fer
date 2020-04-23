package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Defines strategy for building new file name.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface NameBuilder {
	/**
	 * Extracts info from FileResult and saves it to the {@link StringBuilder}.
	 * 
	 * @param result Reference to the info about file
	 * @param sb     Reference to the {@link StringBuilder}
	 */
	void execute(FilterResult result, StringBuilder sb);
}
