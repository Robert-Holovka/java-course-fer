package hr.fer.zemris.java.models;

/**
 * Model for a single band.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Band {
	/**
	 * Band identifier.
	 */
	private String ID;
	/**
	 * Band name.
	 */
	private String name;
	/**
	 * Link to a one of this band songs.
	 */
	private String song;

	/**
	 * Constructs new instance of this model.
	 * 
	 * @param iD   Band identifier
	 * @param name Band name
	 * @param song Link to a one of this band songs
	 */
	public Band(String iD, String name, String song) {
		ID = iD;
		this.name = name;
		this.song = song;
	}

	/**
	 * @return band identifier
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return band name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return link to a one of this band songs
	 */
	public String getSong() {
		return song;
	}

}
