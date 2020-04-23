package hr.fer.zemris.java.hw16.gallery;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caches essential informations about this gallery.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GalleryInfo {

	/**
	 * Maps tags and its content.
	 */
	private static ConcurrentHashMap<String, List<Image>> imagesForTag = new ConcurrentHashMap<>();

	/**
	 * Adds image info for specific tag.
	 * 
	 * @param tag   Image tag
	 * @param image Image Info
	 */
	public synchronized static void addImageForTag(String tag, Image image) {
		List<Image> images = (imagesForTag.containsKey(tag)) ? imagesForTag.get(tag) : new LinkedList<>();
		images.add(image);
		imagesForTag.put(tag, images);
	}

	/**
	 * Returns list of images that are tagged by some tag.
	 * 
	 * @param tag Image tag
	 * @return List of images
	 */
	public synchronized static List<Image> getImagesForTag(String tag) {
		tag = tag.trim();
		return imagesForTag.get(tag);
	}

	/**
	 * @return Returns list of all tags.
	 */
	public synchronized static List<String> getAllTags() {
		List<String> tags = new LinkedList<String>(imagesForTag.keySet());
		Collections.sort(tags);
		return tags;
	}

}
