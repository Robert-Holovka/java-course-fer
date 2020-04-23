package hr.fer.zemris.java.hw07.observer2;

/**
 * Tracks how many times value has changed since this class subscribed to the
 * IntegerStorage subject.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Counter on how many times value has changed.
	 */
	private int timesChanged;

	@Override
	public void valueChanged(IntegerStorage.IntegerStorageChange istorage) {
		timesChanged++;
		System.out.println("Number of value changes since tracking: " + timesChanged);
	}

}
