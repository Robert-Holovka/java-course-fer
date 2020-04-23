package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Collection which stores a list of entries. Each entry is specified by its key
 * and value. Entries are considered as equal if their keys match. Key can not
 * be a {@code null} reference, value can. Adding new entry which key already
 * exists will result in altering that entry instead of adding a new one.
 * 
 * @author Robert Holovka
 * @version 1.0
 * 
 * @param <K> Key type of an entry
 * @param <V> Value type of an entry
 */
public class Dictionary<K, V> {
	/**
	 * Storage for instances of {@link Entry}.
	 */
	private ArrayIndexedCollection<Entry> entries;

	/**
	 * Creates new instance of a {@code Dictionary}.
	 */
	public Dictionary() {
		entries = new ArrayIndexedCollection<>();
	}

	/**
	 * Helper class which represents one entry defined by its key and value.
	 */
	private class Entry {
		/**
		 * Key of an entry.
		 */
		private Object key;
		/**
		 * Value of an entry.
		 */
		private V value;

		/**
		 * Creates new instance of an entry defined by a given value and key.
		 * 
		 * @param key   Key of an entry
		 * @param value Value of an entry
		 * @throws {@link NullPointerException} if given key is equal to {@code null}
		 *         reference
		 */
		private Entry(Object key, V value) {
			Objects.requireNonNull(key, "Key can not be a null reference.");
			this.key = key;
			this.value = value;
		}

		/**
		 * Checks whether two entries are equal. Entries are considered as equal if
		 * their keys match.
		 * 
		 * @param obj Object to be compared
		 * @return True if entries are equal, false otherwise
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (obj == null)
				return false;
			if (!(obj instanceof Dictionary.Entry))
				return false;

			@SuppressWarnings("unchecked")
			Entry other = (Entry) obj;
			return Objects.equals(key, other.key);
		}
	}

	/**
	 * Checks if dictionary is empty. {@link Dictionary} is empty when it contains
	 * no entries.
	 * 
	 * @return True if dictionary is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return entries.isEmpty();
	}

	/**
	 * Returns the number of currently stored entries in this {@link Dictionary}.
	 * 
	 * @return Size of a dictionary;
	 */
	public int size() {
		return entries.size();
	}

	/**
	 * Removes all entries from this dictionary.
	 */
	public void clear() {
		entries.clear();
	}

	/**
	 * Creates new instance of an entry defined by given key-value pair and stores
	 * it in this table.
	 * 
	 * @param key   Key of an entry
	 * @param value Value of an entry
	 * @throws {@link NullPointerException} if given key is equal to a {@code null}
	 *         reference
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key);
		// Check if key exists
		Entry e = new Entry(key, value);
		int index = entries.indexOf(e);
		if (index < 0) {
			entries.add(e);
		} else {
			entries.get(index).value = value;
		}
	}

	/**
	 * Returns value of an entry specified by a given key. If entry with specified
	 * key does not exist in this dictionary it returns {@code null} reference.
	 * 
	 * @param key Key of an entry
	 * @return Vale of an entry specified by a given key or null reference if entry
	 *         is not found
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}

		int index = entries.indexOf(new Entry(key, null));
		if (index < 0) {
			return null;
		}

		return entries.get(index).value;
	}
}
