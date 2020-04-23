package coloring.algorithms;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import marcupic.opjj.statespace.coloring.Picture;

/**
 * Gives detail information about single pixel and performs various operations
 * on it.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Coloring implements Supplier<Pixel>, Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel> {

	/**
	 * Selected pixel from the picture.
	 */
	private Pixel reference;
	/**
	 * Reference to the picture.
	 */
	private Picture picture;
	/**
	 * New color for the selected pixel.
	 */
	private int fillColor;
	/**
	 * Color of the selected pixel.
	 */
	private int refColor;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param reference Selected pixel from the picture
	 * @param picture   Reference to the picture
	 * @param fillColor New color for the selected pixel
	 */
	public Coloring(Pixel reference, Picture picture, int fillColor) {
		this.reference = reference;
		this.picture = picture;
		this.fillColor = fillColor;
		refColor = picture.getPixelColor(reference.getX(), reference.getY());
	}

	/**
	 * Returns reference to the selected pixel.
	 * 
	 * @return Pixel
	 */
	public Pixel getReference() {
		return reference;
	}

	/**
	 * Returns reference to the active picture.
	 * 
	 * @return Picture
	 */
	public Picture getPicture() {
		return picture;
	}

	/**
	 * Returns current color of the pixel.
	 * 
	 * @return int color
	 */
	public int getFillColor() {
		return fillColor;
	}

	/**
	 * Returns new color of the pixel.
	 * 
	 * @return int color
	 */
	public int getRefColor() {
		return refColor;
	}

	@Override
	public boolean test(Pixel t) {
		return picture.getPixelColor(t.getX(), t.getY()) == refColor;
	}

	@Override
	public List<Pixel> apply(Pixel t) {
		LinkedList<Pixel> neighbours = new LinkedList<>();
		int x = t.getX();
		int y = t.getY();
		// Left
		if ((x - 1) >= 0) {
			neighbours.add(new Pixel(x - 1, y));
		}
		// Up
		if ((y - 1) >= 0) {
			neighbours.add(new Pixel(x, y - 1));
		}
		// Right
		if ((x + 1) < picture.getWidth()) {
			neighbours.add(new Pixel(x + 1, y));
		}
		// Down
		if ((y + 1) < picture.getHeight()) {
			neighbours.add(new Pixel(x, y + 1));
		}

		return neighbours;
	}

	@Override
	public void accept(Pixel t) {
		picture.setPixelColor(t.getX(), t.getY(), fillColor);
	}

	@Override
	public Pixel get() {
		return reference;
	}

}
