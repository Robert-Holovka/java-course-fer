package hr.fer.zemris.java.hw06.shell;

/**
 * MyShell is a console based application which allows user to execute some
 * commands with multiple arguments. Commands are provided through standard
 * input, and user will be asked to type them in after this program starts. List
 * of available commands can be retrieved with the command "help", while
 * description for specific command is obtained with the following command "help
 * command-name".
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class MyShell {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Commands from the argument line
	 */
	public static void main(String[] args) {
		try {
			processCommands();
		} catch (ShellIOException e) {
			System.exit(1);
			// Ne smijem koristiti System.out.println, a ni Environment jer zbog njega se
			// dogodila greska :/
		}
	}

	/**
	 * Fetches user input and calls appropriate command implementations.
	 * 
	 * @throws ShellIOException If operations of reading/writing from some source
	 *                          failed
	 */
	private static void processCommands() throws ShellIOException {
		Environment env = new EnvironmentImpl();
		env.writeln("Welcome to MyShell v 1.0");
		StringBuilder sb = new StringBuilder();
		ShellStatus status = ShellStatus.CONTINUE;

		do {
			env.write(env.getPromptSymbol() + " ");

			String tempLine = env.readLine();
			while (tempLine.endsWith(String.valueOf(env.getMorelinesSymbol()))) {
				sb.append(tempLine.substring(0, tempLine.length() - 1));
				env.write(env.getMultilineSymbol() + " ");
				tempLine = env.readLine();
			}
			sb.append(tempLine);

			String[] commandData = Utils.parseCommand(sb.toString());
			sb.setLength(0);

			String commandName = commandData[0];
			String commandArgs = (commandData.length == 1) ? null : commandData[1];
			ShellCommand command = env.commands().get(commandName);

			if (command == null) {
				env.writeln(String.format("Command \"%s\" does not exist!", commandName));
				continue;
			}

			status = command.executeCommand(env, commandArgs);
		} while (status == ShellStatus.CONTINUE);
	}

}
