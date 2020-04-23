package hr.fer.zemris.java.hw17.shell;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;

import hr.fer.zemris.java.hw17.trazilica.Result;

/**
 * Environment represents communication bridge between Console and ShellCommand.
 * Supports operations for processing documents.
 * 
 * @author Robert Holovka
 * @version 1.0
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
	 * Returns number of words in the vocabulary.
	 * 
	 * @return vocabulary size
	 */
	long getVocabularySize();

	/**
	 * Returns lists of results cached in this environment.
	 * 
	 * @return List of Result instances
	 */
	List<Result> getResults();

	/**
	 * Filters words from a query. Retains only words that can be found inside
	 * defined vocabulary.
	 * 
	 * @param arguments Query arguments
	 * @return Filtered query arguments
	 */
	Set<String> filterQueryWords(String arguments);

	/**
	 * Processes query.
	 * 
	 * @param words List of query arguments.
	 */
	void processQuery(ArrayList<String> words);
}
