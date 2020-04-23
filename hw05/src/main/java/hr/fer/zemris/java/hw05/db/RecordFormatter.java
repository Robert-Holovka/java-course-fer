package hr.fer.zemris.java.hw05.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents class that provides method for formatting output of query results.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RecordFormatter {

	/**
	 * Number of characters for a longest identifier of a student contained in
	 * database.
	 */
	private static int longestJMBAG = 0;
	/**
	 * Number of characters for a longest last name contained in database.
	 */
	private static int longestLastName = 0;
	/**
	 * Number of characters for a longest first name contained in database.
	 */
	private static int longestFirstName = 0;

	/**
	 * Formats given records in a form of a simple table.
	 * 
	 * @param records Records of the students
	 * @return Formatted table
	 */
	public static List<String> format(List<StudentRecord> records) {
		List<String> output = new LinkedList<>();

		for (StudentRecord record : records) {
			int length = record.getJmbag().length();
			longestJMBAG = (length > longestJMBAG) ? length : longestJMBAG;

			length = record.getLastName().length();
			longestLastName = (length > longestLastName) ? length : longestLastName;

			length = record.getFirstName().length();
			longestFirstName = (length > longestFirstName) ? length : longestFirstName;
		}

		StringBuilder sb = new StringBuilder();
		if (records.size() > 0) {
			output.add(getBorderLine(sb));
		}

		for (StudentRecord record : records) {
			sb.setLength(0);

			// Write jmbag
			sb.append("| ");
			sb.append(record.getJmbag());
			sb.append(new String(new char[longestJMBAG - record.getJmbag().length() + 1]).replace('\0', ' '));
			sb.append("|");

			// Write last name
			sb.append(" ");
			sb.append(record.getLastName());
			sb.append(new String(new char[longestLastName - record.getLastName().length() + 1]).replace('\0', ' '));
			sb.append("|");

			// Write first name
			sb.append(" ");
			sb.append(record.getFirstName());
			sb.append(new String(new char[longestLastName - record.getFirstName().length() + 1]).replace('\0', ' '));
			sb.append("|");

			// Write final grade
			String grade = String.valueOf(record.getFinalGrade());
			sb.append(" ");
			sb.append(grade);
			sb.append(" |");

			output.add(sb.toString());
		}

		if (records.size() > 0) {
			output.add(getBorderLine(sb));
		}

		output.add("Records selected: " + records.size());
		return output;
	}

	/**
	 * Constructs border line of a table.
	 * 
	 * @param sb StringBuilder
	 * @return String that represents border line of the table
	 */
	private static String getBorderLine(StringBuilder sb) {
		sb.setLength(0);
		sb.append("+");
		sb.append(new String(new char[longestJMBAG + 2]).replace('\0', '='));
		sb.append("+");
		sb.append(new String(new char[longestLastName + 2]).replace('\0', '='));
		sb.append("+");
		sb.append(new String(new char[longestFirstName + 2]).replace('\0', '='));
		sb.append("+");
		sb.append(new String(new char[3]).replace('\0', '='));
		sb.append("+");
		return sb.toString();
	}
}
