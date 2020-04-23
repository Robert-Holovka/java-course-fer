package hr.fer.zemris.java.hw17.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellCommand;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
import hr.fer.zemris.java.hw17.shell.Utils;

/**
 * Simulates simple search engine. For a given set of words it finds the most
 * similar documents and outputs them to the console.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class QueryCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private QueryCommand() {
		name = "query";
		description = new LinkedList<>();
		description.add("Finds 10 closest documents for a given query.");
		description.add("Usage example: \"> query best movies 2019 \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.trim().isEmpty()) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		// Filter query words
		ArrayList<String> words = new ArrayList<>(env.filterQueryWords(arguments));
		for (int i = 0; i < words.size(); i++) {
			if (i == 0) {
				env.write("Query is: [");
			}
			if (i == (words.size() - 1)) {
				env.write(words.get(i) + "]\n");
			} else {
				env.write(words.get(i) + ", ");
			}
		}
		// Process query
		env.processQuery(words);
		// Write results
		env.writeln("10 najboljih rezultata:");
		ResultsCommand.getInstance().executeCommand(env, null);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new QueryCommand();
		}
		return instance;
	}

}
