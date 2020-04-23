package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.ShellCommand;

/**
 * Base class of a command.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public abstract class Command implements ShellCommand {

	/**
	 * Number of elements that buffer can store.
	 */
	protected static final int DEFAULT_BUFFER_SIZE = 4096;
	/**
	 * Name of the command.
	 */
	protected String name;
	/**
	 * Description of the command.
	 */
	protected List<String> description;

	@Override
	public String getCommandName() {
		return name;
	}

	@Override
	public List<String> getCommandDescription() {
		return description;
	}

}
