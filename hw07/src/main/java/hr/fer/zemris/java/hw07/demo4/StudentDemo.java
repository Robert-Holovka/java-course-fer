package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Demonstrates usage of a StudentRecord class. Contains numerous methods for
 * selecting student records.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StudentDemo {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		List<String> lines = null;
		Path file = Paths.get("./studenti.txt");

		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Missing file: " + file.getFileName());
			return;
		}

		List<StudentRecord> records = convert(lines);

		System.out.printf("Zadatak 1%n=========%n");
		System.out.println(vratiBodovaViseOd25(records));
		System.out.println();

		System.out.printf("Zadatak 2%n=========%n");
		System.out.println(vratiBrojOdlikasa(records));
		System.out.println();

		System.out.printf("Zadatak 3%n=========%n");
		vratiListuOdlikasa(records).forEach(System.out::println);
		System.out.println();

		System.out.printf("Zadatak 4%n=========%n");
		vratiSortiranuListuOdlikasa(records).forEach(System.out::println);
		System.out.println();

		System.out.printf("Zadatak 5%n=========%n");
		vratiPopisNepolozenih(records).forEach(System.out::println);
		System.out.println();

		System.out.printf("Zadatak 6%n=========%n");
		var map = razvrstajStudentePoOcjenama(records);
		for (int grade : map.keySet()) {
			var students = map.get(grade);
			for (var student : students) {
				System.out.println(grade + " => " + student);
			}
		}
		System.out.println();

		System.out.printf("Zadatak 7%n=========%n");
		var map2 = vratiBrojStudenataPoOcjenama(records);
		for (int grade : map2.keySet()) {
			System.out.println(grade + " : " + map2.get(grade));
		}
		System.out.println();

		System.out.printf("Zadatak 8%n=========%n");
		var map3 = razvrstajProlazPad(records);
		for (boolean fail : map3.keySet()) {
			var students = map3.get(fail);
			for (var student : students) {
				System.out.println(fail + " => " + student);
			}
		}
		System.out.println();
	}

	/**
	 * Filters given list of student records and returns number of students who had
	 * achieved more than 25 points in all exams combined.
	 * 
	 * @param records List of student records
	 * @return Number of students who got more than 25 points
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records
				.stream()
				.filter((r) -> (r.getMI() + r.getZI() + r.getLAB()) > 25.0)
				.count();
	}

	/**
	 * Filters given list of student records and returns number of students that
	 * passed course with best possible grade.
	 * 
	 * @param records List of student records
	 * @return Number of students who got best grade
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter((r) -> r.getFinalGrade() == 5)
				.count();
	}

	/**
	 * Filters given list of student records and returns list of students that
	 * passed their course with best final grade.
	 * 
	 * @param records List of student records
	 * @return List of students who got best grade
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter((r) -> r.getFinalGrade() == 5)
				.collect(Collectors.toList());
	}

	/**
	 * Filters given list of student records and returns sorted list of students
	 * that passed their course with best final grade.
	 * 
	 * @param records List of student records
	 * @return Sorted list of students who got best grade
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		Comparator<StudentRecord> comparator = (r1, r2) -> {
			Double total1 = r1.getLAB() + r1.getMI() + r1.getZI();
			Double total2 = r2.getLAB() + r2.getMI() + r2.getZI();
			return total2.compareTo(total1);
		};
		comparator = comparator.thenComparing(StudentRecord::getLastName).thenComparing(StudentRecord::getFirstName)
				.thenComparing(StudentRecord::getJmbag);

		return records
				.stream()
				.filter((r) -> r.getFinalGrade() == 5)
				.sorted(comparator)
				.collect(Collectors.toList());
	}

	/**
	 * Filters given list of student records and returns list of identifiers of a
	 * students who did not pass course. Records are sorted by identifiers.
	 * 
	 * @param records List of student records
	 * @return List of students who failed course
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records
				.stream()
				.filter((r) -> r.getFinalGrade() == 1)
				.map(StudentRecord::getJmbag)
				.sorted(String::compareTo)
				.collect(Collectors.toList());
	}

	/**
	 * Classifies given list of student records and returns map of students divided
	 * by grades.
	 * 
	 * @param records List of student records
	 * @return Map<Integer, List<StudentRecord>> Students sorted by grades
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	/**
	 * Classifies given list of student records and returns map which tells how many
	 * students got some specific grade.
	 * 
	 * @param records List of student records
	 * @return Map<Integer, Integer> Each grade contains number of student that got
	 *         it
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.toMap(StudentRecord::getFinalGrade, (r) -> 1, (v1, v2) -> v1 + v2));
	}

	/**
	 * Classifies given list of student records and returns map of students divided
	 * by fail/pass.
	 * 
	 * @param records List of student records
	 * @return Map<Integer, List<StudentRecord>> Students divided into 2 groups:
	 *         students who failed course and students who passed course
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records
				.stream()
				.collect(Collectors.partitioningBy((r) -> r.getFinalGrade() > 1));
	}

	/**
	 * Converts textual data to a List of instance of a StudentRecord.
	 * 
	 * @param lines Records in textual format
	 * @return List<StudentRecord>
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		LinkedList<StudentRecord> records = new LinkedList<>();
		for (String line : lines) {
			if (line.isBlank()) {
				continue;
			}

			String data[] = line.split("\\s+");
			if (data.length != 7 || !isNumber(data[3]) || !isNumber(data[4]) || !isNumber(data[5])
					|| !isNumber(data[6]) || Integer.parseInt(data[6]) < 1 || Integer.parseInt(data[6]) > 5) {
				System.out.println("Skipping invalid record: " + line);
			}

			records.add(new StudentRecord(data[0], data[1], data[2], Double.parseDouble(data[3]),
					Double.parseDouble(data[4]), Double.parseDouble(data[5]), Integer.parseInt(data[6])));
		}
		return records;
	}

	/**
	 * Checks whether given text contains number.
	 * 
	 * @param data Text to be checked
	 * @return True if passed String is a number, false otherwise
	 */
	private static boolean isNumber(String data) {
		return data.matches("[0-9]+") || data.matches("^[0-9]+\\.?[0-9]+$");
	}

}
