package hr.fer.zemris.java.hw17.shell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Defines numerous helper methods used by classes in this project.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Utils {

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
	 * second piece contains command arguments if they exist.
	 * 
	 * @param command String
	 * @return String array
	 */
	public static String[] parseCommand(String command) {
		return command.split("\\s+", 2);
	}

	/**
	 * Checks whether given String is Integer.
	 * 
	 * @param s String
	 * @return True if given String is Integer, false otherwise
	 */
	public static boolean isInteger(String s) {
		return s.matches("^-?\\d+$");
	}

	/**
	 * Extracts words from a given line and returns them in a List. Word is
	 * considered as a sequence of a characters that are alphabetic.
	 * 
	 * @param line Line with words
	 * @return List of words
	 */
	public static LinkedList<String> extractWords(String line) {
		LinkedList<String> words = new LinkedList<>();
		char[] data = line.toCharArray();

		StringBuilder wordBuilder = new StringBuilder();
		for (char c : data) {
			if (Character.isAlphabetic(c)) {
				wordBuilder.append(c);
			} else {
				if (wordBuilder.length() > 0) {
					String word = wordBuilder.toString().trim().toLowerCase();
					words.add(word);
					wordBuilder.setLength(0);
				}
			}
		}
		
		// Last word
		if (wordBuilder.length() > 0) {
			String word = wordBuilder.toString().trim().toLowerCase();
			words.add(word);
		}
		return words;
	}
}
