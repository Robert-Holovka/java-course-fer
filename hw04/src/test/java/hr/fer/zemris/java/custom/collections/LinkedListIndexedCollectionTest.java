package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void createEmptyList() {
		try {
			@SuppressWarnings("unused")
			LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void createListFromNonExistingCollection() {
		try {
			@SuppressWarnings("unused")
			LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void createListFromCollection() {
		LinkedListIndexedCollection<String> list1 = new LinkedListIndexedCollection<>();
		list1.add("0");
		list1.add("1");
		LinkedListIndexedCollection<String> list2 = new LinkedListIndexedCollection<>(list1);
		list2.add("3");
		assertArrayEquals(new int[] { 2, 3 }, new int[] { list1.size(), list2.size() });
	}

	@Test
	public void isEmptyNoElementsInList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		assertEquals(true, list.isEmpty());
	}

	@Test
	public void isEmptyOneElementInList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		list.add("Something");
		assertEquals(false, list.isEmpty());
	}

	@Test
	public void sizeWithEmptyList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		assertEquals(0, list.size());
	}

	@Test
	public void sizeWithThreeElements() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		assertEquals(3, list.size());
	}

	@Test
	public void containsEmptyList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		assertEquals(false, list.contains("Something"));
	}

	@Test
	public void containsExistingValues() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		for (int i = 0; i < 3; i++) {
			list.add(String.valueOf(i));
		}

		boolean[] expected = new boolean[] { true, true, true };
		boolean[] actual = new boolean[] { list.contains("0"), list.contains("1"), list.contains("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void addNull() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		try {
			list.add(null);
			fail("List should not contain null values");
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void addElements() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		assertArrayEquals(new Object[] { "0", "1", "2" }, list.toArray());
	}

	@Test
	public void removeNonExistingElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		assertEquals(false, list.remove("4"));
	}

	@Test
	public void removeFirstElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		assertEquals(true, list.remove("0"));
	}

	@Test
	public void removeMidElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		assertEquals(true, list.remove("1"));
	}

	@Test
	public void removeLastElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		assertEquals(true, list.remove("2"));
	}

	@Test
	public void toArrayEmptyList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		assertEquals(0, list.toArray().length);
	}

	@Test
	public void toArrayMultipleValues() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		Object[] expected = { "0", "1", "2" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void clearList() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		list.clear();
		assertTrue(list.isEmpty());
	}

	@Test
	public void getElementBadIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		try {
			list.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getFirstElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		assertEquals("0", list.get(0));
	}

	@Test
	public void getSecondElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		assertEquals("1", list.get(1));
	}

	@Test
	public void getMidElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		assertEquals("2", list.get(2));
	}

	@Test
	public void getFourthElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		assertEquals("3", list.get(3));
	}

	@Test
	public void getLastElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		assertEquals("4", list.get(4));
	}

	@Test
	public void indexOfNonExistingElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		assertEquals(-1, list.indexOf("3"));
	}

	@Test
	public void indexOfMultipleElements() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);

		int[] expected = new int[] { 0, 1, 2 };
		int[] actual = new int[] { list.indexOf("0"), list.indexOf("1"), list.indexOf("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeElementBadIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 3);
		try {
			list.remove(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void removeElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 1);
		list.remove(0);

		assertEquals(true, list.isEmpty());
	}

	@Test
	public void removeFirstElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.remove(0);

		Object[] expected = { "1", "2", "3", "4" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeMidElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.remove(2);

		Object[] expected = { "0", "1", "3", "4" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeLastElementWithIndex() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.remove(4);

		Object[] expected = { "0", "1", "2", "3" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertNull() {
		try {
			LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
			addElements(list, 3);

			list.insert(null, 0);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void insertElement() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.insert("i", 4);

		Object[] expected = { "0", "1", "2", "3", "i", "4" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementAtBeginning() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.insert("i", 0);

		Object[] expected = { "i", "0", "1", "2", "3", "4" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementAtMid() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.insert("i", 2);

		Object[] expected = { "0", "1", "i", "2", "3", "4" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementAtEnd() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		addElements(list, 5);
		list.insert("i", 5);

		Object[] expected = { "0", "1", "2", "3", "4", "i" };
		Object[] actual = list.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void forEachRemove() {
		LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>();
		list.add("1");
		list.add("2");
		LinkedListIndexedCollection<String> list2 = new LinkedListIndexedCollection<>();
		class LocalProcessor implements Processor<String> {
			@Override
			public void process(String value) {
				list2.add(value);
			}
		}
		list.forEach(new LocalProcessor());
		assertEquals(2, list2.size());
	}
	
	@Test
	public void addAllSatisfying() {
		LinkedListIndexedCollection<Integer> list = new LinkedListIndexedCollection<>();
		list.add(1);
		list.add(2);
		LinkedListIndexedCollection<Integer> list2 = new LinkedListIndexedCollection<>();
		class LocalTester implements Tester<Integer> {
			@Override
			public boolean test(Integer obj) {
				return obj == 2;
			}
		}
		list2.addAllSatisfying(list, new LocalTester());
		assertEquals(1, list2.size());
	}

	@Test
	public void addAllFromOther() {
		LinkedListIndexedCollection<String> list1 = new LinkedListIndexedCollection<>();
		addElements(list1, 3);

		LinkedListIndexedCollection<String> list2 = new LinkedListIndexedCollection<>(list1);
		list2.addAll(list1);

		assertArrayEquals(new int[] { 3, 6 }, new int[] { list1.size(), list2.size() });
	}

	public void addElements(LinkedListIndexedCollection<String> list, int n) {
		for (int i = 0; i < n; i++) {
			list.add(String.valueOf(i));
		}
	}

}
