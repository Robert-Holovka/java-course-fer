package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ObjectStackTest {

	@Test
	public void createEmptyStack() {
		try {
			@SuppressWarnings("unused")
			ObjectStack<Integer> stack = new ObjectStack<>();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void pushNullValue() {
		try {
			ObjectStack<String> stack = new ObjectStack<>();
			stack.push(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void pushOneValue() {
		ObjectStack<String> stack = new ObjectStack<>();
		stack.push("1");
		assertEquals(1, stack.size());
	}

	@Test
	public void pushMultipleValues() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 20);
		assertEquals(20, stack.size());
	}

	@Test
	public void sizeOfEmptyStack() {
		ObjectStack<String> stack = new ObjectStack<>();
		assertEquals(0, stack.size());
	}

	@Test
	public void sizeWithMultipleElements() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 20);
		assertEquals(20, stack.size());
	}

	@Test
	public void isEmptyWithZeroElements() {
		ObjectStack<Integer> stack = new ObjectStack<>();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void isEmptyOneElement() {
		ObjectStack<String> stack = new ObjectStack<>();
		stack.push("1");
		assertFalse(stack.isEmpty());
	}

	@Test
	public void isEmptyMultipleElements() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 3);
		assertFalse(stack.isEmpty());
	}

	@Test
	public void clearEmptyStack() {
		ObjectStack<String> stack = new ObjectStack<>();
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void clearStack() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 20);
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void peekEmptyStack() {
		try {
			ObjectStack<String> stack = new ObjectStack<>();
			stack.peek();
			fail();
		} catch (EmptyStackException e) {
			assertTrue(true);
		}
	}

	@Test
	public void peekFromStackWithOneElement() {
		ObjectStack<String> stack = new ObjectStack<>();
		stack.push("1");
		assertEquals("1", stack.peek());
	}

	@Test
	public void peekFromStackWithMultipleElements() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 20);
		assertEquals("19", stack.peek());
	}

	@Test
	public void popEmptyStack() {
		try {
			ObjectStack<String> stack = new ObjectStack<>();
			stack.pop();
			fail();
		} catch (EmptyStackException e) {
			assertTrue(true);
		}
	}

	@Test
	public void popFromStackWithOneElement() {
		ObjectStack<String> stack = new ObjectStack<>();
		stack.push("1");
		assertEquals("1", stack.pop());
		assertTrue(stack.isEmpty());
	}

	@Test
	public void popFromStackWithMultipleElements() {
		ObjectStack<String> stack = new ObjectStack<>();
		addElements(stack, 20);
		assertEquals("19", stack.pop());
	}

	public void addElements(ObjectStack<String> stack, int n) {
		for (int i = 0; i < n; i++) {
			stack.push(String.valueOf(i));
		}
	}

}
