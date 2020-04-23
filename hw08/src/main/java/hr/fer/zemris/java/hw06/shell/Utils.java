package hr.fer.zemris.java.hw06.shell;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines numerous helper methods used by classes in this package.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Utils {

	/**
	 * Message which describes situation when program couldn't perform because of
	 * unfamiliar reason.
	 */
	public static String UNKNOWN_ERROR_MESSAGE = "An unknown error has occurred.";
	/**
	 * Hexadecimal base number.
	 */
	private static final int HEXADECIMAL_BASE = 16;
	/**
	 * Half of the byte.
	 */
	private static final int NIBBLE_SIZE = 4;

	/**
	 * Splits given String by a whitespace and returns chunks of that String as an
	 * array. If string is enclosed by quotes, whitespace is skipped.
	 * 
	 * @param line String to be splitted
	 * @return String array
	 */
	public static String[] splitByWhitespace(String line) {
		if (line == null || line.length() == 0) {
			return new String[0];
		}

		List<String> data = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(line);
		while (m.find()) {
			data.add(m.group(1));
		}

		String[] array = new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			array[i] = data.get(i);
		}

		return array;
	}

	/**
	 * Checks whether given String contains valid number of arguments defined in
	 * {@code expected} array. Arguments are extracted from a given String and
	 * whitespace is used as a delimiter.
	 * 
	 * @param arguments String which contains arguments
	 * @param expected  Array of valid number of arguments
	 * @return True if given String contains valid number of arguments, false
	 *         otherwise
	 */
	public static boolean isValidNumberOfArgs(String arguments, int... expected) {
		if (arguments == null) {
			return containsNumber(expected, 0);
		}

		String[] args = splitByWhitespace(arguments);
		int actual = args.length;
		return containsNumber(expected, actual);
	}

	/**
	 * Checks whether given number is actually stored in passed array {@code args}.
	 * 
	 * @param args Array of integers
	 * @param n    Number to be checked
	 * @return True if given array contains given number, false otherwise
	 */
	private static boolean containsNumber(int[] args, int n) {
		for (int arg : args) {
			if (arg == n) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns message that explains wrong usage of the named Command.
	 * 
	 * @param commandName Name of the wrongly used Command
	 * @return Error message
	 */
	public static String wrongUsageMessage(String commandName) {
		return String.format("Wrong number of arguments for command \"%s\", see \"help %s\" for usage.", commandName,
				commandName);
	}

	/**
	 * Splits given command into two pieces. First piece contains command name,
	 * second piece contains command arguments.
	 * 
	 * @param command String
	 * @return String array
	 */
	public static String[] parseCommand(String command) {
		return command.split("\\s+", 2);
	}

	/**
	 * Returns given text indented by defined number of whitespace characters.
	 * 
	 * @param text             Text to be indented
	 * @param indentationLevel Number of whitespace characters
	 * @return new, indented, String
	 */
	public static String indent(String text, int indentationLevel) {
		return " ".repeat(indentationLevel) + text;
	}

	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();
		for (byte b : bytearray) {
			char firstDigit = Character.forDigit((b >> 4) & 0xF, HEXADECIMAL_BASE);
			char secondDigit = Character.forDigit(b & 0xF, HEXADECIMAL_BASE);
			sb.append(Character.toLowerCase(firstDigit));
			sb.append(Character.toLowerCase(secondDigit));
		}

		return sb.toString();
	}

	/**
	 * Transforms given string to array of bytes. String must contain characters
	 * that are allowed in hexadecimal system and its length must be divisible by
	 * number 2.
	 * 
	 * @param keyText Text representation of hexadecimal number
	 * @return Array of bytes
	 * @throws IllegalArgumentException if given string is not divisible by 2 or if
	 *                                  it contains characters that are not valid in
	 *                                  hexadecimal system
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Key length must be divisible by 2.");
		}

		byte[] bytes = new byte[keyText.length() / 2];
		for (int i = 0; i < keyText.length(); i += 2) {
			int first = extractDigit(keyText.charAt(i));
			int second = extractDigit(keyText.charAt(i + 1));
			bytes[i / 2] = (byte) ((first << NIBBLE_SIZE) + second);
		}

		return bytes;
	}

	/**
	 * Extracts integer value of a hexadecimal character.
	 * 
	 * @param c Hexadecimal character
	 * @return Integer value of {@code c}
	 * @throws IllegalArgumentException if given character is not a valid
	 *                                  hexadecimal
	 */
	private static int extractDigit(Character c) {
		if (Character.digit(c, HEXADECIMAL_BASE) == -1) {
			throw new IllegalArgumentException();
		}

		return Integer.parseInt(String.valueOf(c), HEXADECIMAL_BASE);
	}

	/**
	 * Parses given path, removes quotes, double backslashes and resolves relative
	 * paths.
	 * 
	 * @param path
	 * @param env
	 * @return
	 */
	public static Path parsePath(String path, Environment env) {
		path = removeQuotesEscapeBackslash(path);

		try {
			return env.getCurrentDirectory().resolve(path).normalize();
		} catch (InvalidPathException e) {
			return null;
		}
	}

	/**
	 * Removes beginning and ending quotes from the given string. Replaces all
	 * double backslashes with a single one inside the string.
	 * 
	 * @param line Text to be purified
	 * @return New text
	 */
	public static String removeQuotesEscapeBackslash(String line) {
		if (line.startsWith("\"")) {
			line = line.substring(1);
		}
		if (line.endsWith("\"")) {
			line = line.substring(0, line.length() - 1);
		}

		StringBuilder sb = new StringBuilder();
		int length = line.length();
		for (int i = 0; i < length; i++) {
			sb.append(line.charAt(i));
			if (line.charAt(i) == '\\' && (i + 1) < length && line.charAt(i + 1) == '\\') {
				i++; // Skip escaping
			}
		}
		return sb.toString();
	}

}
