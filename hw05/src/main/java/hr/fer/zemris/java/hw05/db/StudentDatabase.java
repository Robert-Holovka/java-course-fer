package hr.fer.zemris.java.hw05.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Storage for student records. Provides methods for selecting specific
 * record/records.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StudentDatabase {

	/**
	 * List of student records.
	 */
	private List<StudentRecord> recordsList;
	/**
	 * Stores entries. Entry is defined by its key(student identifier) and
	 * value(StudentRecord).
	 */
	private Map<String, StudentRecord> recordsMap;

	/**
	 * Constructs new instance of database which stores student records.
	 * 
	 * @param records Records of a students in textual format
	 * @throws NullPointerException if given reference to records is {@code null}.
	 */
	public StudentDatabase(List<String> records) {
		Objects.requireNonNull(records);
		recordsList = new LinkedList<>();
		recordsMap = new HashMap<>(records.size());
		fillDatabase(records);
	}

	/**
	 * Finds student with given identifier and returns its record.
	 * 
	 * @param jmbag Identifier for the student
	 * @return StudentRecord
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return recordsMap.get(jmbag);
	}

	/**
	 * Returns all records that passed some given criteria.
	 * 
	 * @param filter Conditional for selecting student records
	 * @return List of student records
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> filteredRecords = new LinkedList<>();

		for (StudentRecord record : recordsList) {
			if (filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}

		return filteredRecords;
	}

	/**
	 * Fills this database with instances of StudentRecord class. Records are parsed
	 * from textual format.
	 * 
	 * @param records Student records in textual format
	 * @throws InvalidRecordException if one of records does not meet valid format
	 */
	private void fillDatabase(List<String> records) {
		for (String line : records) {
			String[] data = line.split("\t");

			if (data.length != 4) {
				throw new InvalidRecordException("Record: \"" + line + "\" is not valid.");
			}

			String key = data[0];
			String grade = data[3];
			if (recordsMap.containsKey(key) || !isValidGrade(grade)) {
				throw new InvalidRecordException("Record: \"" + line + "\" is not valid.");
			}

			StudentRecord record = new StudentRecord(key, data[1], data[2], Integer.parseInt(grade));
			recordsList.add(record);
			recordsMap.put(key, record);
		}
	}

	/**
	 * Checks whether given grade is valid. Valid grade range [1,5].
	 * 
	 * @param grade Grade in textual format
	 * @return True if given grade is valid, false otherwise
	 */
	private boolean isValidGrade(String grade) {
		String gradePattern = "^[1-5]{1}$";
		return grade.matches(gradePattern);
	}
}
