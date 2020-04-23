package searching.demo;

import java.util.Arrays;
import java.util.LinkedList;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Graphic demonstration of a puzzle solver.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SlagalicaMain {

	/**
	 * Size of this puzzle.
	 */
	public static final int PUZZLE_SIZE = 9;

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Wrong number of arguments.");
			return;
		}

		if (args[0].length() != PUZZLE_SIZE) {
			System.out.println("Puzzle must have 9 pieces!");
			return;
		}

		int[] puzzle;
		try {
			puzzle = extractPuzzle(args[0]);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return;
		}

		Slagalica slagalica = new Slagalica(
				new KonfiguracijaSlagalice(puzzle));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			SlagalicaViewer.display(rješenje);
		}
	}

	/**
	 * Extracts puzzle elements from the given text.
	 * 
	 * @param string Puzzle elements
	 * @return An array of Integers
	 */
	private static int[] extractPuzzle(String string) {
		char[] data = string.toCharArray();
		LinkedList<Character> puzzleDigits = new LinkedList<Character>(
				Arrays.asList(new Character[] { '0', '1', '2', '3', '4', '5', '6', '7', '8' }));
		int[] puzzle = new int[PUZZLE_SIZE];

		for (int i = 0; i < PUZZLE_SIZE; i++) {
			if (!Character.isDigit(data[i])) {
				throw new IllegalArgumentException("Puzzle must contain only digits, from 0 to 9!");
			}

			puzzleDigits.remove(puzzleDigits.indexOf(data[i]));
			puzzle[i] = data[i] - '0';
		}

		if (puzzleDigits.size() > 0) {
			throw new IllegalArgumentException("Puzzle must contain digits from 0 to 9, each digit occurs only once.");
		}

		return puzzle;
	}
}