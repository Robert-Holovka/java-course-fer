package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class EmptyStackExceptionTest {

	@Test
	public void createDefault() {
		try {
			EmptyStackException e = new EmptyStackException();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void createWithMessage() {
		EmptyStackException e = new EmptyStackException("test");
		assertEquals("test", e.getMessage());
	}

	@Test
	public void createWithCause() {
		EmptyStackException e = new EmptyStackException(new Exception());
		if (e.getCause() instanceof Exception) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
	}

	@Test
	public void createWithCauseAndMessage() {
		EmptyStackException e = new EmptyStackException("test", new Exception());
		if (e.getCause() instanceof Exception) {
			assertTrue(true);
		} else {
			assertTrue(false);
		}
		assertEquals("test", e.getMessage());
	}
}
