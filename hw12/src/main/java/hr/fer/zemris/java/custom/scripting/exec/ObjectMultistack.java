package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Data structure which holds multiple stacks. Each stack is defined by its key.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ObjectMultistack {

	/**
	 * Storage for multiple stacks.
	 */
	private Map<String, MultistackEntry> stacks;

	/**
	 * Constructs default instance of this class.
	 */
	public ObjectMultistack() {
		stacks = new HashMap<>();
	}

	/**
	 * Represents one entry that stack holds.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class MultistackEntry {
		/**
		 * Pointer to the next entry.
		 */
		private MultistackEntry next;
		/**
		 * Storage for value.
		 */
		private ValueWrapper value;

		/**
		 * Constructs new instance of this class.
		 * 
		 * @param next  Pointer to the next entry
		 * @param value ValueWrapper value
		 */
		public MultistackEntry(MultistackEntry next, ValueWrapper value) {
			this.next = next;
			this.value = value;
		}
	}

	/**
	 * Adds given value to the stack defined by a passed name.
	 * 
	 * @param keyName      Name of the stack
	 * @param valueWrapper Value to be pushed onto stack
	 * @throws NullPointerException if given key are value is {@code null} reference
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(keyName);
		Objects.requireNonNull(valueWrapper);
		stacks.put(keyName, new MultistackEntry(stacks.get(keyName), valueWrapper));
	}

	/**
	 * Returns and deletes last value pushed to a stack defined by a given key.
	 * 
	 * @param keyName Name of the stack
	 * @return ValueWrapper Top of the defined stack
	 * @throws EmptyStackException if stack is empty
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName) || !stacks.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		var top = stacks.get(keyName);
		stacks.put(keyName, top.next);
		return top.value;
	}

	/**
	 * Returns last value pushed to a stack defined by a given key.
	 * 
	 * @param keyName Name of the stack
	 * @return ValueWrapper Top of the defined stack
	 * @throws EmptyStackException if stack is empty
	 */
	public ValueWrapper peek(String keyName) {
		if (isEmpty(keyName) || !stacks.containsKey(keyName)) {
			throw new EmptyStackException();
		}
		var top = stacks.get(keyName);
		return (top == null) ? null : top.value;
	}

	/**
	 * Checks whether stack defined by a given key is empty.
	 * 
	 * @param keyName Name of the stack
	 * @return True if stack is empty, false otherwise
	 */
	public boolean isEmpty(String keyName) {
		var stack = stacks.get(keyName);
		return stack == null;
	}
}
