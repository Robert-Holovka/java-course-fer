package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void createDefault() {
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection<? extends Number> array = new ArrayIndexedCollection<>();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void createWithCustomCapacity() {
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(20);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void createFromNonExistingCollection() {
		try {
			@SuppressWarnings("unused")
			ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

	@Test
	public void createFromOtherCollection() {
		ArrayIndexedCollection<String> array1 = new ArrayIndexedCollection<>();
		addElements(array1, 3);
		ArrayIndexedCollection<String> array2 = new ArrayIndexedCollection<>(array1);
		assertArrayEquals(new int[] { 3, 3 }, new int[] { array1.size(), array2.size() });
	}

	@Test
	public void createFromOtherCollectionWithCapacity() {
		ArrayIndexedCollection<String> array1 = new ArrayIndexedCollection<>();
		addElements(array1, 3);
		ArrayIndexedCollection<String> array2 = new ArrayIndexedCollection<>(array1, 100);
		assertArrayEquals(new int[] { 3, 3 }, new int[] { array1.size(), array2.size() });
	}

	@Test
	public void addNullValue() {
		try {
			ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
			array.add(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void addOneValue() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		array.add("1");
	}

	@Test
	public void addMultipleValues() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
	}

	@Test
	public void isEmptyNoElementsInArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		assertEquals(true, array.isEmpty());
	}

	@Test
	public void isEmptyOneElementInArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		array.add("Something");
		assertEquals(false, array.isEmpty());
	}

	@Test
	public void sizeWithEmptyArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		assertEquals(0, array.size());
	}

	@Test
	public void sizeWithThreeElements() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
		assertEquals(5, array.size());
	}

	@Test
	public void containsEmptyArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		assertEquals(false, array.contains("Something"));
	}

	@Test
	public void containsExistingValues() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);

		boolean[] expected = new boolean[] { true, true, true };
		boolean[] actual = new boolean[] { array.contains("0"), array.contains("1"), array.contains("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeFirstElement() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);

		assertEquals(true, array.remove("0"));
	}

	@Test
	public void removeMidElement() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);

		assertEquals(true, array.remove("1"));
	}

	@Test
	public void removeLastElement() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);

		assertEquals(true, array.remove("2"));
	}

	@Test
	public void toArrayEmptyArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();

		assertEquals(0, array.toArray().length);
	}

	@Test
	public void toArrayMultipleValues() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);

		Object[] expected = { "0", "1", "2" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void clearArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		array.clear();
		assertTrue(array.isEmpty());
	}

	@Test
	public void getElementBadIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		try {
			array.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getFirstElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		assertEquals("0", array.get(0));
	}

	@Test
	public void getMidElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		assertEquals("2", array.get(2));
	}

	@Test
	public void getLastElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
		assertEquals("4", array.get(4));
	}

	@Test
	public void indexOfNonExistingElement() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		assertEquals(-1, array.indexOf("5"));
	}

	@Test
	public void indexOfMultipleElements() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);

		int[] expected = new int[] { 0, 1, 2 };
		int[] actual = new int[] { array.indexOf("0"), array.indexOf("1"), array.indexOf("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeElementBadIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 3);
		try {
			array.remove(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void removeElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 1);
		array.remove(0);

		assertEquals(true, array.isEmpty());
	}

	@Test
	public void removeFirstElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
		array.remove(0);

		Object[] expected = { "1", "2", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeMidElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
		array.remove(2);

		Object[] expected = { "0", "1", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeLastElementWithIndex() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 5);
		array.remove(4);

		Object[] expected = { "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeElementFromFullArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(5);
		addElements(array, 5);
		array.remove(2);

		Object[] expected = { "0", "1", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertNull() {
		try {
			ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
			addElements(array, 5);
			array.insert(null, 0);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void insertElementAtBeginning() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 4);
		array.insert("i", 0);

		Object[] expected = { "i", "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementIntoMiddle() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 4);
		array.insert("i", 2);

		Object[] expected = { "0", "1", "i", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementAtEnd() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
		addElements(array, 4);
		array.insert("i", 4);

		Object[] expected = { "0", "1", "2", "3", "i" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArray() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(4);
		addElements(array, 4);
		array.insert("i", 3);

		Object[] expected = { "0", "1", "2", "i", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayAtBeginning() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(4);
		addElements(array, 4);
		array.insert("i", 0);

		Object[] expected = { "i", "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayIntoMiddle() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(4);
		addElements(array, 4);
		array.insert("i", 2);

		Object[] expected = { "0", "1", "i", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayAtEnd() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(4);
		addElements(array, 4);
		array.insert("i", 4);

		Object[] expected = { "0", "1", "2", "3", "i" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void forEachRemove() {
		ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>(4);
		array.add("1");
		array.add("2");
		ArrayIndexedCollection<String> array2 = new ArrayIndexedCollection<>(4);
		class LocalProcessor implements Processor<String> {
			@Override
			public void process(String value) {
				array2.add("1");
			}
		}
		array.forEach(new LocalProcessor());
		assertEquals(2, array.size());
	}

	@Test
	public void addAllSatisfying() {
		ArrayIndexedCollection<Integer> array = new ArrayIndexedCollection<>(4);
		array.add(1);
		array.add(2);
		array.add(3);
		ArrayIndexedCollection<Integer> array2 = new ArrayIndexedCollection<>(4);
		class LocalTester implements Tester<Integer> {
			@Override
			public boolean test(Integer obj) {
				return obj == 2;
			}
		}
		array2.addAllSatisfying(array, new LocalTester());
		assertEquals(1, array2.size());
	}

	@Test
	public void addAllFromOther() {
		ArrayIndexedCollection<String> array1 = new ArrayIndexedCollection<>();
		addElements(array1, 3);

		ArrayIndexedCollection<String> array2 = new ArrayIndexedCollection<>(array1);
		array2.addAll(array1);

		assertArrayEquals(new int[] { 3, 6 }, new int[] { array1.size(), array2.size() });
	}

	private void addElements(ArrayIndexedCollection<String> array, int n) {
		for (int i = 0; i < n; i++) {
			array.add(String.valueOf(i));
		}
	}
}
