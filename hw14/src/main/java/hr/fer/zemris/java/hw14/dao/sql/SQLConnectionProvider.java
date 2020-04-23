package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Provider that stores connection instances for each Thread.
 *
 * @see ThreadLocal
 * @author Robert Holovka
 * @version 1.0
 */
public class SQLConnectionProvider {

	/**
	 * Storage for connections. Key is Thread ID, value is Connection instance.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Sets given connection for a calling Thread. If {@code null} reference is
	 * passed then already stored connection for that Thread will be deleted.
	 * 
	 * @param con Connection to the database
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Returns connection which calling Thread can use.
	 * 
	 * @return Connection to the database
	 */
	public static Connection getConnection() {
		return connections.get();
	}

}