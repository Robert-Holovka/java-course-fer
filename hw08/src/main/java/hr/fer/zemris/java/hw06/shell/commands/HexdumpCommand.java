package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * Produces hex-output of a file. Expects single argument.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class HexdumpCommand extends Command {

	/**
	 * Lower limit for acceptable byte value.
	 */
	private static final byte MIN_SUPPORTED_BYTE = (byte) 32;
	/**
	 * Upper limit for acceptable byte value.
	 */
	private static final byte MAX_SUPPORTED_BYTE = (byte) 127;

	private static final int HEX_16 = 16;

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private HexdumpCommand() {
		name = "hexdump";
		description = new LinkedList<>();
		description.add("Produces hex-output of a file.");
		description.add("Expects single argument:");
		description.add("- file name");
		description.add("Usage example: \"> hexdump c:/a.txt \"");
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
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(path)) {
			env.writeln(String.format("File \"%s\" does not exist.", path));
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(path)) {
			env.writeln(String.format("\"%s\" is not a file.", path.getFileName()));
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(Files.newInputStream(path))) {
			byte[] buff = new byte[HEX_16];
			int offset = 0;
			StringBuilder sb = new StringBuilder();

			while (true) {
				int size = is.read(buff);
				if (size < 0) {
					break;
				}
				// 1st column
				sb.append(String.format("%08d", offset));
				sb.append(": ");
				// 2nd column
				String hex = Utils.bytetohex(Arrays.copyOf(buff, size)).toUpperCase();
				int charsPrinted = 0;
				for (int i = 0; i < hex.length(); i += 2) {
					sb.append(hex.charAt(i));
					sb.append(hex.charAt(i + 1));
					charsPrinted += 2;
					if (i == (HEX_16 - 2)) {
						sb.append("|");
					} else {
						sb.append(" ");
					}
					charsPrinted++;
				}

				if (hex.length() < (HEX_16)) {
					sb.append(" ".repeat(23 - charsPrinted));
					charsPrinted += 23 - charsPrinted;
					sb.append("|");
					charsPrinted++;
				}

				if (hex.length() < (2 * HEX_16)) {
					sb.append(" ".repeat(48 - charsPrinted));
				}

				sb.append("|");
				sb.append(" ");

				// 3rd column
				for (int i = 0; i < hex.length(); i += 2) {
					byte b = Utils.hextobyte(hex.substring(i, i + 2))[0];
					char c = (b < MIN_SUPPORTED_BYTE || b > MAX_SUPPORTED_BYTE) ? '.' : (char) b;
					sb.append(c);
				}

				env.write(sb.toString());
				env.writeln("");
				sb.setLength(0);
				offset += 10;
			}
		} catch (IOException e) {
			env.writeln(Utils.UNKNOWN_ERROR_MESSAGE);
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
			instance = new HexdumpCommand();
		}
		return instance;
	}

}
