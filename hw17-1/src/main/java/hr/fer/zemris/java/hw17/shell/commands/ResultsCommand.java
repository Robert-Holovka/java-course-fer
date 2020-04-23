package hr.fer.zemris.java.hw17.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellCommand;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
import hr.fer.zemris.java.hw17.shell.Utils;
import hr.fer.zemris.java.hw17.trazilica.Result;

/**
 * Writes to the console results cached in a {@link Environment}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ResultsCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private ResultsCommand() {
		name = "results";
		description = new LinkedList<>();
		description.add("Lists results from a previously executed query.");
		description.add("This command doesn't take any arguments.");
		description.add("Usage example: \"> results \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		List<Result> results = env.getResults();
		if (results == null || results.size() == 0) {
			env.writeln("Nema rezultata. Napravite prvo novi upit!");
		} else {
			for (int i = 0; i < results.size(); i++) {
				env.write("[" + i + "] ");
				env.writeln(results.get(i).toString());
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new ResultsCommand();
		}
		return instance;
	}

}
