package hr.fer.zemris.java.hw14;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Listener that triggers when server starts and stops. Initializes database
 * connection pool. Constructs necessary tables if missing and fills them with
 * seed data.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * FQCN of a Driver class.
	 */
	private static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";
	/**
	 * Name of this database tables scheme.
	 */
	private static final String SCHEMA = "IVICA";
	/**
	 * Tag for table poll.
	 */
	private static final String TABLE_POLL = "POLLS";
	/**
	 * Tag for table polloptions.
	 */
	private static final String TABLE_POLL_OPTIONS = "POLLOPTIONS";
	/**
	 * Path to the file that contains example polls.
	 */
	private static final String POLLS_SEED = "./WEB-INF/seed/polls.txt";
	/**
	 * Path to the file that contains bands.
	 */
	private static final String BANDS_SEED = "./WEB-INF/seed/bands.txt";
	/**
	 * Path to the file that contains djs.
	 */
	private static final String DJS_SEED = "./WEB-INF/seed/djs.txt";

	/**
	 * Path to the file which contains properties necessary for constructing
	 * database connection.
	 */
	private static final String PROPERTIES_FILE = "./WEB-INF/dbsettings.properties";
	/**
	 * Tag for connections pool.
	 */
	public static final String POOL_TAG = "dbPool";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// Construct connection URL
		Path propertiesFile = Paths.get(sce.getServletContext().getRealPath(PROPERTIES_FILE));
		String connectionURL = null;
		try {
			connectionURL = Utils.constructConnectionURL(propertiesFile);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			return;
		} catch (IOException e) {
			System.out.println("Provided path to the database configuration file is invalid. Path: " +
					propertiesFile.toString());
			return;
		}

		// Create connections pool
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(DRIVER_CLASS);
		} catch (PropertyVetoException e1) {
			throw new RuntimeException("An error has occured while initializing databse pool.", e1);
		}
		cpds.setJdbcUrl(connectionURL);
		sce.getServletContext().setAttribute(POOL_TAG, cpds);

		// Create tables and load seeds if not present
		initializeDatabase(cpds, sce.getServletContext());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute(POOL_TAG);
		// Destroy connections pool
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Performs database initialization if necessary.
	 * 
	 * @param cpds Connection pool
	 * @param sc   ServletContext
	 */
	private void initializeDatabase(ComboPooledDataSource cpds, ServletContext sc) {
		// Fetch connection
		Connection con = Utils.fetchConnection(cpds);
		if (con == null) {
			System.out.println("Database is not available");
			return;
		}

		// Register connection
		SQLConnectionProvider.setConnection(con);
		DAO dao = DAOProvider.getDao();

		// Create poll table if it doesn't exist
		if (!dao.doesTableExists(SCHEMA, TABLE_POLL)) {
			dao.createPollTable();
		}
		// Create poll options table if it doesn't exist
		if (!dao.doesTableExists(SCHEMA, TABLE_POLL_OPTIONS)) {
			dao.createPollOptionsTable();
		}

		// Populate poll table if it is empty
		if (dao.getPolls().isEmpty()) {
			Map<String, Long> pollIDs = populatePollTable(dao, Paths.get(sc.getRealPath(POLLS_SEED)));
			populatePollOptionsTable(dao, Paths.get(sc.getRealPath(BANDS_SEED)), pollIDs.get("band"));
			populatePollOptionsTable(dao, Paths.get(sc.getRealPath(DJS_SEED)), pollIDs.get("djs"));
		}

		// Unregister and close connection
		SQLConnectionProvider.setConnection(null);
		Utils.closeResource(con);
	}

	/**
	 * Populates poll table with a data from provided seed file.
	 * 
	 * @param dao      Data access object
	 * @param seedFile File with defined polls
	 * @return Map<String, Long> ids of new poll entries
	 */
	private Map<String, Long> populatePollTable(DAO dao, Path seedFile) {
		Map<String, Long> ids = new HashMap<>();
		List<String[]> pollsData = Utils.extractSeedData(seedFile);
		for (String[] data : pollsData) {
			String title = data[0];
			String message = data[1];
			long newId = dao.insertPoll(new Poll(-1, title, message));
			if (title.equals("Glasanje za omiljeni bend")) {
				ids.put("band", newId);
			} else {
				ids.put("djs", newId);
			}
		}
		return ids;
	}

	/**
	 * Populates poll options table with a data from provided seed file.
	 * 
	 * @param dao      Data access object
	 * @param seedFile File with defined poll options
	 */
	private void populatePollOptionsTable(DAO dao, Path seedFile, long pollID) {
		List<String[]> options = Utils.extractSeedData(seedFile);
		for (String[] data : options) {
			dao.insertPollOption(new PollOption(-1, data[0], data[1], pollID, 0));
		}
	}

}