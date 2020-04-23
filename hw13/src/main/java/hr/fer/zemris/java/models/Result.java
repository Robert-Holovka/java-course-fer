package hr.fer.zemris.java.models;

/**
 * Model for one vote result. Connects single Band with number of achieved
 * votes. Stores band identifier and number of votes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Result {

	/*
	 * Band identifier.
	 */
	private String ID;
	/**
	 * Number of votes.
	 */
	private int votes;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param ID    Band identifier
	 * @param votes Number of votes
	 */
	public Result(String ID, int votes) {
		this.ID = ID;
		this.votes = votes;
	}

	/**
	 * @return band identifier
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return Number of votes that band has
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Votes for this band. Increases number of votes by 1.
	 */
	public synchronized void increaseVote() {
		votes++;
	}

}
