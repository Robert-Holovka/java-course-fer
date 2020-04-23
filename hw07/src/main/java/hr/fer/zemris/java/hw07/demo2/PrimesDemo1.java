package hr.fer.zemris.java.hw07.demo2;

/**
 * Demonstrates usage of PrimesCollection.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PrimesDemo1 {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {

//		Got prime: 2
//		Got prime: 3
//		Got prime: 5
//		Got prime: 7
//		Got prime: 11

		PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
