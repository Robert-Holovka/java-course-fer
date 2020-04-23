package hr.fer.zemris.java.hw07.observer2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Subject which holds one Integer value and a list of {@code observers}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class IntegerStorage {

	/**
	 * Storage for a value.
	 */
	private int value;
	/**
	 * List of subscribed observers.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		observers = new ArrayList<>();
	}

	/**
	 * Helper class which hold more informations about change.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	class IntegerStorageChange {
		/**
		 * Value before change.
		 */
		private int oldValue;
		/**
		 * Value after change.
		 */
		private int newValue;

		/**
		 * Constructs new instance of this class.
		 * 
		 * @param oldValue value before change
		 * @param newValue value after change
		 */
		public IntegerStorageChange(int oldValue, int newValue) {
			this.oldValue = oldValue;
			this.newValue = newValue;
		}

		/**
		 * Returns instance of IntegerStorage class
		 * 
		 * @return IntegerStorage
		 */
		public IntegerStorage getStorage() {
			return IntegerStorage.this;
		}

		/**
		 * Returns value before change.
		 * 
		 * @return int
		 */
		public int getOldValue() {
			return oldValue;
		}

		/**
		 * Returns value after change.
		 * 
		 * @return int
		 */
		public int getNewValue() {
			return newValue;
		}

	}

	/**
	 * Subscribes new observer.
	 * 
	 * @param observer IntegerStorageObserver
	 * @throws NullPointerException if given observer is {@code null} reference
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes specified observer.
	 * 
	 * @param observer IntegerStorageObserver
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		observers.remove(observer);
	}

	/**
	 * Removes all observers.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Returns value stored in this class
	 * 
	 * @return int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets new value.
	 * 
	 * @param value new value
	 */
	public void setValue(int value) {
		if (this.value == value)
			return;
		int oldValue = this.value;
		this.value = value;
		if (observers == null)
			return;

		var changeStorage = new IntegerStorageChange(oldValue, value);
		int size = observers.size();
		for (int i = 0; i < size; i++) {
			var observer = observers.get(i);
			observer.valueChanged(changeStorage);

			if (size != observers.size()) {
				size = observers.size();
				i--;
			}
		}
	}
}
