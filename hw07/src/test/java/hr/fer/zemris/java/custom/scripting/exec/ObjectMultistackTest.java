package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.EmptyStackException;

import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {

	@Test
	public void constructMultiStack() {
		try {
			@SuppressWarnings("unused")
			var stack = new ObjectMultistack();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void isStackEmptyTrue() {
		var stack = new ObjectMultistack();
		stack.push("drugiKey", new ValueWrapper(3));
		assertTrue(stack.isEmpty("nesto"));
	}

	@Test
	public void isStackEmptyFalse() {
		var stack = new ObjectMultistack();
		stack.push("drugiKey", new ValueWrapper(3));
		assertFalse(stack.isEmpty("drugiKey"));
	}

	@Test
	public void pushOnStack() {
		var stack = new ObjectMultistack();
		stack.push("new", new ValueWrapper("aaa"));
		assertFalse(stack.isEmpty("new"));
	}

	@Test
	public void popFromStack() {
		var stack = new ObjectMultistack();
		stack.push("new", new ValueWrapper("aaa"));
		assertEquals("aaa", stack.pop("new").getValue());
		assertTrue(stack.isEmpty("new"));
	}

	@Test
	public void popFromEmptyStack() {
		var stack = new ObjectMultistack();
		stack.push("new", new ValueWrapper("aaa"));
		stack.pop("new");
		assertThrows(EmptyStackException.class, () -> stack.pop("new"));
	}

	@Test
	public void peekStack() {
		var stack = new ObjectMultistack();
		stack.push("new", new ValueWrapper("aaa"));
		assertEquals("aaa", stack.peek("new").getValue());
		assertFalse(stack.isEmpty("new"));
	}
	
	@Test
	public void peekFromEmptyStack() {
		var stack = new ObjectMultistack();
		stack.push("new", new ValueWrapper("aaa"));
		stack.pop("new");
		assertThrows(EmptyStackException.class, () -> stack.peek("new"));
	}

	@Test
	public void complexDemo() {
		var stack = new ObjectMultistack();
		stack.push("nums", new ValueWrapper("1"));
		stack.push("nums", new ValueWrapper("2"));
		stack.push("nums", new ValueWrapper("3"));

		stack.push("letters", new ValueWrapper("a"));
		stack.push("letters", new ValueWrapper("b"));
		stack.push("letters", new ValueWrapper("c"));

		assertTrue(stack.isEmpty("SomethingElse"));

		assertEquals("3", stack.peek("nums").getValue());
		assertEquals("c", stack.peek("letters").getValue());

		assertEquals("3", stack.pop("nums").getValue());
		assertEquals("c", stack.pop("letters").getValue());
		assertEquals("2", stack.pop("nums").getValue());
		assertEquals("b", stack.pop("letters").getValue());
		assertEquals("1", stack.pop("nums").getValue());
		assertEquals("a", stack.pop("letters").getValue());

		assertTrue(stack.isEmpty("nums"));
		assertTrue(stack.isEmpty("letters"));

	}

}
