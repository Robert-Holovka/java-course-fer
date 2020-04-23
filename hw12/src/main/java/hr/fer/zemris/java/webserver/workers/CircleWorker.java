package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Creates a single image which displays colored circle. Writes that image to
 * the {@link OutputStream} as a response to the client.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) {
		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.GREEN);
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}