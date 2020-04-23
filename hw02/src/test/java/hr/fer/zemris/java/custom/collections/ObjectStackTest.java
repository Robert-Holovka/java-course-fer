package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ObjectStackTest {

	@Test
	public void createEmptyStack() {
		try {
			ObjectStack stack = new ObjectStack();
			assertTrue(true);
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void pushNullValue() {
		try {
			ObjectStack stack = new ObjectStack();
			stack.push(null);
			fail();
		} catch (NullPointerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void pushOneValue() {
		ObjectStack stack = new ObjectStack();
		stack.push("1");
		assertEquals(1, stack.size());
	}

	@Test
	public void pushMultipleValues() {
		ObjectStack stack = new ObjectStack();
		addElements(stack, 20);
		assertEquals(20, stack.size());
	}

	@Test
	public void sizeOfEmptyStack() {
		ObjectStack stack = new ObjectStack();
		assertEquals(0, stack.size());
	}

	@Test
	public void sizeWithMultipleElements() {
		ObjectStack stack = new ObjectStack();
		stack = addElements(stack, 20);
		assertEquals(20, stack.size());
	}

	@Test
	public void isEmptyWithZeroElements() {
		ObjectStack stack = new ObjectStack();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void isEmptyOneElement() {
		ObjectStack stack = new ObjectStack();
		stack.push("1");
		assertFalse(stack.isEmpty());
	}

	@Test
	public void isEmptyMultipleElements() {
		ObjectStack stack = new ObjectStack();
		stack = addElements(stack, 3);
		assertFalse(stack.isEmpty());
	}

	@Test
	public void clearEmptyStack() {
		ObjectStack stack = new ObjectStack();
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void clearStack() {
		ObjectStack stack = new ObjectStack();
		stack = addElements(stack, 20);
		stack.clear();
		assertTrue(stack.isEmpty());
	}

	@Test
	public void peekEmptyStack() {
		try {
			ObjectStack stack = new ObjectStack();
			stack.peek();
			fail();
		} catch (EmptyStackException e) {
			assertTrue(true);
		}
	}

	@Test
	public void peekFromStackWithOneElement() {
		ObjectStack stack = new ObjectStack();
		stack.push("1");
		assertEquals("1", stack.peek());
	}

	@Test
	public void peekFromStackWithMultipleElements() {
		ObjectStack stack = new ObjectStack();
		stack = addElements(stack, 20);
		assertEquals("19", stack.peek());
	}

	@Test
	public void popEmptyStack() {
		try {
			ObjectStack stack = new ObjectStack();
			stack.pop();
			fail();
		} catch (EmptyStackException e) {
			assertTrue(true);
		}
	}

	@Test
	public void popFromStackWithOneElement() {
		ObjectStack stack = new ObjectStack();
		stack.push("1");
		assertEquals("1", stack.pop());
		assertTrue(stack.isEmpty());
	}

	@Test
	public void popFromStackWithMultipleElements() {
		ObjectStack stack = new ObjectStack();
		stack = addElements(stack, 20);
		assertEquals("19", stack.pop());
	}

	public ObjectStack addElements(ObjectStack stack, int n) {
		for (int i = 0; i < n; i++) {
			stack.push(String.valueOf(i));
		}
		return stack;
	}

}
