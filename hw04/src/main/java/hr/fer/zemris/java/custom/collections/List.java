package hr.fer.zemris.java.custom.collections;

/**
 * Defines methods for manipulating with elements of a list type
 * {@link Collection}.
 * 
 * @author Robert Holovka
 * @version 1.1
 * 
 * @param <T> Type of a stored in a list
 */
public interface List<T> extends Collection<T> {

	/**
	 * Returns object that is stored in a list at specified position.
	 * 
	 * @param index Position of element in collection
	 * @return Object stored at specified index
	 * @throws {@link IndexOutOfBoundsException} if value of index is outside the
	 *         range [0, size - 1]
	 */
	T get(int index);

	/**
	 * Inserts (does not overwrite) the given value at the given position in list.
	 * 
	 * @param value    Reference to the object that will be inserted in this list
	 * @param position Position in list where reference to the object will be
	 *                 inserted
	 * @throws {@link IndexOutOfBoundsException} if value of given index is outside
	 *         the range [0, size]
	 * @throws {@link NullPointerException} If given value is equal to
	 *         <code>null</code>
	 */
	void insert(T value, int position);

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value Value for which this method will try to find its position inside
	 *              this collection
	 * @return Position of the first occurrence of the given value or -1 if element
	 *         is not found
	 */
	int indexOf(Object value);

	/**
	 * 
	 * @param index Position of element that will be removed
	 * @throws {@link IndexOutOfBoundsException} If value of given index is outside
	 *         of the range [0, size - 1]
	 */
	void remove(int index);
}
