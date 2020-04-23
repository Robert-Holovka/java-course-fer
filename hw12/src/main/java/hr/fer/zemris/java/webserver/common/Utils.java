package hr.fer.zemris.java.webserver.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides utility methods.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Utils {

	/**
	 * Integer value of a horizontal tab character.
	 */
	private static final int TAB = 9;
	/**
	 * Integer value of a space character.
	 */
	private static final int SPACE = 32;
	/**
	 * Integer value of a new line character.
	 */
	private static final int LINE_FEED = 10;
	/**
	 * Integer value of a carriage return.
	 */
	private static final int CARRIAGE_RETURN = 13;

	/**
	 * Checks whether given String is Integer.
	 * 
	 * @param s String
	 * @return True if given String is Integer, false otherwise
	 */
	public static boolean isInteger(String s) {
		return s.matches("[1-9][0-9]*");
	}

	/**
	 * Extracts host parameter from a HTTP header.
	 * 
	 * @param headers HTTP header lines
	 * @return host parameter
	 */
	public static String extractHost(List<String> headers) {
		String host = null;
		for (String line : headers) {
			String hostTag = "Host:";
			if (line.startsWith(hostTag)) {
				line = line.replaceFirst(hostTag, "");
				if (line.contains(":")) {
					host = line.split(":")[0].trim();
				} else {
					host = line.trim();
				}
				break;
			}
		}
		return host;
	}

	/**
	 * Returns file extension from a given path.
	 * 
	 * @param file File location on the disk
	 * @return File extension
	 */
	public static String getFileExtension(Path file) {
		String[] nameSplitted = file.getFileName().toString().split("\\.");
		if (nameSplitted.length < 2) {
			return null;
		}
		return nameSplitted[nameSplitted.length - 1].trim();
	}

	/**
	 * Extracts HTTP header lines from a single String.
	 * 
	 * @param requestHeader HTTP header in a single line
	 * @return List of header lines
	 */
	public static List<String> extractHeaders(String requestHeader) {
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		for (String s : requestHeader.split("\n")) {
			// End of header
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == TAB || c == SPACE) {
				currentLine += s;
			} else {
				if (currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if (!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * Simple automata for reading HTTP header.
	 * 
	 * @param is {@link InputStream}
	 * @return HTTP Header as byte array
	 * @throws IOException If given {@link InputStream} is closed
	 */
	public static byte[] readRequest(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		int state = 0;
		l: while (true) {
			int b = is.read();
			if (b == -1)
				return null;
			if (b != CARRIAGE_RETURN) {
				bos.write(b);
			}
			switch (state) {
			case 0:
				if (b == CARRIAGE_RETURN) {
					state = 1;
				} else if (b == LINE_FEED)
					state = 4;
				break;
			case 1:
				if (b == LINE_FEED) {
					state = 2;
				} else
					state = 0;
				break;
			case 2:
				if (b == CARRIAGE_RETURN) {
					state = 3;
				} else
					state = 0;
				break;
			case 3:
				if (b == LINE_FEED) {
					break l;
				} else
					state = 0;
				break;
			case 4:
				if (b == LINE_FEED) {
					break l;
				} else
					state = 0;
				break;
			}
		}
		return bos.toByteArray();
	}

	/**
	 * Helper method for sending error responses back to the client. Also signals to
	 * the client to close connection.
	 * 
	 * @param cos        {@link OutputStream} stream where this server will send
	 *                   response
	 * @param statusCode Response status code
	 * @param statusText Response status text
	 * @throws IOException If given {@link OutputStream} is closed
	 */
	public static void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {
		cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" +
				"Server: simple java server\r\n" +
				"Content-Type: text/plain; charset=UTF-8\r\n" +
				"Content-Length: 0\r\n" +
				"Connection: close\r\n" +
				"\r\n").getBytes(StandardCharsets.US_ASCII));
		cos.flush();
	}

}
