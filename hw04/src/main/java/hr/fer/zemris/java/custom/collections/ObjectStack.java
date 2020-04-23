package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Class <code>ObjectStack</code> represents stack data structure which stores
 * objects. Duplicate values are allowed while storage of <code>null</code>
 * values is not allowed.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class ObjectStack<T> {

	/**
	 * Storage unit for elements that are pushed to stack.
	 */
	private ArrayIndexedCollection<T> stack;

	/**
	 * Creates new, empty instance of a <code>ObjectStack</code> class.
	 */
	public ObjectStack() {
		stack = new ArrayIndexedCollection<T>();
	}

	/**
	 * Checks whether stack is empty. Stack is empty when it contains no elements.
	 * 
	 * @return True if stack is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	/**
	 * Returns the number of currently stored elements in stack.
	 * 
	 * @return Size of a collection;
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Adds given value to the top of the stack.
	 * 
	 * @param value Value to be stored in stack
	 * @throws {@link NullPointerException} if given value is equal to
	 *         <code>null</code>
	 */
	public void push(T value) {
		value = Objects.requireNonNull(value);
		stack.add(value);
	}

	/**
	 * Removes last value stored on stack and returns it.
	 * 
	 * @return last value stored on stack
	 * @throws {@link EmptyStackException} if there are no elements on stack
	 */
	public T pop() {
		if (stack.isEmpty()) {
			throw new EmptyStackException("Can't perform operation 'pop' on an empty stack");
		}

		int first = stack.size() - 1;
		T value = stack.get(first);
		stack.remove(first);

		return value;
	}

	/**
	 * Returns last element stored on stack.
	 * 
	 * @return last value stored on stack
	 * @throws {@link EmptyStackException} if there are no elements on stack
	 */
	public T peek() {
		if (stack.isEmpty()) {
			throw new EmptyStackException("Can't perform operation 'pop' on an empty stack");
		}

		return stack.get(stack.size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		stack.clear();
	}

}
