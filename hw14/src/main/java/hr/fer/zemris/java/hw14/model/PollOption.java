package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Represents one poll option for one specific poll (@see Poll).
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class PollOption {
	/**
	 * Poll option identifier.
	 */
	private long id;
	/**
	 * Poll option name.
	 */
	private String optionTitle;
	/**
	 * Link to the poll option content.
	 */
	private String optionLink;
	/**
	 * ID of a poll that is option belongs to.
	 */
	private long pollID;
	/**
	 * Number of votes that this option has.
	 */
	private long votesCount;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param id          Poll option identifier
	 * @param optionTitle Poll option name
	 * @param optionLink  Link to the poll option content
	 * @param pollID      ID of a poll that is option belongs to
	 * @param votesCount  Number of votes that this option has
	 * @throws NullPointerException if any of non-primitive values is {@code null}
	 *                              reference
	 */
	public PollOption(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
		super();
		this.id = id;
		this.optionTitle = Objects.requireNonNull(optionTitle);
		this.optionLink = Objects.requireNonNull(optionLink);
		this.pollID = pollID;
		this.votesCount = votesCount;
	}

	/**
	 * @return Poll option identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Poll option name
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * @return Link to the poll option content
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * @return ID of a poll that is option belongs to
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * @return Number of votes that this option has
	 */
	public long getVotesCount() {
		return votesCount;
	}

}
