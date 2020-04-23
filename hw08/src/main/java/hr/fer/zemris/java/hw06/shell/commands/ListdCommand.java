package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Lists all paths that are saved onto stack.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ListdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private ListdCommand() {
		name = "listd";
		description = new LinkedList<>();
		description.add("Lists all paths that are saved onto stack.");
		description.add("This command takes no arguments.");
		description.add("Usage example: \"> listd");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		if (stack == null || stack.isEmpty()) {
			env.writeln("Nema pohranjenih direktorija.");
			return ShellStatus.CONTINUE;
		}

		stack.forEach((p) -> env.writeln(p.toString()));
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new ListdCommand();
		}
		return instance;
	}

}
