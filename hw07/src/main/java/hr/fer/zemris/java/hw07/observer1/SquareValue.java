package hr.fer.zemris.java.hw07.observer1;

/**
 * Squares value that IntegerStorage holds.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
