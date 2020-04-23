package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Provides various methods for handling client requests (e.g. writing response
 * header/body). This context also caches many types of parameters and can store
 * cookies.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RequestContext {

	/**
	 * {@link Charset} which this context uses for writing.
	 */
	private Charset charset;
	/**
	 * Encoding which this context uses for writing.
	 */
	private String encoding;
	/**
	 * Response status code.
	 */
	private int statusCode;
	/**
	 * Response status text.
	 */
	private String statusText;
	/**
	 * Response content type.
	 */
	private String mimeType;
	/**
	 * Response body content length.
	 */
	private Long contentLength;
	/**
	 * Tells whether header is already generated.
	 */
	private boolean headerGenerated;
	/**
	 * Stream on which this context flushes its content.
	 */
	private OutputStream outputStream;
	/**
	 * Unmodifiable map of parameters.
	 */
	private Map<String, String> parameters;
	/**
	 * Map of persistent parameters.
	 */
	private Map<String, String> persistentParameters;
	/**
	 * Map of temporary parameters.
	 */
	private Map<String, String> temporaryParameters;
	/**
	 * List of cookies.
	 */
	private List<RCCookie> outputCookies;
	/**
	 * Delegates script execution.
	 */
	private IDispatcher dispatcher;
	/**
	 * Session ID.
	 */
	private String sessionID;

	/**
	 * Constructs new request context.
	 * 
	 * @param outputStream         Stream on which this context flushes its content
	 * @param parameters           Map of parameters
	 * @param persistentParameters Map of persistent parameters
	 * @param outputCookies        List of RCCookies
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null,null);
	}

	/**
	 * Constructs new request context.
	 * 
	 * @param outputStream         Stream on which this context flushes its content
	 * @param parameters           Map of parameters
	 * @param persistentParameters Map of persistent parameters
	 * @param outputCookies        List of RCCookies
	 * @param temporaryParameters  Map of temporary parameters
	 * @param dispatcher           Delegates script execution
	 * @throws NullPointerException if given {@link OutputStream} is a {@code null}
	 *                              reference
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher, String sessionID) {

		this.outputStream = Objects.requireNonNull(outputStream);
		this.parameters = (parameters == null) ? new HashMap<>() : parameters;
		parameters = Collections.unmodifiableMap(parameters);
		this.persistentParameters = (persistentParameters == null) ? new HashMap<>() : persistentParameters;
		this.outputCookies = (outputCookies == null) ? new LinkedList<>() : outputCookies;
		this.temporaryParameters = (temporaryParameters == null) ? new HashMap<>() : temporaryParameters;
		this.dispatcher = dispatcher;
		this.sessionID = sessionID;

		// Default
		encoding = "UTF-8";
		charset = Charset.forName(encoding);
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
		contentLength = null;
		headerGenerated = false;
	}

	/**
	 * Returns "normal" parameter defined by a given name or {@code null} if
	 * parameter does not exist.
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * @return Set of "normal" parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns persistent parameter defined by a given name or {@code null} if
	 * parameter does not exist.
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * @return Set of persistent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Returns temporary parameter defined by a given name or {@code null} if
	 * parameter does not exist.
	 * 
	 * @param name Parameter name
	 * @return Parameter value
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * @return Set of temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * @return script executor
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @return unique session ID
	 */
	public String getSessionID() {
		return sessionID;
	}

	/**
	 * Sets encoding which this context uses for writing.
	 * 
	 * @param encoding String
	 * @throws IllegalArgumentException if given {@code encoding} is not supported
	 * @throws RuntimeException         if header has already been generated
	 */
	public void setEncoding(String encoding) {
		checkIfHeaderIsGenerated();
		if (!Charset.isSupported(encoding)) {
			throw new IllegalArgumentException("Encoding: " + encoding + " is not supported.");
		}
		this.encoding = encoding;
		this.charset = Charset.forName(encoding);
	}

	/**
	 * Sets response status code.
	 * 
	 * @param statusCode Integer
	 * @throws RuntimeException if header has already been generated
	 */
	public void setStatusCode(int statusCode) {
		checkIfHeaderIsGenerated();
		this.statusCode = statusCode;
	}

	/**
	 * Sets response status text.
	 * 
	 * @param statusText String
	 * @throws RuntimeException if header has already been generated
	 */
	public void setStatusText(String statusText) {
		checkIfHeaderIsGenerated();
		this.statusText = statusText;
	}

	/**
	 * Sets response body content type.
	 * 
	 * @param mimeType content type
	 * @throws RuntimeException if header has already been generated
	 */
	public void setMimeType(String mimeType) {
		checkIfHeaderIsGenerated();
		this.mimeType = mimeType;
	}

	/**
	 * Sets response body content length.
	 * 
	 * @param contentLength Long
	 * @throws RuntimeException if header has already been generated
	 */
	public void setContentLength(Long contentLength) {
		checkIfHeaderIsGenerated();
		this.contentLength = contentLength;
	}

	/**
	 * Stores new persistent parameter defined by a given name and value.
	 * 
	 * @param name  parameter name
	 * @param value parameter value
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Stores new temporary parameter defined by a given name and value.
	 * 
	 * @param name  parameter name
	 * @param value parameter value
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes persistent parameter defined by a given name.
	 * 
	 * @param name parameter name
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Removes temporary parameter defined by a given name.
	 * 
	 * @param name parameter name
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Stores given cookie to this context.
	 * 
	 * @param rcCookie
	 * @throws RuntimeException if header has already been generated
	 */
	public void addRCCookie(RCCookie rcCookie) {
		checkIfHeaderIsGenerated();
		outputCookies.add(rcCookie);
	}

	/**
	 * @throws RuntimeException if header has already been generated
	 */
	private void checkIfHeaderIsGenerated() {
		if (headerGenerated) {
			throw new RuntimeException("Header is already generated, could not set given property.");
		}
	}

	/**
	 * Writes given text to the {@link OutputStream}.
	 * 
	 * @param text Text to be written
	 * @return this context
	 * @throws IOException if {@link OutputStream} is closed
	 */
	public RequestContext write(String text) throws IOException {
		write(text.getBytes(charset));
		return this;
	}

	/**
	 * Writes given bytes to the {@link OutputStream}.
	 * 
	 * @param data Text bytes to be written
	 * @return this context
	 * @throws IOException if {@link OutputStream} is closed
	 */
	public RequestContext write(byte[] data) throws IOException {
		write(data, 0, data.length);
		return this;
	}

	/**
	 * Writes given bytes to the {@link OutputStream}.
	 * 
	 * @param data   Text bytes to be written
	 * @param offset Initial offset
	 * @param len    Length of the text
	 * @return this context
	 * @throws IOException if {@link OutputStream} is closed
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		if (!headerGenerated) {
			writeHeader();
		}
		outputStream.write(data, offset, len);
		return this;
	}

	public void flush() throws IOException {
		outputStream.flush();
	}

	/**
	 * Writes response header to the {@link OutputStream}.
	 * 
	 * @throws IOException if {@link OutputStream} is closed
	 */
	private void writeHeader() throws IOException {
		charset = Charset.forName(encoding);
		String header = generateHeader();
		byte[] headerData = header.getBytes(StandardCharsets.ISO_8859_1);
		outputStream.write(headerData, 0, headerData.length);
		headerGenerated = true;
	}

	/**
	 * @return response header as instance of String
	 */
	private String generateHeader() {
		String newLine = "\r\n";
		StringBuilder sb = new StringBuilder();

		// Status
		sb.append(String.format("HTTP/1.1 %d %s", statusCode, statusText));
		sb.append(newLine);
		// Content-Type
		sb.append(String.format("Content-Type: %s", mimeType));
		if (mimeType.startsWith("text/")) {
			sb.append(String.format("; charset=%s", encoding));
		}
		sb.append(newLine);
		// Content length
		if (contentLength != null) {
			sb.append(String.format("Content-Length: %d", contentLength));
		}
		// Cookies
		for (var cookie : outputCookies) {
			sb.append("Set-Cookie:" + cookie.toString());
			sb.append(newLine);
		}

		// End of header
		sb.append(newLine);
		return sb.toString();
	}

	/**
	 * Represents a cookie that browser can store. This cookie can contain only one
	 * attribute.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	public static class RCCookie {
		/**
		 * Attribute name.
		 */
		private String name;
		/**
		 * Attribute value.
		 */
		private String value;
		/**
		 * Tells browser for which domain this cookie should be used.
		 */
		private String domain;
		/**
		 * Tells browser for which path this cookie should be used.
		 */
		private String path;
		/**
		 * Cookie lifetime.
		 */
		private Integer maxAge;
		
		private boolean HTTPOnly;

		/**
		 * Constructs new instance of this cookie.
		 * 
		 * @param name   Attribute name
		 * @param value  Attribute value
		 * @param maxAge Cookie lifetime
		 * @param domain Tells browser for which domain this cookie should be used
		 * @param path   Tells browser for which path this cookie should be used
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path, boolean HTTPOnly) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
			this.HTTPOnly = HTTPOnly;
		}

		/**
		 * @return Attribute name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @return Attribute value
		 */
		public String getValue() {
			return value;
		}

		/**
		 * @return Domain for which this cookie should be used
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * @return Path for which this cookie should be used
		 */
		public String getPath() {
			return path;
		}

		/**
		 * @return Cookie lifetime
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			if (name != null) {
				sb.append(" " + name + "=\"" + value + "\";");
			}
			if (domain != null) {
				sb.append(String.format(" Domain=%s;", domain));
			}
			if (path != null) {
				sb.append(String.format(" Path=%s;", path));
			}
			if (maxAge != null) {
				sb.append(String.format(" Max-Age=%s;", maxAge));
			}
			if (HTTPOnly) {
				sb.append(" HttpOnly;");
			}

			// Remove last ";"
			sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		}
	}

}
