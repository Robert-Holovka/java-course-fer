package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demonstrates usage of {@link SmartScriptEngine} and tests its functionalities
 * on various scripts.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SmartScriptEngineDemo {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line, not used here
	 * @throws IOException If one of the files does not exist or is not readable
	 */
	public static void main(String[] args) throws IOException {

		// osnovni.smscr
		String documentBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/osnovni.smscr")), "UTF-8");
		Map<String, String> parameters = new HashMap<String, String>();
		Map<String, String> persistentParameters = new HashMap<String, String>();
		List<RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies))
						.execute();

		System.out.println();
		// zbrajanje.smscr
		documentBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/zbrajanje.smscr")), "UTF-8");
		parameters = new HashMap<String, String>();
		persistentParameters = new HashMap<String, String>();
		cookies = new ArrayList<RequestContext.RCCookie>();
		parameters.put("a", "4");
		parameters.put("b", "2");
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies))
						.execute();

		System.out.println();
		// brojPoziva.smscr
		documentBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/brojPoziva.smscr")), "UTF-8");
		parameters = new HashMap<String, String>();
		persistentParameters = new HashMap<String, String>();
		cookies = new ArrayList<RequestContext.RCCookie>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
				cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));

		System.out.println();
		// fibonacci.smscr
		documentBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/fibonacci.smscr")), "UTF-8");
		parameters = new HashMap<String, String>();
		persistentParameters = new HashMap<String, String>();
		cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies))
						.execute();
		
		System.out.println();
		// fibonaccih.smscr
		documentBody = new String(Files.readAllBytes(Paths.get("./webroot/scripts/fibonaccih.smscr")), "UTF-8");
		parameters = new HashMap<String, String>();
		persistentParameters = new HashMap<String, String>();
		cookies = new ArrayList<RequestContext.RCCookie>();
		// create engine and execute it
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies))
						.execute();
	}
}
