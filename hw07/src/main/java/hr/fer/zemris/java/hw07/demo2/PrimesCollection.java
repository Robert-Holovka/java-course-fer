package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Generates prime numbers on demand. From 2 to {@link Integer#MAX_VALUE}
 * 
 * @author Robert Holovka
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Defines how many consecutive prime numbers user wants.
	 */
	private int numOfPrimes;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param numOfPrimes
	 */
	public PrimesCollection(int numOfPrimes) {
		this.numOfPrimes = numOfPrimes;
	}

	/**
	 * Iterator implementation for this class. Returns consecutive prime numbers
	 * starting from 2. Iterating is finished when {@link #numOfPrimes} is
	 * satisfied.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class PrimesCollectionIterator implements Iterator<Integer> {
		/**
		 * Lastly generated prime number.
		 */
		int currentPrime = 1;
		/**
		 * Tells how many more consecutive prime numbers this iterator has to return.
		 */
		int leftToGenerate;

		/**
		 * Constructs instance of this class.
		 * 
		 * @param leftToGenerate how many more consecutive prime numbers this iterator
		 *                       has to return
		 */
		public PrimesCollectionIterator(int leftToGenerate) {
			this.leftToGenerate = leftToGenerate;
		}

		@Override
		public boolean hasNext() {
			return leftToGenerate > 0 && currentPrime != Integer.MAX_VALUE;
		}

		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			for (int i = currentPrime + 1; i < Integer.MAX_VALUE; i++) {
				if (isPrime(i)) {
					leftToGenerate--;
					currentPrime = i;
					return currentPrime;
				}
			}

			// Should not execute but left here just in case
			throw new NoSuchElementException();
		}

		/**
		 * Checks whether given {@code int} is a prime number.
		 * 
		 * @param candidate Integer to be checked
		 * @return True if given int is a prime number, false otherwise
		 */
		private boolean isPrime(int candidate) {
			for (int i = 2; i <= Math.sqrt(candidate); i++) {
				if (candidate % i == 0) {
					return false;
				}
			}
			return true;
		}

	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator(numOfPrimes);
	}

}
