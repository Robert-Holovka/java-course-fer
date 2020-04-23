package hr.fer.zemris.java.hw14.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.sql.DataSource;

/**
 * Some common utilities methods.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Utils {

	/**
	 * Checks whether given String is Integer.
	 * 
	 * @param s String
	 * @return True if given String is Integer, false otherwise
	 */
	public static boolean isInteger(String s) {
		return s.matches("^-?\\d+$");
	}

	/**
	 * Opens given properties file and constructs connection URL from it.
	 * 
	 * @param propertiesFile Path to the properties file
	 * @return Connection URL
	 * @throws IOException          If given path is invalid
	 * @throws NullPointerException if one of the required properties is missing
	 */
	public static String constructConnectionURL(Path propertiesFile) throws IOException {
		Properties props = new Properties();
		props.load(Files.newInputStream(propertiesFile));
		String host = Objects.requireNonNull(props.getProperty("host"),
				"Missing property: host");
		String port = Objects.requireNonNull(props.getProperty("port"),
				"Missing property: port");
		String name = Objects.requireNonNull(props.getProperty("name"),
				"Missing property: name");
		String user = Objects.requireNonNull(props.getProperty("user"),
				"Missing property: user");
		String password = Objects.requireNonNull(props.getProperty("password"),
				"Missing property: password");

		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:derby://");
		sb.append(host);
		sb.append(":");
		sb.append(port);
		sb.append("/");
		sb.append(name);
		sb.append(";");
		sb.append("user=" + user);
		sb.append(";");
		sb.append("password=" + password);

		return sb.toString();
	}

	/**
	 * Extracts connection from a {@link DataSource}.
	 * 
	 * @param ds {@link DataSource} instance
	 * @return null or {@link Connection} instance
	 */
	public static Connection fetchConnection(DataSource ds) {
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			return null;
		}
	}

	/**
	 * Reads seed file, filters comments and parses each line into an array of
	 * parameters. Parameters are splitted by a delimiter ';'.
	 * 
	 * @param seedFile Path to the seed file
	 * @return List of parameter arrays
	 */
	public static List<String[]> extractSeedData(Path seedFile) {
		LinkedList<String[]> data = new LinkedList<>();
		try {
			List<String> lines = Files.readAllLines(seedFile);
			for (String line : lines) {
				// Skip comments
				if (line.startsWith("#")) {
					continue;
				}

				String[] parameters = line.split(";");
				// Purify data
				for (int i = 0; i < parameters.length; i++) {
					parameters[i] = parameters[i].trim();
				}

				data.add(parameters);
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return data;
	}

	/**
	 * Wrapper for closing resources.
	 * 
	 * @param resource
	 */
	public static void closeResource(AutoCloseable resource) {
		if (resource == null) {
			return;
		}
		try {
			resource.close();
		} catch (Exception ignorable) {
		}
	}

}
