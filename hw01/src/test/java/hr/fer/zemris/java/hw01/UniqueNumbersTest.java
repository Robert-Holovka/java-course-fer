package hr.fer.zemris.java.hw01;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.*;

public class UniqueNumbersTest {

	@Test
	public void addSingleNode() {
		TreeNode root = null;
		root = addNode(root, 42);
		assertEquals(42, root.value);
	}

	@Test
	public void addUniqueNodes() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 21, 35 };
		root = addNodes(numbers, root);

		int[] actualNumbers = new int[] { root.value, root.right.value, root.left.value, root.left.right.value };
		assertArrayEquals(numbers, actualNumbers);
	}

	@Test
	public void addDuplicatedNodes() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 42, 21, 21, 35, 76 };
		root = addNodes(numbers, root);

		int[] expectedNumbers = new int[] { 42, 76, 21, 35 };
		int[] actualNumbers = new int[] { root.value, root.right.value, root.left.value, root.left.right.value };
		assertArrayEquals(expectedNumbers, actualNumbers);
	}

	@Test
	public void treeSizeWithZeroElements() {
		TreeNode root = null;
		assertEquals(0, treeSize(root));
	}

	@Test
	public void treeSizeWithSingleElement() {
		TreeNode root = null;
		root = addNode(root, 42);

		assertEquals(1, treeSize(root));
	}

	@Test
	public void treeSizeWithUniqueElements() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 21, 99, 20, -5 };
		root = addNodes(numbers, root);

		assertEquals(6, treeSize(root));
	}

	@Test
	public void treeSizeWithDuplicatedElements() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 42, 99, 76, -5 };
		root = addNodes(numbers, root);

		assertEquals(4, treeSize(root));
	}

	@Test
	public void containsValueInEmptyTree() {
		TreeNode root = null;
		assertEquals(false, containsValue(root, 42));
	}

	@Test
	public void containsExistingValue() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 21, 99, 20, -5 };
		root = addNodes(numbers, root);

		assertEquals(true, containsValue(root, 42));
	}

	@Test
	public void containsNonexistingValue() {
		TreeNode root = null;
		int[] numbers = new int[] { 42, 76, 21, 99, 20, -5 };
		root = addNodes(numbers, root);

		assertEquals(false, containsValue(root, 3));
	}

	private TreeNode addNodes(int[] numbers, TreeNode root) {
		for (int number : numbers) {
			root = addNode(root, number);
		}
		return root;
	}

}
