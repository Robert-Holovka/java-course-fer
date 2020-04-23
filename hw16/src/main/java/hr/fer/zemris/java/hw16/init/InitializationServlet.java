package hr.fer.zemris.java.hw16.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.gallery.GalleryInfo;
import hr.fer.zemris.java.hw16.gallery.Image;

/**
 * Loads images/tags from the file {@linkplain #GALLERY_DESCRIPTION} and caches
 * them into {@link GalleryInfo} class.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@WebListener
public class InitializationServlet implements ServletContextListener {

	/**
	 * Path to the gallery description file.
	 */
	private static final String GALLERY_DESCRIPTION = "/WEB-INF/opisnik.txt";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			loadGallery(sce.getServletContext().getRealPath(GALLERY_DESCRIPTION));
		} catch (IOException e) {
			System.out.println("Could not load informations about this gallery");
			return;
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	/**
	 * Parses provided file and fills {@link GalleryInfo} with its content.
	 * 
	 * @param descriptor Path to the gallery description file
	 * @throws IOException If given file does not exist
	 */
	private void loadGallery(String descriptor) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get(descriptor));
		for (int i = 1; i < lines.size();) {
			String imageFileName = lines.get(i++).trim();
			String imageDescription = lines.get(i++).trim();

			String[] tags = lines.get(i++).split(",");
			List<String> imageTags = Arrays.asList(tags);
			imageTags = imageTags.stream().map((tag) -> tag.trim()).collect(Collectors.toList());

			Image imageInfo = new Image(imageFileName, imageDescription, imageTags);

			for (var tag : tags) {
				GalleryInfo.addImageForTag(tag.trim(), imageInfo);
			}
		}
	}
}
