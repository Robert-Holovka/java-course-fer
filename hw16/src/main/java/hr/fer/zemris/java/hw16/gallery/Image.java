package hr.fer.zemris.java.hw16.gallery;

import java.util.List;

/**
 * Provides additional informations about some image.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Image {
	/**
	 * Image file name.
	 */
	private String fileName;
	/**
	 * Image description.
	 */
	private String description;
	/**
	 * List of tags that represents this image.
	 */
	private List<String> imageTags;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param fileName    Image file name
	 * @param description Image description
	 * @param imageTags   List of tags that represents this image
	 */
	public Image(String fileName, String description, List<String> imageTags) {
		this.fileName = fileName;
		this.description = description;
		this.imageTags = imageTags;
	}

	/**
	 * @return image file name
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return image description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return List of tags that represents this image
	 */
	public List<String> getImageTags() {
		return imageTags;
	}

}
