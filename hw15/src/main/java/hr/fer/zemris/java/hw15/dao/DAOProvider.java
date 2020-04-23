package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Singleton which provides object that knows how to communicate with data
 * persistence layer.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DAOProvider {

	/**
	 * Singleton. Registers JPA provider.
	 */
	private static DAO dao = new JPADAOImpl();

	/**
	 * @return Object which encapsulates access to the data persistence layer
	 */
	public static DAO getDAO() {
		return dao;
	}

}