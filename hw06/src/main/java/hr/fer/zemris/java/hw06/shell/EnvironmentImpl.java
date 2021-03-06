package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeCommand;

/**
 * Concrete implementation of a Environment. Represents communication bridge
 * between MyShell and ShellCommand.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EnvironmentImpl implements Environment {

	/**
	 * Object responsible for retrieving textual data from some source.
	 */
	private Scanner sc;
	/**
	 * Unmodifiable storage of commands defined by their keys.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Signals that following line is a part of multilined text.
	 */
	private Character multilineSymbol;
	/**
	 * Signals to the user that some input is needed.
	 */
	private Character promptSymbol;
	/**
	 * Signals that after current line more text is coming.
	 */
	private Character moreLinesSymbol;

	/**
	 * Constructs default instance of this class.
	 */
	public EnvironmentImpl() {
		sc = new Scanner(System.in);
		// Close scanner when program terminates
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				sc.close();
			}
		}));

		multilineSymbol = '|';
		promptSymbol = '>';
		moreLinesSymbol = '\\';

		commands = new TreeMap<>();
		initCommands();
	}

	/**
	 * Fills {@link #commands} with concrete implementation of classes from
	 * {@link hr.fer.zemris.java.hw06.commands}.
	 */
	private void initCommands() {
		commands.put(HelpCommand.getInstance().getCommandName(), HelpCommand.getInstance());
		commands.put(ExitCommand.getInstance().getCommandName(), ExitCommand.getInstance());
		commands.put(SymbolCommand.getInstance().getCommandName(), SymbolCommand.getInstance());
		commands.put(CharsetsCommand.getInstance().getCommandName(), CharsetsCommand.getInstance());
		commands.put(CatCommand.getInstance().getCommandName(), CatCommand.getInstance());
		commands.put(LsCommand.getInstance().getCommandName(), LsCommand.getInstance());
		commands.put(TreeCommand.getInstance().getCommandName(), TreeCommand.getInstance());
		commands.put(CopyCommand.getInstance().getCommandName(), CopyCommand.getInstance());
		commands.put(MkdirCommand.getInstance().getCommandName(), MkdirCommand.getInstance());
		commands.put(HexdumpCommand.getInstance().getCommandName(), HexdumpCommand.getInstance());
		commands = Collections.unmodifiableSortedMap(commands);
	}

	/**
	 * Reads line from standard input and returns it to the caller.
	 * 
	 * @return String line
	 * @throws ShellIOException if line can not be retrieved standard input
	 */
	@Override
	public String readLine() throws ShellIOException {
		String line;
		try {
			line = sc.nextLine();
		} catch (NoSuchElementException | IllegalStateException e) {
			throw new ShellIOException();
		}
		return line;
	}

	/**
	 * Writes given text to standard output.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to standard output
	 */
	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	/**
	 * Writes given text followed by a new line to standard output.
	 * 
	 * @param text Text to be written
	 * @throws ShellIOException if this method couldn't perform operation of writing
	 *                          to standard output
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			System.out.println(text);
		} catch (Exception e) {
			throw new ShellIOException();
		}
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = symbol;
	}

}
