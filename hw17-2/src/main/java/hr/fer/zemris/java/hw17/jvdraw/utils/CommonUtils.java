package hr.fer.zemris.java.hw17.jvdraw.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Some commonly used utility methods.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CommonUtils {

	/**
	 * Checks whether given String is Integer.
	 * 
	 * @param s String
	 * @return True if given String is Integer, false otherwise
	 */
	public static boolean isPositiveInteger(String s) {
		return s.matches("^\\d+$");
	}

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

}
