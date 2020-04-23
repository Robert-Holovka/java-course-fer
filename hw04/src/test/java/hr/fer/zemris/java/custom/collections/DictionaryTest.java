package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void createDictionary() {
		try {
			Dictionary<Integer, String> d = new Dictionary<>();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void isEmptyWithNoEntriesInDictionary() {
		Dictionary<Integer, String> d = new Dictionary<>();
		assertEquals(true, d.isEmpty());

	}

	@Test
	public void isEmptyWithEntryInDictionary() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(0, "nešto");
		assertEquals(false, d.isEmpty());
	}

	@Test
	public void sizeWithNoEntriesInDictionary() {
		Dictionary<Integer, String> d = new Dictionary<>();
		assertEquals(0, d.size());
	}

	@Test
	public void sizeWithEntryInDictionary() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(5, "nešto");
		assertEquals(1, d.size());
	}

	@Test
	public void clearNonEmptyDictionary() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "nešto");
		d.put(2, "nešto");
		d.put(3, "nešto");
		d.clear();
		assertEquals(0, d.size());
	}

	@Test
	public void equalsSameKeys() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "nešto");
		var e1 = d.get(1);
		var e2 = d.get(1);

		assertTrue(e1.equals(e2));
	}

	@Test
	public void getExistingValue() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "nešto");
		d.put(2, "svašta");
		assertEquals("nešto", d.get(1));
		assertEquals("svašta", d.get(2));
	}

	@Test
	public void getNonExistingValue() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "nešto");
		assertEquals(null, d.get(2));
	}

	@Test
	public void putExistingKeyDifferentValue() {
		Dictionary<Integer, String> d = new Dictionary<>();
		d.put(1, "nešto");
		d.put(1, "svašta");
		assertEquals("svašta", d.get(1));
		assertEquals(1, d.size());
	}

	@Test
	public void putNullKey() {
		assertThrows(NullPointerException.class, () -> new Dictionary<String, String>().put(null, null));
	}
}
