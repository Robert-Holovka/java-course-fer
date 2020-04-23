package hr.fer.zemris.java.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.common.Database;

/**
 * Triggers only when this applications starts or stops. Used for initialization
 * of a {@link Database} and registering server start time.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@WebListener
public class ServerContext implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Server running...");
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());

		String results = sce.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		Database.resultsFile = results;
		String bands = sce.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		Database.bandsFile = bands;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Server is closing...");
	}

}
