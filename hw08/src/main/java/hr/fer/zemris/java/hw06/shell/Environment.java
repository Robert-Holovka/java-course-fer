package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Defines methods that every Environment implementation should have.
 * Environment represents communication bridge between MyShell and ShellCommand.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public interface Environment {

	/**
	 * Reads line from some undefined source and returns it to the caller.
	 * 
	 * @return String line
	 * @throws ShellIOException if line can not be retrieved from source
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes given text to some undefined destination.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to destination
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes given text followed by a new line to some undefined destination.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to destination
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns commands defined in this Environment.
	 * 
	 * @return SortedMap<String, ShellCommand>
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns character which signals that following line is a part of the
	 * multilined text.
	 * 
	 * @return Character
	 */
	Character getMultilineSymbol();

	/**
	 * Sets given character as a multiline symbol.
	 * 
	 * @param symbol which signals that following line is a part of the multilined
	 *               text.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol.
	 * 
	 * @return Character
	 */
	Character getPromptSymbol();

	/**
	 * Sets given character as a new prompt symbol.
	 * 
	 * @param symbol Character
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns character which signals that more lines is coming after current line.
	 * 
	 * @return Character
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets given character as a symbol for more lines.
	 * 
	 * @param symbol which signals that more lines is coming after current line.
	 */
	void setMorelinesSymbol(Character symbol);
	
	/**
	 * Returns path to the working directory.
	 * 
	 * @return Path
	 */
	Path getCurrentDirectory();
	
	/**
	 * Sets given path as a new working directory.
	 * 
	 * @param path
	 */
	void setCurrentDirectory(Path path);
	
	/**
	 * Returns data that is stored under the given key.
	 * 
	 * @param key of the data
	 * @return value
	 */
	Object getSharedData(String key);
	
	/**
	 * Sets data specified by a given value and key.
	 * 
	 * @param key of the data
	 * @return value
	 */
	void setSharedData(String key, Object value);
}
