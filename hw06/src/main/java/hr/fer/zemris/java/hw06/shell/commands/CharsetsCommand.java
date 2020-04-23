package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * This command lists names of supported character encoding sets and it take no
 * arguments.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CharsetsCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private CharsetsCommand() {
		name = "charsets";
		description = new LinkedList<>();
		description.add("List names of supported charsets.");
		description.add("Takes no arguments.");
		description.add("Usage example: \"> charsets \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 0)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		env.writeln("List of supported charsets:");
		for (String charset : Charset.availableCharsets().keySet()) {
			env.writeln("- " + charset);
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
			instance = new CharsetsCommand();
		}
		return instance;
	}

}
