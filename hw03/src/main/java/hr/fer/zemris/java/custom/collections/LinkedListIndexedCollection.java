package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class <code>LinkedListIndexedCollection</code> represents linked list
 * {@link Collection} of objects. Duplicate values are allowed while storage of
 * <code>null</code> values is not allowed.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class LinkedListIndexedCollection implements List {

	/**
	 * Counter for structural modifications executed in this collection.
	 */
	private long modificationCount = 0l;

	/**
	 * Number of elements stored in this list.
	 */
	private int size;
	/**
	 * Reference to the first node in this list.
	 */
	private ListNode first;
	/**
	 * Reference to the last node in this list.
	 */
	private ListNode last;

	/**
	 * Instance of class <code>ListNode</code> represents one node in
	 * <code>LinkedListIndexedCollection</code>
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class ListNode {
		/**
		 * Reference to the previous node in <code>LinkedListIndexedCollection</code>
		 */
		private ListNode previous;
		/**
		 * Reference to the next node in <code>LinkedListIndexedCollection</code>
		 */
		private ListNode next;
		/**
		 * Reference to the value that is stored in this node
		 */
		private Object value;

		/**
		 * Creates new instance of a <code>ListNode</code>
		 * 
		 * @param previous Reference to the previous node in
		 *                 <code>LinkedListIndexedCollection</code>
		 * @param next     Reference to the next node in
		 *                 <code>LinkedListIndexedCollection</code>
		 * @param value    Reference to the object value that this node will store
		 */
		private ListNode(ListNode previous, ListNode next, Object value) {
			this.previous = previous;
			this.next = next;
			this.value = value;
		}
	}

	/**
	 * Creates empty instance of a <code>LinkedListIndexedCollection</code>.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Creates new instance of a <code>LinkedListIndexedCollection</code> class and
	 * fills it with values from passed {@link Collection}.
	 * 
	 * @param other Subclass of {@link Collection}
	 * @throws {@link NullPointerException} if reference to other collection is
	 *         equal to <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection other) {
		other = Objects.requireNonNull(other, "Reference to other collection must not be null!");
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
	 * Adds the given object into this collection.
	 * 
	 * @param value to be added in collection
	 * @throws {@link NullPointerException} If given reference to the object is
	 *         equal to the <code>null</code> value
	 */
	@Override
	public void add(Object value) {
		value = Objects.requireNonNull(value);

		ListNode node = new ListNode(last, null, value);
		if (first == null) {
			first = node;
		} else if (size == 1) {
			first.next = node;
		} else {
			last.next = node;
		}

		last = node;
		size++;
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

		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
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
		if (value == null || isEmpty()) {
			return false;
		}

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
		Object[] elements = new Object[size];

		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			elements[i++] = node.value;
		}

		return elements;
	}

	/**
	 * Removes all elements from this collection.
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
		modificationCount++;
	}

	/**
	 * Returns object that is stored in list at specified position.
	 * 
	 * @param index Position of element in this collection
	 * @return Object stored at specified index
	 * @throws {@link IndexOutOfBoundsException} if value of index is outside the
	 *         range [0, size - 1]
	 */
	public Object get(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}

		return getNode(index).value;
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in list.
	 * Average complexity of this method is O(n).
	 * 
	 * @param value    Reference to the object that will be inserted in this list
	 * @param position Position in list where reference to object will be inserted
	 * @throws {@link IndexOutOfBoundsException} if value of given index is outside
	 *         the range [0, size]
	 * @throws {@link NullPointerException} If given value is equal to
	 *         <code>null</code>
	 */
	public void insert(Object value, int position) {
		value = Objects.requireNonNull(value, "LinkedListIndexedCollection can not store null elements!");
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}
		if (size == 0 || position == size) {
			add(value);
			return;
		}

		ListNode oldNode = getNode(position);
		ListNode newNode = new ListNode(oldNode.previous, oldNode, value);

		if (oldNode.previous != null) {
			oldNode.previous.next = newNode;
		} else {
			first = newNode;
		}

		oldNode.previous = newNode;
		size++;
		modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. Average complexity: O(n).
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

		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.value)) {
				return i;
			}
			i++;
		}

		// Element does not exist
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

		ListNode node = getNode(index);

		if (size == 1) {
			clear();
			return;
		}

		if (node.previous == null) { // First element
			first = node.next;
			first.previous = null;
		} else if (node.next == null) { // Last element
			last = node.previous;
			last.next = null;
		} else { // Mid element
			ListNode previous = node.previous;
			ListNode next = node.next;

			previous.next = next;
			next.previous = previous;
		}

		size--;
		modificationCount++;
	}

	/**
	 * Returns node that is stored in list at specified position.
	 * 
	 * @param index Position of node in this collection
	 * @return Node stored at specified index
	 * @throws {@link IndexOutOfBoundsException} if value of index is outside the
	 *         range [0, size - 1]
	 */
	private ListNode getNode(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException();
		}

		ListNode node;
		if (index > (size / 2 + 1)) {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.next;
			}
		} else {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		}

		return node;
	}

	/**
	 * Concrete implementation of {@link ElementsGetter}.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class LinkedListIterator implements ElementsGetter {
		/**
		 * Reference to the instance that contains this getter.
		 */
		private LinkedListIndexedCollection col;
		/**
		 * Number of collection modification when this getter was created.
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
		private LinkedListIterator(LinkedListIndexedCollection col, long savedModificationCount) {
			Objects.requireNonNull(col);
			this.savedModificationCount = savedModificationCount;
			this.col = col;
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
		public Object getNextElement() {
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
	public ElementsGetter createElementsGetter() {
		return new LinkedListIterator(this, modificationCount);
	}

}
