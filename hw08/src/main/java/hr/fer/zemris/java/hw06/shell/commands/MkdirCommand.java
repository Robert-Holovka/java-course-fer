package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Creates the appropriate directory structure. The mkdir command takes a single
 * argument: directory name.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class MkdirCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private MkdirCommand() {
		name = "mkdir";
		description = new LinkedList<>();
		description.add("Creates the appropriate directory structure.");
		description.add("The mkdir command takes a single argument:");
		description.add("- directory name");
		description.add("Usage example: \"> mkdir C:/folderName \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] data = arguments.split("(?<=[\\/])");
		String directoryPath = String.join("", Arrays.copyOfRange(data, 0, data.length - 1));
		String directoryName = data[data.length - 1];

		Path pathWithoutName = Utils.parsePath(directoryPath, env);
		Path fullPath = Utils.parsePath(arguments, env);
		if (fullPath == null) {
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(pathWithoutName)) {
			env.writeln("Invalid path: " + directoryPath);
			return ShellStatus.CONTINUE;
		}

		if (Files.exists(fullPath)) {
			env.writeln(String.format("Directory \"%s\" already exists on a specified path.", directoryName));
			return ShellStatus.CONTINUE;
		}

		try {
			Files.createDirectory(fullPath);
			env.writeln(String.format("Directory \"%s\" created.", directoryName));
		} catch (IOException e) {
			env.writeln(Utils.UNKNOWN_ERROR_MESSAGE);
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
			instance = new MkdirCommand();
		}
		return instance;
	}

}
