package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.io.OutputStream;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Creates HTML table where first column is parameter names and second one is
 * parameter values. Writes created table to the client {@link OutputStream} as
 * a response.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		context.setMimeType("text/html");
		try {
			context.write("<html><body>");
			context.write("<table border=\"1px\">");
			context.write("<tr>");
			context.write("<th>Parameter name</th>");
			context.write("<th>Parameter value</th>");
			context.write("</tr>");

			for (var name : context.getParameterNames()) {
				context.write("<tr>");
				context.write("<td>" + name + "</td>");
				context.write("<td>" + context.getParameter(name) + "</td>");
				context.write("</tr>");
			}

			context.write("</table>");
			context.write("</body></html>");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}