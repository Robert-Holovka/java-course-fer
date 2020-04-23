package hr.fer.zemris.java.custom.collections;

/**
 * Defines some basic operations that every collection must have.
 * 
 * @author Robert Holovka
 * @version 1.2
 * 
 * @param <T> Type of a value which this collection stores
 */
public interface Collection<T> {

	/**
	 * Checks whether collection is empty. Collection is empty when it contains no
	 * elements.
	 * 
	 * @return True if collections is empty, false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of currently stored elements in Collection.
	 * 
	 * @return Size of a collection;
	 */
	int size();

	/**
	 * Adds the given reference to the object into collection.
	 * 
	 * @param value Reference to the object
	 */
	void add(T value);

	/**
	 * Checks if collection contains given value.
	 * 
	 * @param value Value for which method will determine its existence in
	 *              collection
	 * @return True if given value is found inside collection, false otherwise
	 */
	boolean contains(Object value);

	/**
	 * Removes first occurrence of the given value from the collection.
	 * 
	 * @param value Value to be removed from collection
	 * @return True if element is found and removed, false otherwise
	 */
	boolean remove(Object value);

	/**
	 * Allocates new array with size equals to the size of this collections, fills
	 * it with collection content and returns the array.
	 * 
	 * @return Array filled with elements from this collection
	 */
	Object[] toArray();

	/**
	 * Method calls <code>processor.process()</code> for each element of this
	 * collection.
	 * 
	 * @param processor Instance of {@link Processor} class with suitable
	 *                  implementation of <code>process</code> method which will be
	 *                  executed on every element from this collections
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Copies all elements from the given collection inside this one.
	 * 
	 * @param other Collection from which elements will be copied
	 * @throws {@link NullPointerException} if passed reference to other collection
	 *         is equal to <code>null</code>
	 */
	default void addAll(Collection<? extends T> other) {
		other.forEach(this::add);
	}

	/**
	 * Removes all elements from this collection.
	 */
	void clear();

	/**
	 * Creates new instance of {@link ElementsGetter} and returns it.
	 * 
	 * @return ElementsGetter Reference.
	 */
	ElementsGetter<T> createElementsGetter();

	/**
	 * Adds all elements from given collection that pass some certain conditions
	 * specified by given implementation of a method {@link Tester#test(Object)}.
	 * 
	 * @param col    Reference to some other collection
	 * @param tester Concrete implementation of the method method
	 *               {@link Tester#test(Object)}
	 */
	default void addAllSatisfying(Collection<T> col, Tester<? super T> tester) {
		ElementsGetter<T> getter = col.createElementsGetter();

		while (getter.hasNextElement()) {
			T value = getter.getNextElement();
			if (tester.test(value)) {
				add(value);
			}
		}
	}

}
