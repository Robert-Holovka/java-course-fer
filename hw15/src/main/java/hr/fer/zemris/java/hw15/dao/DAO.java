package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public interface DAO {

	/**
	 * Returns blog entry for a specified id.
	 * 
	 * @param id Blog entry identifier
	 * @return BlogEntry
	 * @throws DAOException if something went wrong
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Stores given user in a database.
	 * 
	 * @param user
	 * @throws DAOException
	 */
	public void createUser(BlogUser user) throws DAOException;

	/**
	 * Returns user defined by a given nick.
	 * 
	 * @param nick
	 * @return
	 * @throws DAOException
	 */
	public BlogUser getUserByNickname(String nick) throws DAOException;

	/**
	 * Returns all users from database.
	 * 
	 * @return
	 * @throws DAOException
	 */
	public List<BlogUser> getUsers() throws DAOException;

	/**
	 * Saves provided instance of blog entry.
	 * 
	 * @param entry
	 * @throws DAOException
	 */
	public void createBlogEntry(BlogEntry entry) throws DAOException;

	/**
	 * Creates new comment for a specific entry.
	 * 
	 * @param comment
	 * @throws DAOException
	 */
	public void createComment(BlogComment comment) throws DAOException;

	/**
	 * Updates blog entry.
	 * 
	 * @param entry
	 * @throws DAOException
	 */
	public void updateBlogEntry(BlogEntry entry) throws DAOException;

}