package hr.fer.zemris.java.hw14.dao;

import hr.fer.zemris.java.hw14.dao.sql.SQLDAO;

/**
 * Singleton which provides object that knows how to communicate with data
 * persistence layer.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DAOProvider {

	/**
	 * Singleton. Registers SQL provider.
	 */
	private static DAO dao = new SQLDAO();

	/**
	 * @return Object which encapsulates access to the data persistence layer
	 */
	public static DAO getDao() {
		return dao;
	}

}