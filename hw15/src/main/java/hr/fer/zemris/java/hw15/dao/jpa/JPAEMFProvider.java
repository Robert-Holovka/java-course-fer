package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provides Java Persistence Api Entity Manager Factory.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class JPAEMFProvider {

	/**
	 * Storage for entitiy manager factory.
	 */
	public static EntityManagerFactory emf;
	
	/**
	 * @return entitiy manager factory instances
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets entitiy manager factory instance
	 * @param emf
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}