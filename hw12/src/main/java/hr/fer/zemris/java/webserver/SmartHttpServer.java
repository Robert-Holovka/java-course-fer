package hr.fer.zemris.java.webserver;

import static hr.fer.zemris.java.webserver.common.Utils.extractHeaders;
import static hr.fer.zemris.java.webserver.common.Utils.extractHost;
import static hr.fer.zemris.java.webserver.common.Utils.getFileExtension;
import static hr.fer.zemris.java.webserver.common.Utils.isInteger;
import static hr.fer.zemris.java.webserver.common.Utils.readRequest;
import static hr.fer.zemris.java.webserver.common.Utils.sendError;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Multithreaded HttpServer which can execute custom written scripts.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartHttpServer {

	/**
	 * Default size for a buffer.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024;
	/**
	 * Number of letters in English alphabet.
	 */
	private static final int NUMBER_OF_LETTERS = 26;
	/**
	 * Number of characters that session ID should contain.
	 */
	private static final int SESSION_ID_LENGTH = 20;
	/**
	 * Extension of a smart script.
	 */
	private static final String SCRIPT_EXTENSION = "smscr";
	/**
	 * Package for worker classes.
	 */
	private static final String WORKERS_PACKAGE = "hr.fer.zemris.java.webserver.workers";

	/**
	 * IP address of this server.
	 */
	private String address;
	/**
	 * Domain name of this server.
	 */
	private String domainName;
	/**
	 * Port on which this server listens.
	 */
	private int port;
	/**
	 * Number of working threads this server uses.
	 */
	private int workerThreads;
	/**
	 * Number of seconds until session expires.
	 */
	private int sessionTimeout;
	/**
	 * Path to the document root.
	 */
	private Path documentRoot;
	/**
	 * Flag which indicates whether server is running.
	 */
	private boolean serverRunning = false;

	/**
	 * Map of mime types that this server supports.
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();
	/**
	 * Reference to the thread that accepts client requests.
	 */
	private ServerThread serverThread;
	/**
	 * Thread pool.
	 */
	private ExecutorService threadPool;
	/**
	 * Map of workers.
	 */
	private Map<String, IWebWorker> workersMap;
	/**
	 * Map of alive sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SmartHttpServer.SessionMapEntry>();
	/**
	 * Random number generator.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Creates instance of this HTTP Server.
	 * 
	 * @param configFileName Server configuration file
	 * @throws NullPointerException if given configuration file is a {@code null}
	 *                              reference
	 */
	public SmartHttpServer(String configFileName) {
		Objects.requireNonNull(configFileName);
		loadProperties(configFileName);
	}

	/**
	 * Loads all necessary properties.
	 * 
	 * @param configFile Server configuration file
	 */
	private void loadProperties(String configFile) {
		try {
			Properties props = new Properties();
			props.load(Files.newInputStream(Paths.get(configFile)));
			loadServerProperties(props);
			loadMimeTypes(props);
			loadWorkers(props);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Loads server properties.
	 * 
	 * @param props Server properties
	 * @throws IOException          If reading configuration file was not successful
	 * @throws NullPointerException if any necessary parameter is not defined
	 */
	private void loadServerProperties(Properties props) {
		this.address = Objects.requireNonNull(props.getProperty("server.address"));
		this.domainName = Objects.requireNonNull(props.getProperty("server.domainName"));

		String port = Objects.requireNonNull(props.getProperty("server.port"));
		if (!isInteger(port)) {
			throw new IllegalArgumentException("Port number must be an integer, provided. " + port);
		}
		String workerThreads = Objects.requireNonNull(props.getProperty("server.workerThreads"));
		if (!isInteger(workerThreads)) {
			throw new IllegalArgumentException(
					"Number of worker threads must be an integer, provided. " + workerThreads);
		}
		String sessionTimeout = Objects.requireNonNull(props.getProperty("session.timeout"));
		if (!isInteger(sessionTimeout)) {
			throw new IllegalArgumentException("Session timeout must be an integer, provided. " + sessionTimeout);
		}

		this.port = Integer.parseInt(port);
		this.workerThreads = Integer.parseInt(workerThreads);
		this.sessionTimeout = Integer.parseInt(sessionTimeout);

		String documentRoot = Objects.requireNonNull(props.getProperty("server.documentRoot"));
		Path documentRootPath = Paths.get(documentRoot).toAbsolutePath().normalize();
		if (!Files.exists(documentRootPath) || !Files.isDirectory(documentRootPath)) {
			throw new IllegalArgumentException("Provided document root path: '" + documentRoot + "' is not valid.");
		}
		this.documentRoot = documentRootPath;
	}

	/**
	 * Loads mime types that this server supports.
	 * 
	 * @param props Server properties
	 * @throws IOException          If reading mime properties file failed
	 * @throws NullPointerException if mime configuration file does not exists
	 */
	private void loadMimeTypes(Properties props) throws IOException {
		String mimeConfig = Objects.requireNonNull(props.getProperty("server.mimeConfig"));
		Properties mime = new Properties();
		mime.load(Files.newInputStream(Paths.get(mimeConfig)));

		for (Object key : mime.keySet()) {
			String keyString = (String) key;
			mimeTypes.put(keyString, mime.getProperty(keyString));
		}
	}

	/**
	 * Loads worker classes from configuration file.
	 * 
	 * @param props Worker properties file
	 * @throws IOException              If reading worker properties file failed
	 * @throws IllegalAccessException   If access to the worker class was denied
	 * @throws InstantiationException   If worker class instantiation failed
	 * @throws ClassNotFoundException   If defined worker class does not exist
	 * @throws NullPointerException     If worker properties file does not exist
	 * @throws IllegalArgumentException If properties file contains duplicated
	 *                                  workers
	 */
	@SuppressWarnings("deprecation")
	private void loadWorkers(Properties props)
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String workersConfig = Objects.requireNonNull(props.getProperty("server.workers"));
		Properties workers = new Properties();
		workersMap = new HashMap<>();
		workers.load(Files.newInputStream(Paths.get(workersConfig)));

		for (Object path : workers.keySet()) {
			String pathString = (String) path;
			if (workersMap.containsKey(pathString)) {
				throw new IllegalArgumentException("Path: '" + pathString + "' already has defined worker.");
			}
			String fqcn = workers.getProperty(pathString);
			Class<?> referenceToClass = null;
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);

			Object newObject = null;
			newObject = referenceToClass.newInstance();

			IWebWorker iww = (IWebWorker) newObject;
			workersMap.put(pathString, iww);
		}
	}

	/**
	 * Starts server if already not running.
	 */
	protected synchronized void start() {
		if (serverRunning) {
			return;
		}
		threadPool = Executors.newFixedThreadPool(workerThreads);
		serverThread = new ServerThread();
		serverThread.start();
	}

	/**
	 * Stops server if it is running.
	 */
	protected synchronized void stop() {
		if (!serverRunning) {
			return;
		}
		serverRunning = false;
		threadPool.shutdown();
	}

	/**
	 * Main server thread. Listens for clients connections and creates new worker
	 * for each new request.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	protected class ServerThread extends Thread {
		@Override
		public void run() {
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(address, port));
			} catch (IOException e) {
				System.out.println("Could not establish server on a given port and adresss.");
				return;
			}

			Thread sessionCleaner = new Thread(() -> {
				while (true) {
					try {
						Thread.sleep(1000 * 60 * 5);
					} catch (InterruptedException e) {
					}
					System.out.println("tu sam");
					final long currentTime = System.currentTimeMillis() / 1000;
					for (var session : sessions.keySet()) {
						if (currentTime >= sessions.get(session).validUntil) {
							System.out.println("izbrisao");
							sessions.remove(session);
						}
					}
				}
			});
			sessionCleaner.setDaemon(true);
			sessionCleaner.start();

			serverRunning = true;
			while (serverRunning) {
				Socket client;
				try {
					client = serverSocket.accept();
					ClientWorker clientWorker = new ClientWorker(client);
					threadPool.submit(clientWorker);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			try {
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Represents class responsible for serving one client(one request).
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/**
		 * Client socket.
		 */
		private Socket csocket;
		/**
		 * Input stream for reading request.
		 */
		private PushbackInputStream istream;
		/**
		 * Output stream for writing response.
		 */
		private OutputStream ostream;
		/**
		 * Request HTTP version.
		 */
		private String version;
		/**
		 * Request method.
		 */
		private String method;
		/**
		 * This server IP adress or domain name.
		 */
		private String host;
		/**
		 * Map of "normal" parameters defined by their name.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		/**
		 * Map of temporary parameters defined by their name.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		/**
		 * Map of permanent parameters defined by their name.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
		/**
		 * List of cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		/**
		 * Session ID.
		 */
		private String SID;
		/**
		 * This client request context.
		 */
		private RequestContext context;

		/**
		 * Constructs new instance of this class.
		 * 
		 * @param csocket Socket connection to the client
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				// Header in byte form
				byte[] requestBytes = readRequest(istream);
				if (requestBytes == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				// Extract header lines + validate them
				List<String> headers = extractHeaders(new String(requestBytes, StandardCharsets.US_ASCII));
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				validateRequest(firstLine);

				// Resolve host
				host = extractHost(headers);
				host = (host == null) ? domainName : host;

				checkSession(headers);

				// Resolve query parameters
				String data[] = firstLine[1].split("\\?");
				String path = data[0];
				String paramString = (data.length == 2) ? data[1] : null;
				parseParameters(paramString);

				internalDispatchRequest(path, true);
			} catch (Exception e) {
				System.out.println("Error happened while serving: " + csocket.getInetAddress().getHostAddress());
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		/**
		 * Determines what server should do based on a request path.
		 * 
		 * @param urlPath    Request URL path
		 * @param directCall
		 * @throws Exception
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall)
				throws Exception {
			Path requestedFile = documentRoot.resolve(urlPath.substring(1));

			// Sanity check for directory traversal attack
			if (!requestedFile.normalize().startsWith(documentRoot)) {
				sendError(ostream, 403, "Forbidden");
				return;
			}

			if (urlPath.startsWith("/private") && directCall == true) {
				sendError(ostream, 403, "Forbidden.");
				return;
			}

			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this, SID);
			}

			// Convention-over-configuration approach
			if (urlPath.startsWith("/ext/")) {
				String className = urlPath.replaceFirst("/ext/", "");
				String fqcn = WORKERS_PACKAGE + "." + className;
				try {
					executeClass(fqcn);
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
					sendError(ostream, 404, "Resource not found");
				}

				return;
			}

			// Configuration based approach
			IWebWorker worker = workersMap.get(urlPath);
			if (worker != null) {
				worker.processRequest(context);
				return;
			}

			if (!Files.exists(requestedFile) || !Files.isReadable(requestedFile)) {
				sendError(ostream, 404, "Resource not found.");
				return;
			}
			String extension = getFileExtension(requestedFile);
			if (extension.equals(SCRIPT_EXTENSION)) {
				executeScript(requestedFile);
			} else {
				serveFile(requestedFile);
			}
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Executes script defined by a given path and writes result to the context
		 * {@link OutputStream}.
		 * 
		 * @param requestedFile Script location.
		 * @throws IOException If script does not exist or {@link OutputStream} is
		 *                     closed
		 */
		private void executeScript(Path script) throws IOException {
			String docBody = new String(Files.readAllBytes(script), StandardCharsets.UTF_8);
			SmartScriptParser parser = new SmartScriptParser(docBody);
			DocumentNode document = parser.getDocumentNode();

			SmartScriptEngine engine = new SmartScriptEngine(document, context);
			engine.execute();
			// Just in case
			context.flush();
		}

		/**
		 * Loads class from a given FQCN and directly executes it.
		 * 
		 * @param fqcn Fully qualified class name
		 * @throws ClassNotFoundException If class does not exist
		 * @throws IllegalAccessException If class is forbidden to access
		 * @throws InstantiationException If class could not be instantiated
		 */
		@SuppressWarnings("deprecation")
		private void executeClass(String fqcn)
				throws ClassNotFoundException, InstantiationException, IllegalAccessException {
			Class<?> referenceToClass = null;
			referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
			Object newObject = null;

			newObject = referenceToClass.newInstance();
			IWebWorker iww = (IWebWorker) newObject;

			try {
				iww.processRequest(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * Writes requested file content to the client {@link OutputStream}.
		 * 
		 * @param requestedFile Requested file path
		 * @throws IOException If requested file does not exist or {@link OutputStream}
		 *                     is closed
		 */
		private void serveFile(Path requestedFile) throws IOException {
			String extension = getFileExtension(requestedFile);
			String mimeType = extractMimeType(extension);

			context.setMimeType(mimeType);
			context.setStatusCode(200);

			try (InputStream is = new BufferedInputStream(Files.newInputStream(requestedFile))) {
				byte[] buf = new byte[DEFAULT_BUFFER_SIZE];
				while (true) {
					int r = is.read(buf);
					if (r < 1)
						break;
					context.write(buf, 0, r);
				}
				context.flush();
			}
		}

		/**
		 * Checks if request is valid.
		 * 
		 * @param firstLine Request data splitted by whitespace
		 * @throws IOException If writing error response to {@link OutputStream} failed
		 * @throw {@link InvalidRequestException} if given request is not valid
		 */
		private void validateRequest(String[] firstLine) throws IOException {
			if (firstLine == null || firstLine.length != 3) {
				String message = "Bad request.";
				sendError(ostream, 400, message);
				throw new InvalidRequestException(message);
			}

			method = firstLine[0].toUpperCase();
			if (!method.equals("GET")) {
				String message = "Method Not Allowed";
				sendError(ostream, 400, message);
				throw new InvalidRequestException(message);
			}

			version = firstLine[2].toUpperCase();
			if (!version.equals("HTTP/1.1") && !version.equals("HTTP/1.0")) {
				String message = "HTTP Version Not Supported";
				sendError(ostream, 400, message);
				throw new InvalidRequestException(message);
			}
		}

		/**
		 * Maps mime type based on a given file extension.
		 * 
		 * @param extension File extension
		 * @return mime type
		 */
		private String extractMimeType(String extension) {
			if (extension == null) {
				return "application/octet-stream";
			} else {
				return mimeTypes.get(extension);
			}
		}

		/**
		 * Extracts parameters from a query string and loads them into {@link #params}
		 * map.
		 * 
		 * @param paramString String which contains query parameters
		 */
		private void parseParameters(String paramString) {
			if (paramString == null) {
				return;
			}
			String[] parameters = paramString.split("&");
			for (String parameter : parameters) {
				String[] p = parameter.split("=");
				if (p.length != 2) {
					continue;
				}
				params.put(p[0], p[1]);
			}
		}

		private synchronized void checkSession(List<String> headers) {
			String cookieTag = "Cookie:";
			String sidTag = "sid";
			String sidCandidate = null;

			for (String header : headers) {
				if (header.startsWith(cookieTag) && header.contains(sidTag)) {
					Pattern pattern = Pattern.compile(cookieTag + ".*" + sidTag + "=\"([a-zA-Z]{20})\".*");
					Matcher matcher = pattern.matcher(header);
					if (matcher.matches()) {
						sidCandidate = matcher.group(1);
					}
				}
			}

			// Cookie with SID does not exist
			if (sidCandidate == null) {
				createNewCookie();
				return;
			}

			// Session with found SID does not exist or hosts are different
			SessionMapEntry sessionCandidate = sessions.get(sidCandidate);
			if (sessionCandidate == null || !host.equals(sessionCandidate.host)) {
				createNewCookie();
				return;
			}

			// Session expired
			long timeNow = System.currentTimeMillis() / 1000;
			if (timeNow >= sessionCandidate.validUntil) {
				sessions.remove(sidCandidate);
				createNewCookie();
				return;
			}

			// Update session
			sessionCandidate.validUntil = timeNow + sessionTimeout;
			this.SID = sessionCandidate.SID;
			permParams = sessionCandidate.map;
		}

		private void createNewCookie() {
			this.SID = generateSID();
			long validUntil = System.currentTimeMillis() / 1000 + sessionTimeout;
			permParams = new ConcurrentHashMap<String, String>();
			SessionMapEntry session = new SessionMapEntry(SID, host, validUntil,
					permParams);
			sessions.put(SID, session);
			outputCookies.add(new RCCookie("sid", this.SID, null, host, "/", true));
		}

	}

	private static class SessionMapEntry {
		String SID;
		String host;
		long validUntil;
		Map<String, String> map;

		public SessionMapEntry(String SID, String host, long validUntil, Map<String, String> map) {
			this.SID = SID;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	private String generateSID() {
		String sid = "";
		for (int i = 0; i < SESSION_ID_LENGTH; i++) {
			int charVal = sessionRandom.nextInt(NUMBER_OF_LETTERS) + (int) 'A';
			sid += (char) charVal;
		}
		return sid;
	}

	/**
	 * Entry point of the server. Expects a single argument: path to the
	 * configuration file.
	 * 
	 * @param args Arguments from the command line.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected server file properties file.");
			return;
		}
		SmartHttpServer server = new SmartHttpServer(args[0]);
		server.start();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> server.stop()));
	}
}
