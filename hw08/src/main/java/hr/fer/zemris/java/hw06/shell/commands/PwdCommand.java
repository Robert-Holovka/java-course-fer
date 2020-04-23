package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * This command prints working directory.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PwdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private PwdCommand() {
		name = "pwd";
		description = new LinkedList<>();
		description.add("Prints working directory.");
		description.add("Takes no arguments.");
		description.add("Usage example: \"> pwd \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}
		
		env.writeln(env.getCurrentDirectory().toString());
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new PwdCommand();
		}
		return instance;
	}

}
