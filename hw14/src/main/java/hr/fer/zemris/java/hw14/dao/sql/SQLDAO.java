package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Concrete implementation of {@link DAO}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class SQLDAO implements DAO {

	@Override
	public boolean createPollTable() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		boolean success = false;
		try {
			pst = con.prepareStatement("CREATE TABLE Polls " +
					"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY," +
					"title VARCHAR(150) NOT NULL," +
					"message CLOB(2048) NOT NULL)");
			pst.execute();
			success = true;
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		} finally {
			Utils.closeResource(pst);
		}

		return success;
	}

	@Override
	public boolean createPollOptionsTable() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		boolean success = false;
		try {
			pst = con.prepareStatement("CREATE TABLE PollOptions " +
					"(id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
					"optionTitle VARCHAR(100) NOT NULL, " +
					"optionLink VARCHAR(150) NOT NULL, " +
					"pollID BIGINT, " +
					"votesCount BIGINT, " +
					"FOREIGN KEY (pollID) REFERENCES Polls(id))");
			pst.execute();
			success = true;
		} catch (Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		} finally {
			Utils.closeResource(pst);
		}

		return success;
	}

	public boolean doesTableExists(String schema, String tableName) {
		Connection con = SQLConnectionProvider.getConnection();
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		boolean exists = false;

		try {
			dbmd = con.getMetaData();
			rs = dbmd.getTables(null, schema, tableName, null);
			if (rs.next()) {
				exists = true;
			}
		} catch (Exception ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(rs);
		}
		return exists;
	}

	@Override
	public long insertPoll(Poll poll) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;

		try {
			pst = con.prepareStatement("INSERT INTO polls (title, message) values (?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, poll.getTitle());
			pst.setString(2, poll.getMessage());
			pst.executeUpdate();

			rset = pst.getGeneratedKeys();
			System.out.println(rset);
			if (rset != null && rset.next()) {
				long newID = rset.getLong(1);
				return newID;
			}

		} catch (Exception ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(rset);
			Utils.closeResource(pst);
		}

		return -1;
	}

	@Override
	public long insertPollOption(PollOption pollOption) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;

		try {
			pst = con.prepareStatement(
					"INSERT INTO polloptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)",
					Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, pollOption.getOptionTitle());
			pst.setString(2, pollOption.getOptionLink());
			pst.setLong(3, pollOption.getPollID());
			pst.setLong(4, pollOption.getVotesCount());
			pst.executeUpdate();

			rset = pst.getGeneratedKeys();
			System.out.println(rset);
			if (rset != null && rset.next()) {
				long newID = rset.getLong(1);
				System.out.println("NOVI ID: " + newID);
				return newID;
			}

		} catch (Exception ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(rset);
			Utils.closeResource(pst);
		}

		return -1;
	}

	@Override
	public List<Poll> getPolls() {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;
		List<Poll> polls = new LinkedList<Poll>();

		try {
			pst = con.prepareStatement("SELECT id, title, message from polls order by title");
			rset = pst.executeQuery();
			while (rset.next()) {
				long id = rset.getLong(1);
				String title = rset.getString(2);
				String message = rset.getString(3);
				Poll p = new Poll(id, title, message);
				polls.add(p);
			}

		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(rset);
			Utils.closeResource(pst);
		}
		return polls;
	}

	@Override
	public Poll getPoll(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;

		try {
			pst = con.prepareStatement("SELECT id, title, message from polls WHERE id=?");
			pst.setLong(1, id);
			rset = pst.executeQuery();
			while (rset.next()) {
				id = rset.getLong(1);
				String title = rset.getString(2);
				String message = rset.getString(3);
				return new Poll(id, title, message);
			}
		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(rset);
			Utils.closeResource(pst);
		}
		return null;
	}

	@Override
	public List<PollOption> getPollOptions(long pollID) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;
		List<PollOption> pollOptions = new LinkedList<>();

		try {
			pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, pollID, votesCount  from polloptions WHERE pollID=? order by optionTitle");
			pst.setLong(1, pollID);
			rset = pst.executeQuery();
			while (rset.next()) {
				long id = rset.getLong(1);
				String title = rset.getString(2);
				String link = rset.getString(3);
				long pID = rset.getLong(4);
				long votes = rset.getLong(5);
				PollOption po = new PollOption(id, title, link, pID, votes);
				pollOptions.add(po);
			}
		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(pst);
			Utils.closeResource(rset);
		}
		return pollOptions;
	}

	@Override
	public PollOption getPollOption(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		ResultSet rset = null;

		try {
			pst = con.prepareStatement(
					"SELECT id, optionTitle, optionLink, pollID, votesCount  from polloptions WHERE id=?");
			pst.setLong(1, id);
			rset = pst.executeQuery();
			while (rset.next()) {
				id = rset.getLong(1);
				String title = rset.getString(2);
				String link = rset.getString(3);
				long pID = rset.getLong(4);
				long votes = rset.getLong(5);
				return new PollOption(id, title, link, pID, votes);
			}

		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(pst);
			Utils.closeResource(rset);
		}
		return null;
	}

	@Override
	public boolean voteForOption(long id) {
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("UPDATE polloptions set votescount=votescount+1 WHERE id=?");
			pst.setLong(1, id);
			int result = pst.executeUpdate();
			return result == 1;
		} catch (SQLException ex) {
			throw new DAOException();
		} finally {
			Utils.closeResource(pst);
		}
	}

}