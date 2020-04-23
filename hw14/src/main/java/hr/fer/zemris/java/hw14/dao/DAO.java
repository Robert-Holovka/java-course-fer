package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Interface towards subsystem of a data layer persistence.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface DAO {

	/**
	 * Creates empty poll table.
	 * 
	 * @return True if creation was successful, false otherwise
	 * @throws DAOException If something went wrong
	 */
	boolean createPollTable() throws DAOException;

	/**
	 * Creates empty poll options table.
	 * 
	 * @return True if creation was successful, false otherwise
	 * @throws DAOException If something went wrong
	 */
	boolean createPollOptionsTable() throws DAOException;

	/**
	 * Checks whether table defined by a given name and schema exists.
	 * 
	 * @param schema    Table schema
	 * @param tableName Table name
	 * @return True if table exists, false otherwise
	 * @throws DAOException If something went wrong
	 */
	boolean doesTableExists(String schema, String tableName) throws DAOException;

	/**
	 * Retrieves list of {@linkplain Poll} contained in a database.
	 * 
	 * @return list of {@linkplain Poll}
	 * @throws DAOException If something went wrong
	 */
	List<Poll> getPolls() throws DAOException;

	/**
	 * Returns from a database {@linkplain Poll} defined by a given id.
	 * 
	 * @param id Identifier for the Poll
	 * @return {@linkplain Poll} instance
	 * @throws DAOException DAOException If something went wrong
	 */
	Poll getPoll(long id) throws DAOException;

	/**
	 * Inserts given {@linkplain Poll} in database.
	 * 
	 * @param poll {@linkplain Poll} instance
	 * @return identifier for the inserted Poll
	 * @throws DAOException DAOException If something went wrong
	 */
	long insertPoll(Poll poll) throws DAOException;

	/**
	 * Retrieves list of {@linkplain PollOption} contained in a database.
	 * 
	 * @return list of {@linkplain PollOption}
	 * @throws DAOException If something went wrong
	 */
	List<PollOption> getPollOptions(long pollID) throws DAOException;

	/**
	 * Returns from a database {@linkplain PollOption} defined by a given id.
	 * 
	 * @param id Identifier for the PollOption
	 * @return {@linkplain PollOption} instance
	 * @throws DAOException DAOException If something went wrong
	 */
	PollOption getPollOption(long id) throws DAOException;

	/**
	 * Performs voting operation for a {@linkplain PollOption} defined by a given
	 * id.
	 * 
	 * @param id Identifier for the PollOption
	 * @return If voting was successful, false otherwise
	 * @throws DAOException
	 */
	boolean voteForOption(long id) throws DAOException;

	/**
	 * Inserts given {@linkplain PollOption} in database.
	 * 
	 * @param poll {@linkplain PollOption} instance
	 * @return identifier for the inserted {@link PollOption}
	 * @throws DAOException DAOException If something went wrong
	 */
	long insertPollOption(PollOption pollOption) throws DAOException;

}