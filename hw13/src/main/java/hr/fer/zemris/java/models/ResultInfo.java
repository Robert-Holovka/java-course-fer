package hr.fer.zemris.java.models;

/**
 * Model for one vote result. Connects single Band with number of achieved
 * votes. Stores band name and number of votes.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class ResultInfo {

	/**
	 * Band name.
	 */
	private String name;
	/**
	 * Number of votes.
	 */
	private int votes;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param name  Band name
	 * @param votes Number of votes that this band has
	 */
	public ResultInfo(String name, int votes) {
		this.name = name;
		this.votes = votes;
	}

	/**
	 * @return band name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return return number of votes that this band has
	 */
	public int getVotes() {
		return votes;
	}

}
