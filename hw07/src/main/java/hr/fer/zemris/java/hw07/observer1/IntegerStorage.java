package hr.fer.zemris.java.hw07.observer1;

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
		this.value = value;
		if (observers == null)
			return;

		int size = observers.size();
		for (int i = 0; i < size; i++) {
			var observer = observers.get(i);
			observer.valueChanged(this);

			if (size != observers.size()) {
				size = observers.size();
				i--;
			}
		}
	}
}
