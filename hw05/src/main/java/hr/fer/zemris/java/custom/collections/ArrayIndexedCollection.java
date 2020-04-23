package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class <code>ArrayIndexedCollection</code> represents resizable and indexed
 * array-backed {@link Collection} of objects. Duplicate values are allowed
 * while storage of <code>null</code> values is not allowed.
 * 
 * @author Robert Holovka
 * @version 1.2
 */
public class ArrayIndexedCollection<T> implements List<T> {

	/**
	 * Counter for structural modifications executed in this collection.
	 */
	private long modificationCount = 0l;

	/**
	 * Default capacity of an array.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Number of elements stored in this array.
	 */
	private int size;

	/**
	 * An array which contains references to objects stored in this collection.
	 */
	private T[] elements;

	/**
	 * Creates new instance of <code>ArrayIndexedCollection</code> class with
	 * capacity set to 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates new instance of <code>ArrayIndexedCollection</code> class with
	 * capacity set to specified value.
	 * 
	 * @param initialCapacity Initial capacity of this collection
	 * @throws {@link IllegalArgumentException} if specified capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(final int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Collection capacity must be greater than 0!");
		}

		elements = (T[]) new Object[initialCapacity];
	}

	/**
	 * Creates new instance of an <code>ArrayIndexedCollection</code> class and
	 * fills it with values from passed {@link Collection}.
	 * 
	 * @param other Subclass of {@link Collection}
	 * @throws {@link NullPointerException} if reference to other collection is
	 *         equal to <code>null</code>
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Creates new instance of <code>ArrayIndexedCollection</code> class with
	 * specified capacity and fills it with values from passed {@link Collection}
	 * 
	 * @param other           Subclass of {@link Collection}
	 * @param initialCapacity Initial capacity of this collection
	 * @throws {@link NullPointerException} if reference to other collection is
	 *         equal to <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		other = Objects.requireNonNull(other, "Reference to the other collection must not be null!");
		initialCapacity = (other.size() > initialCapacity) ? other.size() : initialCapacity;
		elements = (T[]) new Object[initialCapacity];
		addAll(other);
	}

	/**
	 * Returns the number of currently stored elements in this collection.
	 * 
	 * @return Size of a collection;
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection. Since this collection is
	 * resizable average complexity of this method is O(n).
	 * 
	 * @param value to be added in collection
	 * @throws {@link NullPointerException} If given reference to the object is
	 *         equal to the <code>null</code> value
	 */
	@Override
	public void add(T value) {
		value = Objects.requireNonNull(value, "ArrayIndexedCollection does not store null values.");

		if (size == elements.length) {
			elements = reallocateArrayElements(elements.length * 2);
		}

		elements[size++] = value;
		modificationCount++;
	}

	/**
	 * Checks if collection contains given value.
	 * 
	 * @param value Value for which method will determine its existence in
	 *              collection
	 * @return True if given value is found inside collection, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (value == null || isEmpty()) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes first occurrence of the given value from the collection.
	 * 
	 * @param value Value to be removed from collection
	 * @return True if element is found and removed, false otherwise
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index < 0) {
			return false;
		}

		remove(index);
		return true;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with this collection content and returns the array.
	 * 
	 * @return Array filled with elements from this collection
	 */
	@Override
	public Object[] toArray() {
		return reallocateArrayElements(size);
	}

	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
		modificationCount++;
	}

	/**
	 * Returns object that is stored in backing array at position index. Complexity:
	 * O(1).
	 * 
	 * @param index Position of element in this collection
	 * @return Object stored at specified index
	 * @throws {@link IndexOutOfBoundsException} if value of index is outside the
	 *         range [0, size - 1]
	 */
	public T get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array.
	 * Average complexity of this method is O(n) because this collection is
	 * resizable.
	 * 
	 * @param value    Reference to the object that will be inserted in this array
	 * @param position Position in array where reference to object will be inserted
	 * @throws {@link IndexOutOfBoundsException} if value of given index is outside
	 *         the range [0, size]
	 * @throws {@link NullPointerException} If given value is equal to
	 *         <code>null</code>
	 */
	public void insert(T value, int position) {
		value = Objects.requireNonNull(value, "ArrayIndexedCollection can not store null elements!");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (position == size) { // Add new element after last one
			add(value);
		} else {
			/*
			 * Shift last element to (previous position + 1) Reallocate array if needed
			 * Increase size
			 */
			T lastElement = elements[size - 1];
			add(lastElement);

			// Replace old value with passed object
			T toBeShifted = elements[position];
			elements[position] = value;
			T tempValue;
			// Shift old values to (previous position + 1)
			for (int i = position + 1; i < (size); i++) {
				tempValue = elements[i];
				elements[i] = toBeShifted;
				toBeShifted = tempValue;
			}
			modificationCount++;
		}
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. Complexity: O(n).
	 * 
	 * @param value Value for which this method will try to find its position inside
	 *              this collection
	 * @return Position of the first occurrence of the given value or -1 if element
	 *         is not found
	 */
	public int indexOf(Object value) {
		if (value == null || isEmpty()) {
			return -1;
		}

		for (int i = 0; i < size; i++) {
			if (value.equals(elements[i])) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index Position of element that will be removed
	 * @throws {@link IndexOutOfBoundsException} If value of given index is outside
	 *         of the range [0, size - 1]
	 */
	public void remove(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}

		int i = index;
		for (; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		// Position of last non-null value in array
		elements[i] = null;
		size--;
		modificationCount++;
	}

	/**
	 * Creates new array with specified capacity and copies all elements into it
	 * 
	 * @param newCapacity Capacity of new array
	 * @return new array of objects
	 */
	private T[] reallocateArrayElements(int newCapacity) {
		@SuppressWarnings("unchecked")
		T[] reallocatedElements = (T[]) new Object[newCapacity];
		for (int i = 0; i < size; i++) {
			reallocatedElements[i] = elements[i];
		}

		return reallocatedElements;
	}

	/**
	 * Concrete implementation of {@link ElementsGetter}.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class ArrayIterator<E> implements ElementsGetter<E> {
		/**
		 * Reference to the instance that contains this getter.
		 */
		private ArrayIndexedCollection<E> col;
		/**
		 * Number of collection modifications when this getter was created.
		 */
		private long savedModificationCount;
		/**
		 * Position of a first unprocessed element inside collection.
		 */
		private int position;

		/**
		 * Creates new instance of this class with a reference to the creator and saved
		 * number of modifications inside collection which created this getter.
		 * 
		 * @param col                    Reference to the parent collection.
		 * @param savedModificationCount Number of modifications that happened in parent
		 *                               collection before this instance was created.
		 * @throws {@link NullPointerException} If reference to the parent is equal to
		 *         <code>null</code>.
		 */
		private ArrayIterator(ArrayIndexedCollection<E> col, long savedModificationCount) {
			Objects.requireNonNull(col);
			this.col = col;
			this.savedModificationCount = savedModificationCount;
		}

		@Override
		public boolean hasNextElement() {
			if (savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (position == col.size) {
				return false;
			}
			return true;
		}

		@Override
		public E getNextElement() {
			if (savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException();
			}
			if (hasNextElement()) {
				return col.get(position++);
			}
			throw new NoSuchElementException();
		}

	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ArrayIterator<T>(this, modificationCount);
	}

}
