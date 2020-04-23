package searching.slagalica;

import java.util.Arrays;

/**
 * Represents single configuration of puzzle (order of elements).
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class KonfiguracijaSlagalice {

	/**
	 * Size of a row in this puzzle.
	 */
	public static final int ROW_SIZE = 3;
	/**
	 * Size of this puzzle.
	 */
	public static final int PUZZLE_SIZE = 9;

	/**
	 * Elements of this puzzle.
	 */
	private int[] polje;

	/**
	 * Constructs new instance of this class defined by a given order of elements.
	 * 
	 * @param polje
	 * @throws IllegalArgumentException if given order contains invalid number of
	 *                                  elements.
	 */
	public KonfiguracijaSlagalice(int[] polje) {
		if (polje.length != PUZZLE_SIZE) {
			throw new IllegalArgumentException();
		}
		this.polje = polje;
	}

	/**
	 * Returns an array that represents an order of this puzzle.
	 * 
	 * @return int[]
	 */
	public int[] getPolje() {
		return Arrays.copyOf(polje, polje.length);
	}

	/**
	 * Returns index of whitespace block in this puzzle.
	 * 
	 * @return int index
	 */
	public int indexOfSpace() {
		int length = polje.length;
		for (int i = 0; i < length; i++) {
			if (polje[i] == 0) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int arraySize = polje.length;

		for (int i = 0; i < arraySize; i++) {
			char c = (char) ((polje[i] == 0) ? '*' : polje[i] + '0');
			sb.append(c);
			sb.append(" ");

			if (((i % ROW_SIZE) == 2) && (i != (arraySize - 1))) {
				sb.append("\r\n");
			}
		}

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(polje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KonfiguracijaSlagalice))
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		return Arrays.equals(polje, other.polje);
	}

}
