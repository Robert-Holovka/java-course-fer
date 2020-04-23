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
 * Removes lastly added path from the stack.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DropdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private DropdCommand() {
		name = "dropd";
		description = new LinkedList<>();
		description.add("Removes lastly added path from the stack.");
		description.add("Takes no argument.");
		description.add("Usage example: \"> dropd \"");
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
			env.writeln("Stack is empty!");
			return ShellStatus.CONTINUE;
		}

		stack.pop();
		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new DropdCommand();
		}
		return instance;
	}

}
