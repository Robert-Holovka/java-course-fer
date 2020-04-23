package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw17.shell.Environment;
import hr.fer.zemris.java.hw17.shell.EnvironmentImpl;
import hr.fer.zemris.java.hw17.shell.ShellCommand;
import hr.fer.zemris.java.hw17.shell.ShellIOException;
import hr.fer.zemris.java.hw17.shell.ShellStatus;
import hr.fer.zemris.java.hw17.shell.Utils;

/**
 * Konzola is a console based application which allows user to execute some
 * commands with multiple arguments, mostly for processing and searching
 * documents. Commands are provided through standard input, and user will be
 * asked to type them in after this program starts. List of available commands
 * can be retrieved with the command "help", while description for specific
 * command is obtained with the following command "help command-name".
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Konzola {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Commands from the argument line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected path to the directory with documents.");
			return;
		}

		Path dir = Paths.get(args[0]);
		if (!Files.exists(dir) || !Files.isDirectory(dir)) {
			System.out.println("Given path is not a valid directory.");
			return;
		}

		try {
			processCommands(dir);
		} catch (ShellIOException e) {
			if (e.getMessage() != null) {
				System.out.println(e.getMessage());
			} else {
				System.out.println("Something went wrong. Terminating...");
			}
			System.exit(1);
		}
	}

	/**
	 * Fetches user input and calls appropriate command implementations.
	 * 
	 * @throws ShellIOException If command execution has failed
	 */
	private static void processCommands(Path dir) throws ShellIOException {
		Environment env = new EnvironmentImpl(dir);
		env.writeln(String.format("Veličina riječnika je %d riječi.\n", env.getVocabularySize()));
		ShellStatus status = ShellStatus.CONTINUE;

		do {
			env.write("Enter command > ");
			String[] commandData = Utils.parseCommand(env.readLine());

			String commandName = commandData[0];
			String commandArgs = (commandData.length == 1) ? null : commandData[1];
			ShellCommand command = env.commands().get(commandName);

			if (command == null) {
				env.writeln(String.format("Nepoznata naredba."));
				continue;
			}

			status = command.executeCommand(env, commandArgs);
			env.writeln("");
		} while (status == ShellStatus.CONTINUE);
	}
}
