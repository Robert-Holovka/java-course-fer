package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Sets color of the home page.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Home implements IWebWorker {
	
	@Override
	public void processRequest(RequestContext context) {
		String background = context.getPersistentParameter("bgcolor");
		context.setTemporaryParameter("background", (background == null) ? "7F7F7F" : background);
		try {
			context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
		} catch (Exception e) {
		}
	}
}