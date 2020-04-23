package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldValueGettersTest {

	private static StudentRecord record = new StudentRecord("123", "Prezime", "Ime", 3);

	@Test
	public void getFirstName() {
		assertEquals("Ime", FieldValueGetters.FIRST_NAME.get(record));
	}

	@Test
	public void getLastName() {
		assertEquals("Prezime", FieldValueGetters.LAST_NAME.get(record));
	}

	@Test
	public void getJMBAG() {
		assertEquals("123", FieldValueGetters.JMBAG.get(record));
	}

}
