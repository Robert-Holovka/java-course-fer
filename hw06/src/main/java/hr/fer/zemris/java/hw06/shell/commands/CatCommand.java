package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * 
 * This command opens given file and writes its content to console. Command cat
 * takes one or two arguments. The first argument is path to some file and is
 * mandatory. The second argument is character encoding set name that should be
 * used to interpret chars from. If not provided, a default platform encoding
 * should be used.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CatCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private CatCommand() {
		name = "cat";
		description = new LinkedList<>();
		description.add("This command opens given file and writes its content to console.");
		description.add("Command cat takes one or two arguments.");
		description.add("The first argument is path to some file and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset should be used.");
		description.add("Usage example: \"> cat c:/a.txt \"");
		description.add("Usage example: \"> cat c:/a.txt UTF-8 \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!Utils.isValidNumberOfArgs(arguments, 1, 2)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] data = Utils.splitByWhitespace(arguments);
		Path path = Utils.parsePath(data[0]);
		
		if (path == null) {
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(path)) {
			env.writeln(String.format("File \"%s\" does not exist.", path.toString()));
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(path)) {
			env.writeln(String.format("\"%s\" is not a file.", path.toString()));
			return ShellStatus.CONTINUE;
		}

		Charset charset = null;
		try {
			charset = Charset.forName(data[1]);
		} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
			if (e instanceof IllegalArgumentException) {
				env.writeln("Unknown charset provided, reading file with system default charset: "
						+ Charset.defaultCharset().displayName());
			}
			charset = Charset.defaultCharset();
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buff = new byte[DEFAULT_BUFFER_SIZE];

			while (true) {
				int size = is.read(buff);
				if (size < 0) {
					env.writeln("");
					break;
				}
				env.write(new String(Arrays.copyOf(buff, size), charset));
			}
		} catch (Exception e) {
			env.writeln("An error has occurred while opening file.");
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
			instance = new CatCommand();
		}
		return instance;
	}

}
