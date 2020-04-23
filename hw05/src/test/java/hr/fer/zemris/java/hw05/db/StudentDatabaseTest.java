package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StudentDatabaseTest {

	@Test
	public void createDatabaseCorrect() {
		try {
			@SuppressWarnings("unused")
			var db = new StudentDatabase(fillRecords());
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void createDatabaseDuplicatedJMBAG() {
		List<String> records = fillRecords();
		records.add("0000000001	Akšamović	Marin	2");
		assertThrows(InvalidRecordException.class, () -> new StudentDatabase(records));
	}

	@Test
	public void createDatabaseInvalidGrade() {
		List<String> records = fillRecords();
		records.add("0000000011	Akšamović	Marin	0");
		assertThrows(InvalidRecordException.class, () -> new StudentDatabase(records));
	}

	@Test
	public void createDatabaseWrongNumberOfArguments() {
		List<String> records = fillRecords();
		records.add("0000000011	Akšamović	Marin");
		assertThrows(InvalidRecordException.class, () -> new StudentDatabase(records));
	}

	@Test
	public void forExistingJMBAG() {
		var db = new StudentDatabase(fillRecords());
		StudentRecord expected = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		assertEquals(expected, db.forJMBAG("0000000001"));
	}

	@Test
	public void forNonExistingJMBAG() {
		var db = new StudentDatabase(fillRecords());
		assertNull(db.forJMBAG("2222200021"));
	}

	@Test
	public void filterPositiveGrades() {
		List<String> records = fillRecords();
		var db = new StudentDatabase(records);
		List<StudentRecord> filteredRecords = db.filter((record) -> record.getFinalGrade() > 1);
		assertEquals(records.size(), filteredRecords.size());
	}

	@Test
	public void filterNegaiveGrades() {
		List<String> records = fillRecords();
		var db = new StudentDatabase(records);
		List<StudentRecord> filteredRecords = db.filter((record) -> record.getFinalGrade() < 2);
		assertEquals(0, filteredRecords.size());
	}

	private List<String> fillRecords() {
		List<String> records = new LinkedList<>();
		records.add("0000000001	Akšamović	Marin	2");
		records.add("0000000002	Bakamović	Petra	3");
		records.add("0000000003	Bosnić	Andrea	4");
		records.add("0000000004	Božić	Marin	5");
		records.add("0000000005	Brezović	Jusufadis	2");
		records.add("0000000006	Cvrlje	Ivan	3");
		records.add("0000000007	Čima	Sanjin	4");
		records.add("0000000008	Ćurić	Marko	5");
		records.add("0000000009	Dean	Nataša	2");
		records.add("0000000010	Dokleja	Luka	3");
		return records;
	}
}
