package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void createDefaultHashTable() {
		try {
			@SuppressWarnings("unused")
			var table = new SimpleHashtable<Integer, String>();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void createHashTableWithCapacity() {
		try {
			@SuppressWarnings("unused")
			var table = new SimpleHashtable<Integer, String>(9);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void createHashTableWithIllegalCapacity() {
		assertThrows(IllegalArgumentException.class, () -> new SimpleHashtable<Integer, String>(0));
	}

	@Test
	public void isEmptyNoEntries() {
		var table = new SimpleHashtable<Integer, String>();
		assertTrue(table.isEmpty());
	}

	@Test
	public void isEmptyWithFiveEntries() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 5);
		assertFalse(table.isEmpty());
	}

	@Test
	public void sizeNoEntries() {
		var table = new SimpleHashtable<Integer, String>();
		assertEquals(0, table.size());
	}

	@Test
	public void sizeWithFiveEntries() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 5);
		assertEquals(5, table.size());
	}

	@Test
	public void putThreeEntries() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 3);
		assertEquals(3, table.size());
	}
	
	@Test
	public void putNullKey() {
		assertThrows(NullPointerException.class, () -> new SimpleHashtable<Integer, String>(1).put(null, null));
	}
	
	@Test
	public void putExistingKey() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 3);
		table.put("3", 100);
		assertEquals(100, table.get("3"));
	}

	@Test
	public void clearNoEntries() {
		var table = new SimpleHashtable<Integer, String>();
		table.clear();
		assertEquals(0, table.size());
	}

	@Test
	public void clearFiveEntries() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 5);
		table.clear();
		assertEquals(0, table.size());
	}

	@Test
	public void toStringZeroEntries() {
		var table = new SimpleHashtable<String, Integer>();
		assertEquals("[]", table.toString());
	}

	@Test
	public void toStringTwoEntries() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertEquals("[1=1, 2=2]", table.toString());
	}

	@Test
	public void containsExistingKey() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertTrue(table.containsKey("1"));
	}

	@Test
	public void containsNonExistingKey() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertFalse(table.containsKey("3"));
	}

	@Test
	public void containsExistingValue() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertTrue(table.containsValue(1));
	}

	@Test
	public void containsNonExistingValue() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertFalse(table.containsValue(3));
	}

	@Test
	public void getExistingValue() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertEquals(1, table.get("1"));
	}

	@Test
	public void getNonExistingValue() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		assertEquals(null, table.get(3));
	}

	@Test
	public void remove() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 2);
		table.remove("1");
		assertTrue(table.containsKey("2"));
		assertFalse(table.containsKey("1"));
	}

	@Test
	public void iteratorNext() {
		var table = new SimpleHashtable<String, Integer>();
		var table2 = new SimpleHashtable<String, Integer>();
		addEntries(table, 5);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			table2.put(pair.getKey(), pair.getValue());
		}
		assertEquals(5, table2.size());
	}

	@Test
	public void iteratorRemove() {
		var table = new SimpleHashtable<String, Integer>();
		addEntries(table, 5);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = table.iterator();
		while (iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		assertEquals(0, table.size());
	}

	private void addEntries(SimpleHashtable<String, Integer> table, int n) {
		for (int i = 1; i <= n; i++) {
			table.put(String.valueOf(i), i);
		}
	}

}
