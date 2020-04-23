package hr.fer.zemris.java.hw14.model;

import java.util.Objects;

/**
 * Model that represents one poll.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Poll {

	/**
	 * Poll identifier.
	 */
	private long id;
	/**
	 * Poll name.
	 */
	private String title;
	/**
	 * Poll description.
	 */
	private String message;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param id      Poll identifier
	 * @param title   Poll name
	 * @param message Poll description
	 * @throws NullPointerException if any of non-primitive values is {@code null}
	 *                              reference
	 */
	public Poll(long id, String title, String message) {
		this.id = id;
		this.title = Objects.requireNonNull(title);
		this.message = Objects.requireNonNull(message);
	}

	/**
	 * @return Poll identifier
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return Poll name
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return Poll description
	 */
	public String getMessage() {
		return message;
	}

}
