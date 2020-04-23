package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer1.ChangeCounter;
import hr.fer.zemris.java.hw07.observer1.DoubleValue;
import hr.fer.zemris.java.hw07.observer1.IntegerStorage;
import hr.fer.zemris.java.hw07.observer1.IntegerStorageObserver;
import hr.fer.zemris.java.hw07.observer1.SquareValue;

/**
 * Demonstration of a {@code observer} pattern. Subject is represented by a
 * class IntegerStorage. Observer is defined by an {@code interface}
 * IntegerStorageObserver.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ObserverExample {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);
		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);
		istorage.removeObserver(observer);
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);
	}
}
