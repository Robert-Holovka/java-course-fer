package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Defines methods for fetching an element on demand. Elements are stored in a
 * {@link Collection}. <code>ElementsGetter</code> acts as an iterator, iterates
 * through collection and returns element one by one. Each element is returned
 * only one time.
 * 
 * @author Robert Holovka
 * @version 1.0
 * 
 * @see Collection
 */
public interface ElementsGetter {

	/**
	 * Checks whether there are unprocessed elements in collection. Element is
	 * considered as processed if it has been returned by method
	 * {@link #getNextElement()}.
	 * 
	 * @return True if there is at least 1 unprocessed element in collection, false
	 *         otherwise.
	 * @throws {@link ConcurrentModificationException} If collection has changed
	 *         after creating instance of this type.
	 */
	boolean hasNextElement();

	/**
	 * Returns first unprocessed element in collection. Element is considered as
	 * processed if it has been already returned by this method.
	 * 
	 * @return Object Next element in collection.
	 * @throws {@link ConcurrentModificationException} If collection has changed
	 *         after creating instance of this type.
	 * @throws {@link NoSuchElementException} If all elements were visited and
	 *         returned to the caller.
	 */
	Object getNextElement();

	/**
	 * Executes method {@link Processor#process(Object)} on every unprocessed element
	 * in collection.
	 * 
	 * @param p Concrete implementation of {@link Processor#process(Object)}
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
