package hr.fer.zemris.java.hw06.shell.commands;

import java.util.Collections;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Must be called with at least one argument. First argument represents symbol
 * name. Valid symbol names are defined in Symbol. If called with only one
 * argument, character that represents named symbol will be returned. Second
 * argument is used for setting new value to named symbols and it must be a
 * single character.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SymbolCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private SymbolCommand() {
		name = "symbol";
		description = new LinkedList<>();
		description.add("Must be called with at least one argument.");
		description.add("First argument represents symbol name.");
		description.add("Valid symbol names are: PROMPT, MORELINES & MULTILINE");
		description.add("If called with only one argument, character that represents named symbol will be returned.");
		description.add(
				"Second argument is used for setting new value to named symbols and it must be a single character.");
		description.add("Usage example: \"> symbol PROMPT \"");
		description.add("Usage example: \"> symbol PROMPT # \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if (!Utils.isValidNumberOfArgs(arguments, 1, 2)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] args = Utils.splitByWhitespace(arguments);

		String symbolName = args[0];
		String newSymbol = (args.length == 1) ? null : args[1];

		// New symbol representation must be a single character
		if (newSymbol != null && newSymbol.length() != 1) {
			env.writeln("New symbol representation must be a single character!");
			return ShellStatus.CONTINUE;
		}

		// Process symbol type
		Symbol type;
		try {
			type = Symbol.valueOf(symbolName);
		} catch (IllegalArgumentException e) {
			// Symbol does not exist
			env.writeln(String.format("Invalid symbol name '%s'", symbolName));
			return ShellStatus.CONTINUE;
		}

		switch (type) {
		case PROMPT:
			processCommand(Symbol.PROMPT, env, newSymbol, Environment::getPromptSymbol, Environment::setPromptSymbol);
			break;
		case MORELINES:
			processCommand(Symbol.MORELINES, env, newSymbol, Environment::getMorelinesSymbol,
					Environment::setMorelinesSymbol);
			break;
		case MULTILINE:
			processCommand(Symbol.MULTILINE, env, newSymbol, Environment::getMultilineSymbol,
					Environment::setMultilineSymbol);
			break;
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Wrapper for setting/getting MyShell symbols.
	 * 
	 * @param type      Type of the symbol
	 * @param env       Environment where result should be printed
	 * @param newSymbol Character which may be new representation of some symbol
	 * @param getter    Getter for character representation of some symbol
	 * @param setter    Setter for character representation of some symbol
	 */
	private void processCommand(Symbol type, Environment env, String newSymbol, Function<Environment, Character> getter,
			BiConsumer<Environment, Character> setter) {

		if (newSymbol != null) {
			setNewSymbol(type, newSymbol, env, getter, setter);
		} else {
			printSymbolInfo(type, env, getter);
		}
	}

	/**
	 * Prints character representation of wanted symbol.
	 * 
	 * @param type   Type of the symbol
	 * @param env    Environment where result should be printed
	 * @param getter Getter for character representation of some symbol
	 */
	private void printSymbolInfo(Symbol type, Environment env, Function<Environment, Character> getter) {
		env.writeln(String.format("Symbol for %s is '%c'", type.name(), getter.apply(env)));
	}

	/**
	 * Sets new character representation for wanted symbol.
	 * 
	 * @param type      Type of the symbol
	 * @param env       Environment where result should be printed
	 * @param newSymbol Character which may be new representation of some symbol
	 * @param getter    Getter for character representation of some symbol
	 * @param setter    Setter for character representation of some symbol
	 */
	private void setNewSymbol(Symbol type, String newSymbol, Environment env, Function<Environment, Character> getter,
			BiConsumer<Environment, Character> setter) {

		char oldChar = getter.apply(env);
		char newChar = newSymbol.charAt(0);
		setter.accept(env, newChar);
		env.writeln(String.format("Symbol for %s changed from '%c' to '%c'", type.name(), oldChar, newChar));
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new SymbolCommand();
		}
		return instance;
	}

}