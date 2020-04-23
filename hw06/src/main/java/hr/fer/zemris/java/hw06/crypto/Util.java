package hr.fer.zemris.java.hw06.crypto;

/**
 * Utility class which provides methods for conversion between bytes and
 * hexadecimal systems.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Util {

	/**
	 * Hexadecimal base number.
	 */
	private static final int HEXADECIMAL_BASE = 16;

	/**
	 * Half of the byte.
	 */
	private static final int NIBBLE_SIZE = 4;

	/**
	 * Transforms given string to array of bytes. String must contain characters
	 * that are allowed in hexadecimal system and its length must be divisible by
	 * number 2.
	 * 
	 * @param keyText Text representation of hexadecimal number
	 * @return Array of bytes
	 * @throws IllegalArgumentException if given string is not divisible by 2 or if
	 *                                  it contains characters that are not valid in
	 *                                  hexadecimal system
	 */
	public static byte[] hextobyte(String keyText) {
		if (keyText.length() % 2 != 0) {
			throw new IllegalArgumentException("Key length must be divisible by 2.");
		}

		byte[] bytes = new byte[keyText.length() / 2];
		for (int i = 0; i < keyText.length(); i += 2) {
			int first = extractDigit(keyText.charAt(i));
			int second = extractDigit(keyText.charAt(i + 1));
			bytes[i / 2] = (byte) ((first << NIBBLE_SIZE) + second);
		}

		return bytes;
	}

	/**
	 * Extracts integer value of a hexadecimal character.
	 * 
	 * @param c Hexadecimal character
	 * @return Integer value of {@code c}
	 * @throws IllegalArgumentException if given character is not a valid
	 *                                  hexadecimal
	 */
	private static int extractDigit(Character c) {
		if (Character.digit(c, HEXADECIMAL_BASE) == -1) {
			throw new IllegalArgumentException("Byte does not represents hexadecimal number.");
		}

		return Integer.parseInt(String.valueOf(c), HEXADECIMAL_BASE);
	}

	/**
	 * Transforms given array of bytes to hexadecimal String.
	 * 
	 * @param bytearray Array to be transformed
	 * @return String hexadecimal
	 */
	public static String bytetohex(byte[] bytearray) {
		StringBuilder sb = new StringBuilder();

		for (byte b : bytearray) {
			char firstDigit = Character.forDigit((b >> 4) & 0xF, HEXADECIMAL_BASE);
			char secondDigit = Character.forDigit(b & 0xF, HEXADECIMAL_BASE);
			sb.append(Character.toLowerCase(firstDigit));
			sb.append(Character.toLowerCase(secondDigit));
		}

		return sb.toString();
	}

}
