package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Pops lastly added path to stack and sets it as a current directory.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PopdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private PopdCommand() {
		name = "popd";
		description = new LinkedList<>();
		description.add("Pops lastly added path to stack and sets it as a current directory.");
		description.add("This command takes no arguments.");
		description.add("Usage example: \"> popd ");
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

		Path path = stack.pop();
		if (Files.exists(path)) {
			env.setCurrentDirectory(path);
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
			instance = new PopdCommand();
		}
		return instance;
	}

}
