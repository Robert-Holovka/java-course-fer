package hr.fer.zemris.java.hw07.observer2;

/**
 * Observer which subscribes to IntegerStorage.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface IntegerStorageObserver {
	/**
	 * Action that performs when value of IntegerStorage changes.
	 * 
	 * @param istorage IntegerStorageChange
	 */
	void valueChanged(IntegerStorage.IntegerStorageChange istorage);
}