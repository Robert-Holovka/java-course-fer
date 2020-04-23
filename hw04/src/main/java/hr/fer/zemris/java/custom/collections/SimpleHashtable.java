package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Class represents hash table collection which stores entries. Entry is defined
 * by its key and value. Duplicate keys are not allowed and if some operation is
 * performed on an entry with existing key it will result in altering that
 * entry. Also, key must be not a {@code null} value. For values, duplicates are
 * allowed, as well as storage of null references.
 * 
 * @author Robert Holovka
 * @version 1.0
 *
 * @param <K> Key type of an entry
 * @param <V> Value type of an entry
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Default number of slots in table.
	 */
	private static final int DEFAULT_CAPACITY = 16;
	/**
	 * Defines maximum percentage of entries before table will reallocate by
	 * doubling its size.
	 */
	private static final double OVERFILL_PERCENTAGE = 0.75;
	/**
	 * Storage for instances of entries.
	 */
	private TableEntry<K, V>[] table;
	/**
	 * Number of entries stored in a table.
	 */
	private int size;
	/**
	 * Number of operations performed that are altering table structure.
	 */
	private long modificationCount;

	/**
	 * Creates default instance of this class with capacity set to
	 * {@link #DEFAULT_CAPACITY}.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Creates new instance of this class with capacity set to the given value or to
	 * next number that is power of number 2.
	 * 
	 * 
	 * @param capacity Number of slots in a table.
	 * @throws {@link IllegalArgumentException} if given capacity is less than 1s
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int capacity) {
		if (capacity < 1) {
			throw new IllegalArgumentException();
		}

		if ((capacity & (capacity - 1)) != 0) { // is it power of 2?
			int n = (int) (Math.log(capacity) / Math.log(2));
			capacity = (int) (Math.pow(2, n + 1));
		}

		table = (TableEntry<K, V>[]) new TableEntry[capacity];
		modificationCount = 0L;
	}

	/**
	 * Helper class which represents single entry defined by its key and value.
	 *
	 * @param <C> Key type of an entry
	 * @param <W> Value type of an entry
	 */
	public static class TableEntry<C, W> {
		/**
		 * Key of an entry.
		 */
		private C key;
		/**
		 * Value of an entry.
		 */
		private W value;
		/**
		 * Reference to the next entry in a list.
		 */
		private TableEntry<C, W> next;

		/**
		 * Creates new instance of an entry defined by a given value and key.
		 * 
		 * @param key   Key of an entry
		 * @param value Value of an entry
		 * @throws {@link NullPointerException} if given key is {@code null} reference
		 */
		public TableEntry(C key, W value) {
			Objects.requireNonNull(key);
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the key of an entry.
		 * 
		 * @return key
		 */
		public C getKey() {
			return key;
		}

		/**
		 * Returns value of an entry.
		 * 
		 * @return value
		 */
		public W getValue() {
			return value;
		}

		/**
		 * Overwrites this entry by storing given value.
		 * 
		 * @param value New value for this entry.
		 */
		public void setValue(W value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Returns entry specified by a given key.
	 * 
	 * @param key Key of an entry
	 * @return Entry that contains given key or {@code null} if such is not found
	 */
	private TableEntry<K, V> getTableEntry(Object key) {
		if (key == null) {
			return null;
		}

		var entry = getSlot(key);

		while (entry != null) {
			if (entry.getKey().equals(key)) {
				break;
			}
			entry = entry.next;
		}

		return entry;
	}

	/**
	 * Calculates slot position for a given key.
	 * 
	 * @param key Key of an entry
	 * @return Number of slot where given key is stored or should be stored in the
	 *         future
	 * @throws {@link NullPointerException} If given key is equal to {@code null}
	 *         reference
	 */
	private TableEntry<K, V> getSlot(Object key) {
		Objects.requireNonNull(key);
		int slotIndex = Math.abs(key.hashCode()) % table.length;
		return table[slotIndex];
	}

	/**
	 * Doubles number of slots in a table. Structure may differ from previous one
	 * because entries will be added in a table with higher number of slots.
	 */
	@SuppressWarnings("unchecked")
	private void reallocateTable() {
		size = 0;
		TableEntry<K, V>[] oldTable = table;
		table = (TableEntry<K, V>[]) new TableEntry[table.length * 2];

		for (int i = 0; i < oldTable.length; i++) {
			TableEntry<K, V> entry = oldTable[i];

			while (entry != null) {
				put(entry.key, entry.value);
				entry = entry.next;
			}
		}

		modificationCount++;
	}

	/**
	 * Creates new entry with given key-value pair and puts it into the table. If key
	 * already exists, entry with that key updates its value.
	 * 
	 * @param key   Key of an entry
	 * @param value Value of an entry
	 * @throws {@link NullPointerException} If given key is a {@code null} reference
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);

		int slotIndex = Math.abs(key.hashCode()) % table.length;
		var entry = table[slotIndex];

		// Slot is empty
		if (entry == null) {
			table[slotIndex] = new TableEntry<K, V>(key, value);
			size++;
			modificationCount++;
		} else {
			while (true) {
				// Table already contains given key
				if (entry.key.equals(key)) {
					entry.value = value;
					break;
				}
				// Table does not contain given key
				if (entry.next == null) {
					entry.next = new TableEntry<K, V>(key, value);
					size++;
					modificationCount++;
					break;
				}

				entry = entry.next;
			}
		}

		if (size >= OVERFILL_PERCENTAGE * table.length) {
			reallocateTable();
		}
	}

	/**
	 * Returns value of an entry specified by a given key. If given key does not
	 * exist yet in a table, value {@code null} is returned instead.
	 * 
	 * @param key Key of an entry
	 * @return Value of an entry or {@code null} if entry does not exist
	 */
	public V get(Object key) {
		var entry = getTableEntry(key);
		return (entry == null) ? null : entry.getValue();
	}

	/**
	 * Returns number of entries stored in this table.
	 * 
	 * @return number of entries in a table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether given key exist in a table.
	 * 
	 * @param key Key of an entry
	 * @return True if table contains given key, false otherwise
	 */
	public boolean containsKey(Object key) {
		return getTableEntry(key) != null;
	}

	/**
	 * Checks whether given value exists in a table.
	 * 
	 * @param value Value of an entry
	 * @return True if table contains given value, false otherwise
	 */
	public boolean containsValue(Object value) {
		for (int i = 0; i < table.length; i++) {
			var entry = table[i];

			while (entry != null) {
				if (entry.value.equals(value)) {
					return true;
				}

				entry = entry.next;
			}
		}
		return false;
	}

	/**
	 * Removes entry from a table specified by a given key
	 * 
	 * @param key Key of an entry.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int slotIndex = Math.abs(key.hashCode()) % table.length;
		var entry = table[slotIndex];

		// Slot is empty
		if (entry == null) {
			return;
		}

		// Entry is at 1st position in a slot
		if (entry.key.equals(key)) {
			// Entry is the only element in a slot
			if (entry.next == null) {
				table[slotIndex] = null;
			} else { // Slot contains multiple entries
				table[slotIndex] = entry.next;
			}
			size--;
			modificationCount++;
		} else {
			// Connect previous entry with next one
			while (entry.next != null) {
				if (entry.next.key.equals(key)) {
					entry.next = entry.next.next;
					size--;
					modificationCount++;
					break;
				}
				entry = entry.next;
			}
		}
	}

	/**
	 * Checks whether table is empty. Table is considered as empty if number of
	 * stored entries is equal to zero.
	 * 
	 * @return True if table contains no entries, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes all entries from this table.
	 */
	public void clear() {
		if (!isEmpty()) {
			modificationCount++;
		}

		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		size = 0;
	}

	@Override
	public String toString() {
		String output = "[";

		for (int i = 0; i < table.length; i++) {
			TableEntry<K, V> entry = table[i];

			while (entry != null) {
				output += entry.toString() + ", ";
				entry = entry.next;
			}
		}
		// Remove ', ' after last entry
		if (output.length() != 1) {
			output = output.substring(0, output.length() - 2);
		}

		output += "]";
		return output;
	}

	/**
	 * Class represents implementation of an iterator for this table.
	 */
	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {
		/**
		 * Number of modification in a table when this iterator was created.
		 */
		private long savedModificationCount;
		/**
		 * Number of processed(returned) entries.
		 */
		private int visitedEntries;
		/**
		 * Reference to a current entry in a table which will be returned by next call
		 * of a method {@link #next()}.
		 */
		private TableEntry<K, V> currentEntry;
		/**
		 * Reference to a last returned entry from a table.
		 */
		private TableEntry<K, V> previousEntry;
		/**
		 * Index of a current slot in a table.
		 */
		private int currentSlot;

		/**
		 * Creates new instance of this iterator.
		 */
		private IteratorImpl() {
			savedModificationCount = modificationCount;
			currentEntry = table[0];
			currentSlot = 0;
		}

		/**
		 * Checks whether table contains entry that has not been yet returned by a
		 * method {@link #next()}.
		 * 
		 * @return True if such entry is found, false otherwise
		 * @throws {@link ConcurrentModificationException} if this table has changed its
		 *         structure since this iterator has been created
		 */
		@Override
		public boolean hasNext() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}

			return visitedEntries < size;
		}

		/**
		 * Returns next entry from the this table.
		 * 
		 * @return Next entry
		 * @throws {@link ConcurrentModificationException} if this table has changed its
		 *         structure since this iterator has been created
		 * @throws {@link NoSuchElementException} if this iterator has returned all
		 *         entries from this table
		 */
		@Override
		public TableEntry<K, V> next() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			while (currentEntry == null) {
				currentEntry = table[++currentSlot];
			}

			previousEntry = currentEntry;
			visitedEntries++;
			currentEntry = currentEntry.next;
			return previousEntry;
		}

		/**
		 * Removes last entry returned by a method {@link #next()}. This method can only
		 * be called once for each entry.
		 * 
		 * @throws {@link ConcurrentModificationException} if this table has changed its
		 *         structure since this iterator has been created
		 * @throws {@link IllegalStateException} if this method is called on an entry
		 *         that has already been removed or if method {@link #next()} was never
		 *         called
		 */
		@Override
		public void remove() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException();
			}

			if (previousEntry == null) {
				throw new IllegalStateException();
			}

			SimpleHashtable.this.remove(previousEntry.key);
			savedModificationCount++;
			visitedEntries--;
			previousEntry = null;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
}
