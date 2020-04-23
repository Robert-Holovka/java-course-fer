package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The root class in the collection hierarchy. Defines some basic operations
 * that every collection must have.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Collection {

	/**
	 * Creates a new instance of <code>Collection</code> class.
	 */
	protected Collection() {
	}

	/**
	 * Checks whether collection is empty. Collection is empty when it contains no
	 * elements.
	 * 
	 * @return True if collections is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0 ? true : false;
	}

	/**
	 * Returns the number of currently stored elements in Collection.
	 * 
	 * @return Size of a collection;
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given reference to the object into collection.
	 * 
	 * @param value Reference to the object
	 */
	public void add(Object value) {
	}

	/**
	 * Checks if collection contains given value.
	 * 
	 * @param value Value for which method will determine its existence in
	 *              collection
	 * @return True if given value is found inside collection, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes first occurrence of the given value from the collection.
	 * 
	 * @param value Value to be removed from collection
	 * @return True if element is found and removed, false otherwise
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array.
	 * 
	 * @return Array filled with elements from this collection
	 * @throws {@link UnsupportedOperationException} Because class
	 *         <code>Collection</code> represents generic type which does not know
	 *         how its successors will iterate through their elements
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls <code>processor.process()</code> for each element of this
	 * collection. The order in which elements will be sent is undefined in this
	 * class.
	 * 
	 * @param processor Instance of {@link Processor} class with suitable
	 *                  implementation of <code>process</code> method which will be
	 *                  executed on every element from this collections
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Copies all elements from the given collection inside this one.
	 * 
	 * @param other Collection from which elements will be copied
	 * @throws {@link NullPointerException} if passed reference to other collection
	 *         is equal to <code>null</code>
	 */
	public void addAll(Collection other) {
		other = Objects.requireNonNull(other);

		class LocalProcessor extends Processor {
			@Override
			public void process(Object value) {
				add(value);
			}
		}

		other.forEach(new LocalProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 */
	public void clear() {
	}
}
