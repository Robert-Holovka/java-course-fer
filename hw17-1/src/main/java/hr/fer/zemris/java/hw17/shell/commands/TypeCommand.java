package hr.fer.zemris.java.hw17.shell.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellCommand;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
import hr.fer.zemris.java.hw17.shell.Utils;
import hr.fer.zemris.java.hw17.trazilica.Result;

/**
 * Outputs document content. This command retrieves single argument, document
 * index. Index is valid only if list of cached results in {@link Environment}
 * has Document at that index.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class TypeCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private TypeCommand() {
		name = "type";
		description = new LinkedList<>();
		description.add("Prints out content of a result document.");
		description.add("Document is selected with index.");
		description.add("To see available documents and their indexes use 'results' command.");
		description.add("Usage example: \"> type 0 \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		if (!Utils.isInteger(arguments)) {
			env.writeln("Document index must be an Integer.");
			return ShellStatus.CONTINUE;
		}

		int index = Integer.parseInt(arguments);
		List<Result> results = env.getResults();
		if (results == null || index >= results.size()) {
			env.writeln("Invalid document index. Use command 'results' to see available indexes.");
			return ShellStatus.CONTINUE;
		}

		// Load and display file.
		Path filePath = results.get(index).getPath();

		// Header
		String header = "Dokument: " + filePath.toString();
		String separator = "-".repeat(header.length() + 2);
		env.writeln(separator);
		env.writeln(header);
		env.writeln(separator);
		env.writeln("");

		// Output file content
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(filePath)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				env.writeln(line);
			}
		} catch (IOException e) {
			env.writeln("Could not open given file: " + filePath.toString());
		}

		env.writeln(separator);
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new TypeCommand();
		}
		return instance;
	}
}
