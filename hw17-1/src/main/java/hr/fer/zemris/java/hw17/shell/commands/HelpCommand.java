package hr.fer.zemris.java.hw17.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.SortedMap;

import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.ShellCommand;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
import hr.fer.zemris.java.hw17.shell.Utils;

/**
 * Lists all commands if called without arguments. Otherwise, it can receive
 * single argument which represents command name. If command name was given,
 * prints out that command description.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class HelpCommand extends Command {

	/**
	 * Number of whitespace characters used for indentation.
	 */
	private static final int DEFAULT_INDENTATION = 4;

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private HelpCommand() {
		name = "help";
		description = new LinkedList<>();
		description.add("Lists all commands if called without arguments.");
		description.add("Otherwise, it can receive single argument which represents command name.");
		description.add("If command name was given, prints out that command description.");
		description.add("Usage example: \"> help \"");
		description.add("Usage example: \"> help exit \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] args = Utils.splitByWhitespace(arguments);
		switch (args.length) {
		case 0:
			SortedMap<String, ShellCommand> commands = env.commands();
			env.writeln("List of commands:");
			for (String commandName : commands.keySet()) {
				env.writeln("- " + commandName);
			}
			break;
		case 1:
			String commandName = args[0];
			// Command does not exist
			if (!env.commands().containsKey(commandName)) {
				env.writeln(String.format("Command \"%s\" does not exist.", commandName));
			} else { // Command exists
				env.write(commandName + " ".repeat(DEFAULT_INDENTATION));
				int indentationLength = commandName.length() + DEFAULT_INDENTATION;
				boolean first = true;
				for (String desc : env.commands().get(commandName).getCommandDescription()) {
					if (first) {
						env.writeln(desc);
						first = false;
					} else {
						env.writeln(" ".repeat(indentationLength) + desc);
					}
				}
			}
			break;
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
			instance = new HelpCommand();
		}
		return instance;
	}
}
