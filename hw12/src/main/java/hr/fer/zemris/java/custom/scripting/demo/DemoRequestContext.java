package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Generates 3 files whose purpose is to test validity of {@link RequestContext}
 * class.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DemoRequestContext {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 * @throws IOException If at least one of the files does not exist
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Writes {@link RequestContext} response to the given file.
	 * 
	 * @param filePath
	 * @param encoding
	 * @throws IOException
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	/**
	 * Writes {@link RequestContext} response to the given file.
	 * 
	 * @param filePath
	 * @param encoding
	 * @throws IOException
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(),
				new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", 3600, "127.0.0.1",
				"/", false));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, null, "/", false));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}