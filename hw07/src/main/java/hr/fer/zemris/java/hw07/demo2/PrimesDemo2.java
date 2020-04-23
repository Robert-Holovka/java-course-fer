package hr.fer.zemris.java.hw07.demo2;

/**
 * Demonstrates usage of PrimesCollection.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PrimesDemo2 {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {

//		Got prime pair: 2, 2
//		Got prime pair: 2, 3
//		Got prime pair: 3, 2
//		Got prime pair: 3, 3

		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
