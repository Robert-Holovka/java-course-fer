package hr.fer.zemris.java.hw06.crypto;

import static hr.fer.zemris.java.hw06.crypto.Util.bytetohex;
import static hr.fer.zemris.java.hw06.crypto.Util.hextobyte;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void hextobyteOddKey() {
		assertThrows(IllegalArgumentException.class, () -> hextobyte("123"));
	}

	@Test
	public void hextobyteIllegalCharacters() {
		assertThrows(IllegalArgumentException.class, () -> hextobyte("gh"));
	}

	@Test
	public void hextobyteNumberZero() {
		var res = hextobyte("00");
		assertTrue(res[0] == 0);
	}

	@Test
	public void hextobyteKeyLengthZero() {
		var res = hextobyte("");
		assertTrue(res.length == 0);
	}

	@Test
	public void hextobyteComplexExample() {
		assertArrayEquals(new byte[] { 1, -82, 34 }, hextobyte("01aE22"));
	}

	@Test
	public void bytetohexNumberZero() {
		assertEquals("00", bytetohex(new byte[] { 0 }));
	}
	
	@Test
	public void bytetohexEmptyArray() {
		assertEquals("", bytetohex(new byte[] { }));
	}

	@Test
	public void bytetohexComplexDemo() {
		assertEquals("01ae22", bytetohex(new byte[] { 1, -82, 34 }));
	}
}
