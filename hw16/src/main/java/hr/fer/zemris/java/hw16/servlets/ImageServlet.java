package hr.fer.zemris.java.hw16.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Returns full sized image or its thumbnail depending on provided parameters.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "image", urlPatterns = { "/image/*" })
public class ImageServlet extends HttpServlet {

	/**
	 * Path to the pictures in their real size.
	 */
	private static final String REAL_SIZE_PICTURES = "/WEB-INF/slike/";
	/**
	 * Path to the thumbnails.
	 */
	private static final String THUMBNAILS_PATH = "/WEB-INF/thumbnails/";
	/**
	 * Thumbail width.
	 */
	private static final int THUMBNAIL_WIDTH = 150;
	/**
	 * Thumbnail height.
	 */
	private static final int THUMBNAIL_HEIGHT = 150;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imageName = req.getParameter("fileName");
		String isThumbnail = req.getParameter("thumbnail");
		resp.setContentType("image/jpeg");

		Path imagePath = null;
		if (isThumbnail.equals("true")) {
			// Check if directory exists and create new if not
			Path thumbnailDirectory = Paths.get(getServletContext().getRealPath(THUMBNAILS_PATH));
			if (!Files.exists(thumbnailDirectory)) {
				Files.createDirectories(thumbnailDirectory);
			}

			// Create thumbnail if it doesn't exist
			Path thumbnailPath = Paths.get(getServletContext().getRealPath(THUMBNAILS_PATH + imageName));
			if (!Files.exists(thumbnailPath)) {
				Path fullSizedImage = Paths.get(getServletContext().getRealPath(REAL_SIZE_PICTURES + imageName));
				BufferedImage resized = resize(ImageIO.read(fullSizedImage.toFile()), THUMBNAIL_WIDTH,
						THUMBNAIL_HEIGHT);

				OutputStream output = Files.newOutputStream(thumbnailPath);
				ImageIO.write(resized, "jpg", output);
				output.flush();
				output.close();
			}

			imagePath = thumbnailPath;
		} else {
			imagePath = Paths.get(getServletContext().getRealPath(REAL_SIZE_PICTURES + imageName));
		}
		// Return image
		BufferedImage bufferedImage = ImageIO.read(imagePath.toFile());
		OutputStream output = resp.getOutputStream();
		ImageIO.write(bufferedImage, "jpg", output);
		output.flush();
		output.close();

	}

	/**
	 * Resizes given picture to the specified width and height.
	 * 
	 * @param img    Image to be resized
	 * @param width
	 * @param height
	 * @return Scaled picture
	 */
	private static BufferedImage resize(BufferedImage img, int width, int height) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}
}
