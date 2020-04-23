package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void createDefault() {
		try {
			ArrayIndexedCollection array = new ArrayIndexedCollection();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void createWithCustomCapacity() {
		try {
			ArrayIndexedCollection array = new ArrayIndexedCollection(20);
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}

	}

	@Test
	public void createFromNonExistingCollection() {
		try {
			ArrayIndexedCollection array = new ArrayIndexedCollection(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}

	}

	@Test
	public void createFromOtherCollection() {
		ArrayIndexedCollection array1 = new ArrayIndexedCollection();
		array1 = addElements(array1, 3);
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array1);
		assertArrayEquals(new int[] { 3, 3 }, new int[] { array1.size(), array2.size() });
	}

	@Test
	public void createFromOtherCollectionWithCapacity() {
		ArrayIndexedCollection array1 = new ArrayIndexedCollection();
		array1 = addElements(array1, 3);
		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array1, 100);
		assertArrayEquals(new int[] { 3, 3 }, new int[] { array1.size(), array2.size() });
	}

	@Test
	public void addNullValue() {
		try {
			ArrayIndexedCollection array = new ArrayIndexedCollection();
			array.add(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void addOneValue() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("1");
	}

	@Test
	public void addMultipleValues() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
	}

	@Test
	public void isEmptyNoElementsInArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(true, array.isEmpty());
	}

	@Test
	public void isEmptyOneElementInArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array.add("Something");
		assertEquals(false, array.isEmpty());
	}

	@Test
	public void sizeWithEmptyArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(0, array.size());
	}

	@Test
	public void sizeWithThreeElements() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
		assertEquals(5, array.size());
	}

	@Test
	public void containsEmptyArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		assertEquals(false, array.contains("Something"));
	}

	@Test
	public void containsExistingValues() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);

		boolean[] expected = new boolean[] { true, true, true };
		boolean[] actual = new boolean[] { array.contains("0"), array.contains("1"), array.contains("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeFirstElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);

		assertEquals(true, array.remove("0"));
	}

	@Test
	public void removeMidElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);

		assertEquals(true, array.remove("1"));
	}

	@Test
	public void removeLastElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);

		assertEquals(true, array.remove("2"));
	}

	@Test
	public void toArrayEmptyArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();

		assertEquals(0, array.toArray().length);
	}

	@Test
	public void toArrayMultipleValues() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);

		Object[] expected = { "0", "1", "2" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void clearArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		array.clear();
		assertTrue(array.isEmpty());
	}

	@Test
	public void getElementBadIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		try {
			array.get(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void getFirstElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		assertEquals("0", array.get(0));
	}

	@Test
	public void getMidElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		assertEquals("2", array.get(2));
	}

	@Test
	public void getLastElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
		assertEquals("4", array.get(4));
	}

	@Test
	public void indexOfNonExistingElement() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		assertEquals(-1, array.indexOf("5"));
	}

	@Test
	public void indexOfMultipleElements() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);

		int[] expected = new int[] { 0, 1, 2 };
		int[] actual = new int[] { array.indexOf("0"), array.indexOf("1"), array.indexOf("2") };
		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeElementBadIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 3);
		try {
			array.remove(3);
			fail();
		} catch (IndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void removeElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 1);
		array.remove(0);

		assertEquals(true, array.isEmpty());
	}

	@Test
	public void removeFirstElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
		array.remove(0);

		Object[] expected = { "1", "2", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeMidElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
		array.remove(2);

		Object[] expected = { "0", "1", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeLastElementWithIndex() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 5);
		array.remove(4);

		Object[] expected = { "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void removeElementFromFullArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(5);
		array = addElements(array, 5);
		array.remove(2);

		Object[] expected = { "0", "1", "3", "4" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertNull() {
		try {
			ArrayIndexedCollection array = new ArrayIndexedCollection();
			array = addElements(array, 5);
			array.insert(null, 0);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void insertElementAtBeginning() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 4);
		array.insert("i", 0);

		Object[] expected = { "i", "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementIntoMiddle() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 4);
		array.insert("i", 2);

		Object[] expected = { "0", "1", "i", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementAtEnd() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		array = addElements(array, 4);
		array.insert("i", 4);

		Object[] expected = { "0", "1", "2", "3", "i" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArray() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(4);
		array = addElements(array, 4);
		array.insert("i", 3);

		Object[] expected = { "0", "1", "2", "i", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayAtBeginning() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(4);
		array = addElements(array, 4);
		array.insert("i", 0);

		Object[] expected = { "i", "0", "1", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayIntoMiddle() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(4);
		array = addElements(array, 4);
		array.insert("i", 2);

		Object[] expected = { "0", "1", "i", "2", "3" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void insertElementInFullArrayAtEnd() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(4);
		array = addElements(array, 4);
		array.insert("i", 4);

		Object[] expected = { "0", "1", "2", "3", "i" };
		Object[] actual = array.toArray();

		assertArrayEquals(expected, actual);
	}

	@Test
	public void forEachRemove() {
		ArrayIndexedCollection array = new ArrayIndexedCollection(4);
		array.add("1");
		array.add("2");
		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				array.remove("1");
			}
		}
		array.forEach(new LocalProcessor());
		assertEquals(1, array.size());
	}

	@Test
	public void addAllFromOther() {
		ArrayIndexedCollection array1 = new ArrayIndexedCollection();
		array1 = addElements(array1, 3);

		ArrayIndexedCollection array2 = new ArrayIndexedCollection(array1);
		array2.addAll(array1);

		assertArrayEquals(new int[] { 3, 6 }, new int[] { array1.size(), array2.size() });
	}

	private ArrayIndexedCollection addElements(ArrayIndexedCollection array, int n) {
		for (int i = 0; i < n; i++) {
			array.add(String.valueOf(i));
		}
		return array;
	}
}
