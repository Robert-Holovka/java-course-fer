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
 * Pushes current directory onto stack.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PushdCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private PushdCommand() {
		name = "pushd";
		description = new LinkedList<>();
		description.add("Pushes current Directory onto stack.");
		description.add("Takes single argument: a path.");
		description.add("Sets current directory to a given path.");
		description.add("Usage example: \"> pushd c:\\Desktop\"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		Path path = Utils.parsePath(arguments, env);
		if (path == null) {
			env.writeln("Invalid path");
			return ShellStatus.CONTINUE;
		}

		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
		if (stack == null) {
			stack = new Stack<Path>();
		}

		stack.push(env.getCurrentDirectory());
		env.setSharedData("cdstack", stack);
		env.setCurrentDirectory(path);

		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new PushdCommand();
		}
		return instance;
	}

}
