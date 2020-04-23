package hr.fer.zemris.java.hw07.observer2;

/**
 * Squares value that IntegerStorage holds.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage.IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
