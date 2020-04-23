package hr.fer.zemris.java.hw07.observer1;

/**
 * Doubles the value that IntegerStorage contains.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Tells how long this class is subscribed to IntegerStorage.
	 */
	private int duration;
	/**
	 * Tells how many times value of IntegerStorage class has been changed.
	 */
	private int timesUsed;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param duration Tells how long this class is subscribed to IntegerStorage
	 * @throws IllegalArgumentException if duration is less than 1
	 */
	public DoubleValue(int duration) {
		if (duration < 1) {
			throw new IllegalArgumentException();
		}
		this.duration = duration;
	}

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println("Double value: " + istorage.getValue() * 2);

		timesUsed++;
		if (timesUsed == duration) {
			istorage.removeObserver(this);
		}
	}

}
