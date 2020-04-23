package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Defines methods that every shell command must have.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface ShellCommand {
	/**
	 * Executes this command with provided arguments and writes its result to the
	 * given Environment.
	 * 
	 * @param env       Environment where result will be sent
	 * @param arguments This command arguments
	 * @return ShellStatus Signals to the Shell what to do next
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns name of this command.
	 * 
	 * @return String name of this command
	 */
	String getCommandName();

	/**
	 * Returns this command description as a List of String.
	 * 
	 * @return List<String>
	 */
	List<String> getCommandDescription();
}
