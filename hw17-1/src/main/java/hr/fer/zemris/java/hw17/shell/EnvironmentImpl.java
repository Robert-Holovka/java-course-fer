package hr.fer.zemris.java.hw17.shell;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw17.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw17.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw17.shell.commands.QueryCommand;
import hr.fer.zemris.java.hw17.shell.commands.ResultsCommand;
import hr.fer.zemris.java.hw17.shell.commands.TypeCommand;
import hr.fer.zemris.java.hw17.trazilica.DocumentDatabase;
import hr.fer.zemris.java.hw17.trazilica.Result;

/**
 * Concrete implementation of a Environment.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Object responsible for retrieving textual data from some source.
	 */
	private Scanner sc;
	/**
	 * Unmodifiable storage of commands defined by their keys.
	 */
	private SortedMap<String, ShellCommand> commands;
	/**
	 * Storage for documents for which this environment knows.
	 */
	private DocumentDatabase docs;
	/**
	 * List of resulst from lastly executed query.
	 */
	private List<Result> results;

	/**
	 * Constructs default instance of this class.
	 * 
	 * @throws NullPointerException if path to the documents is a {@code null}
	 *                              reference
	 */
	public EnvironmentImpl(Path documents) {
		Objects.requireNonNull(documents);
		sc = new Scanner(System.in);

		// Close scanner when program terminates
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				sc.close();
			}
		}));

		commands = new TreeMap<>();
		initCommands();
		// Initialize document structure
		docs = new DocumentDatabase(documents);
	}

	/**
	 * Fills {@link #commands} with concrete implementation of classes from
	 * {@link hr.fer.zemris.java.hw17.shell.commands}.
	 */
	private void initCommands() {
		commands.put(HelpCommand.getInstance().getCommandName(), HelpCommand.getInstance());
		commands.put(ExitCommand.getInstance().getCommandName(), ExitCommand.getInstance());
		commands.put(ResultsCommand.getInstance().getCommandName(), ResultsCommand.getInstance());
		commands.put(TypeCommand.getInstance().getCommandName(), TypeCommand.getInstance());
		commands.put(QueryCommand.getInstance().getCommandName(), QueryCommand.getInstance());
		commands = Collections.unmodifiableSortedMap(commands);
	}

	/**
	 * Reads line from standard input and returns it to the caller.
	 * 
	 * @return String line
	 * @throws ShellIOException if line can not be retrieved standard input
	 */
	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			line = sc.nextLine();
		} catch (NoSuchElementException | IllegalStateException e) {
			throw new ShellIOException();
		}
		return line;
	}

	/**
	 * Writes given text to standard output.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to standard output
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Writes given text followed by a new line to standard output.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to standard output
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public long getVocabularySize() {
		return docs.getVocabularySize();
	}

	@Override
	public List<Result> getResults() {
		return results;
	}

	@Override
	public Set<String> filterQueryWords(String arguments) {
		Set<String> words = new LinkedHashSet<>(Utils.extractWords(arguments));
		// Remove words outside vocabulary
		words.retainAll(docs.getVocabulary());
		return words;
	}

	@Override
	public void processQuery(ArrayList<String> words) {
		results = docs.calculateSimilarity(words);
	}

}
