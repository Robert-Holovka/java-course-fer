package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * List model which generates prime numbers.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PrimListModel implements ListModel<Long> {

	/**
	 * Storage for prime numbers.
	 */
	private List<Long> primeNumbers;
	/**
	 * Components who observes this model.
	 */
	private List<ListDataListener> listeners;

	/**
	 * Default constructor.
	 */
	public PrimListModel() {
		listeners = new LinkedList<>();
		primeNumbers = new ArrayList<>();
		primeNumbers.add((long) 1);
	}

	/**
	 * Generates next prime number and notifies observers.
	 */
	public void next() {
		long nextPrime = nextPrime();
		if (nextPrime == -1) {
			JOptionPane.showMessageDialog(null, "Maximum number value reached!");
		} else {
			primeNumbers.add(nextPrime);

			// Notify all listeners
			for (var listener : listeners) {
				ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
				listener.intervalAdded(event);
			}
		}
	}

	/**
	 * Calculates next prime number and returns it.
	 * 
	 * @return prime number
	 */
	private long nextPrime() {
		long currentPrime = primeNumbers.get(primeNumbers.size() - 1);
		long nextPrime = -1;

		for (long i = currentPrime + 1; i <= Long.MAX_VALUE; i++) {
			if (isPrime(i)) {
				nextPrime = i;
				break;
			}
		}

		return nextPrime;
	}

	/**
	 * Checks whether given {@code long} is a prime number.
	 * 
	 * @param candidate Long to be checked
	 * @return True if given long is a prime number, false otherwise
	 */
	private boolean isPrime(long candidate) {
		for (int i = 2; i <= Math.sqrt(candidate); i++) {
			if (candidate % i == 0) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getSize() {
		return primeNumbers.size();
	}

	@Override
	public Long getElementAt(int index) {
		return primeNumbers.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// Copy on write
		listeners = new LinkedList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		// Copy on write
		listeners = new LinkedList<>(listeners);
		listeners.remove(l);
	}

}
