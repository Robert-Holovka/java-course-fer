package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.List;


/**
 * Represents one file that passed filtering.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class FilterResult {

	/**
	 * Name of the file.
	 */
	private String fileName;
	/**
	 * List of groups that were matched from a {@link RegularExpression}
	 */
	private List<String> groups;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param fileName Name of the file.
	 * @param groups   List of groups that were matched from a
	 *                 {@link RegularExpression}
	 */
	public FilterResult(String fileName, List<String> groups) {
		this.fileName = fileName;
		this.groups = groups;
	}

	@Override
	public String toString() {
		return fileName;
	}

	/**
	 * Returns number of matched groups.
	 * 
	 * @return int
	 */
	public int numberOfGroups() {
		return groups.size();
	}

	/**
	 * Returns matched group defined by a given index.
	 * 
	 * @param index Index of a group
	 * @return String
	 */
	public String group(int index) {
		if (index < 0 || index > numberOfGroups()) {
			throw new IllegalArgumentException();
		}
		return groups.get(index);
	}

}
