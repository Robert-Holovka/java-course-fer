package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Signals MyShell to terminate. This command doesn't take any arguments.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ExitCommand extends Command {
	
	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private ExitCommand() {
		name = "exit";
		description = new LinkedList<>();
		description.add("Terminates MyShell.");
		description.add("This command doesn't take any arguments.");
		description.add("Usage example: \"> exit \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.TERMINATE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new ExitCommand();
		}
		return instance;
	}

}
