package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * This command changes current directory path.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private CdCommand() {
		name = "cd";
		description = new LinkedList<>();
		description.add("Changes working directory path.");
		description.add("Takes single argument.");
		description.add("Usage example: \"> cd ..\"");
		description.add("Usage example: \"> cd c:\\Desktop\"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		try {
			env.setCurrentDirectory(Paths.get(arguments));
			env.writeln(String.format("Changed to: %s", env.getCurrentDirectory()));
		} catch (IllegalArgumentException e) {
			env.writeln(e.getMessage());
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
			instance = new CdCommand();
		}
		return instance;
	}

}
