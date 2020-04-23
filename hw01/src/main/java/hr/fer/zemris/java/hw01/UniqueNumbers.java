package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program allows user to create ordered binary tree. User can add integers
 * through interactive interface. After user finishes with adding numbers,
 * program will output final tree structure in ascending and descending order.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class UniqueNumbers {

	/**
	 * Instance of a class <code>TreeNode</code> represents one node in ordered
	 * binary tree.
	 */
	static class TreeNode {
		/**
		 * Left child of this node.
		 */
		TreeNode left;
		/**
		 * Right child of this node.
		 */
		TreeNode right;
		/**
		 * Value stored in this node.
		 */
		int value;
	}

	/**
	 * Entry point of the program. Main control flow and output of the final tree
	 * structure.
	 * 
	 * @param args args arguments from the command line
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		TreeNode root = null;

		while (true) {
			System.out.print("Unesite broj > ");
			String line = scanner.next();

			if (line.equals("kraj")) {
				System.out.print("Ispis od najmanjeg: ");
				printAscendingTree(root);
				System.out.println();
				System.out.print("Ispis od najvećeg: ");
				printDescendingTree(root);
				break;
			}

			try {
				int n = Integer.parseInt(line);
				if (containsValue(root, n)) {
					System.out.println("Broj već postoji. Preskačem.");
				} else {
					root = addNode(root, n);
					System.out.println("Dodano.");
				}
			} catch (NumberFormatException e) {
				System.out.format("'%s' nije cijeli broj.%n", line);
			}
		}
		scanner.close();
	}

	/**
	 * Checks if a given number is contained in the tree.
	 * 
	 * @param root   Root of the tree.
	 * @param number Number to be checked.
	 * @return Returns true if tree contains passed number and vice versa.
	 */
	public static boolean containsValue(TreeNode root, int number) {
		if (root == null) {
			return false;
		}

		if (root.value == number) {
			return true;
		} else if (number < root.value) {
			return containsValue(root.left, number);
		} else {
			return containsValue(root.right, number);
		}
	}

	/**
	 * Gives total number of elements stored in the tree.
	 * 
	 * @param root Root of the tree
	 * @return Number of elements
	 */
	public static int treeSize(TreeNode root) {
		int size = 0;
		if (root == null) {
			return size;
		} else {
			size++;
		}
		size += treeSize(root.left);
		size += treeSize(root.right);

		return size;
	}

	/**
	 * Inserts given integer into passed tree structure. Does not insert duplicated
	 * values.
	 * 
	 * @param root   Root of the tree.
	 * @param number Integer that will be inserted into the tree
	 * @return root to the tree structure
	 */
	public static TreeNode addNode(TreeNode root, int number) {
		if (root == null) {
			root = new TreeNode();
			root.value = number;
		} else {
			if (number < root.value) {
				root.left = addNode(root.left, number);
			} else if (number > root.value) {
				root.right = addNode(root.right, number);
			}
		}
		return root;
	}

	/**
	 * Outputs the tree elements in ascending order.
	 * 
	 * @param root Root of the tree
	 */
	public static void printAscendingTree(TreeNode root) {
		if (root == null) {
			return;
		}

		printAscendingTree(root.left);
		System.out.print(root.value + " ");
		printAscendingTree(root.right);
	}

	/**
	 * Outputs the tree elements in descending order.
	 * 
	 * @param root Root of the tree
	 */
	public static void printDescendingTree(TreeNode root) {
		if (root == null) {
			return;
		}

		printDescendingTree(root.right);
		System.out.print(root.value + " ");
		printDescendingTree(root.left);
	}

}
