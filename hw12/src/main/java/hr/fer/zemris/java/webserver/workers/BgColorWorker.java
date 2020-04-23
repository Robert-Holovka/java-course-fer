package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Change background color dynamically.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		String background = context.getParameter("bgcolor");
		context.setTemporaryParameter("background", (background == null) ? "7F7F7F" : background);

		boolean isValidColor = background.matches("[a-fA-F0-9]{6}");

		try {
			if (isValidColor) {
				context.setPersistentParameter("bgcolor", background);
				context.write("<h2>Color has been updated!</h2>");
			} else {
				context.write("<h2>Color was not updated!</h2>");
			}
			context.write("<a href=\"/index2.html\">Home</a>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}