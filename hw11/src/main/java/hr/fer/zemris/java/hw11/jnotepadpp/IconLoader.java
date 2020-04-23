package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * Class responsible for loading/caching icons.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class IconLoader {

	/**
	 * Map of already loaded icons.
	 */
	private static HashMap<ImageInfo, ImageIcon> cache = new HashMap<>();

	/**
	 * Informations about image.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class ImageInfo {
		/**
		 * Image width.
		 */
		private int width;
		/**
		 * Image height.
		 */
		private int height;
		/**
		 * Image virtual location(resource location).
		 */
		private String path;

		/**
		 * Constructs new instance of this class.
		 * 
		 * @param width  Image width
		 * @param height Image height
		 * @param path   Image virtual location(resource location).
		 */
		public ImageInfo(int width, int height, String path) {
			this.width = width;
			this.height = height;
			this.path = path;
		}

		@Override
		public int hashCode() {
			return Objects.hash(height, path, width);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof ImageInfo))
				return false;
			ImageInfo other = (ImageInfo) obj;
			return height == other.height && Objects.equals(path, other.path) && width == other.width;
		}

	}

	/**
	 * Loads image from a specified location defined by a given properties.
	 * 
	 * @param caller Reference to the caller of this method
	 * @param path   Image virtual location(resource location).
	 * @param width  Image width
	 * @param height Image height
	 * @return ImageIcon
	 */
	public static ImageIcon loadImage(Object caller, String path, int width, int height) {
		ImageInfo imageInfo = new ImageInfo(width, height, path);
		if (cache.containsKey(imageInfo)) {
			return cache.get(imageInfo);
		}

		try (InputStream is = caller.getClass().getResourceAsStream(path)) {
			Image image = ImageIO.read(is);
			image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

			ImageIcon icon = new ImageIcon(image);
			cache.put(imageInfo, icon);
			return icon;
		} catch (NullPointerException | IOException e) {
			throw new RuntimeException("Image not found or could not be loaded.");
		}
	}
}
