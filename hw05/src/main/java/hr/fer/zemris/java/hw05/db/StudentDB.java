package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive shell responsible for returning student records that passed query
 * defined by a user.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StudentDB {

	/**
	 * Entry point of the program.đ
	 * 
	 * @param args Arguments from the command line.
	 */
	public static void main(String[] args) {

		List<String> lines = null;
		String file = "database.txt";
		StudentDatabase database;

		try {
			lines = Files.readAllLines(Paths.get("src\\main\\resources\\" + file), StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println("Error occurred while reading from file: " + file);
			return;
		}

		try {
			database = new StudentDatabase(lines);
		} catch (InvalidRecordException e) {
			System.out.println(e.getMessage());
			return;
		}

		Scanner sc = new Scanner(System.in);
		do {
			System.out.printf("> ");
			String line = sc.nextLine().trim();

			if (line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}

			if (!line.startsWith("query")) {
				System.out.println("Query must start with a 'query' command!");
				continue;
			}

			line = line.replaceFirst("query", "");

			QueryParser parser = null;
			try {
				parser = new QueryParser(line);
			} catch (QueryParserException e) {
				System.out.println(e.getMessage());
				continue;
			}

			List<StudentRecord> records = new LinkedList<>();
			if (parser.isDirectQuery()) {
				System.out.println("Using index for record retrieval.");
				var record = database.forJMBAG(parser.getQueriedJMBAG());
				if (record != null) {
					records.add(record);
				}
			} else {
				records = database.filter(new QueryFilter(parser.getQuery()));
			}

			List<String> output = RecordFormatter.format(records);
			output.forEach(System.out::println);
			System.out.println();

		} while (true);
		sc.close();
	}
}
