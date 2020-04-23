package hr.fer.zemris.java.hw17.jvdraw.geometry.factories;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw17.jvdraw.geometry.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geometry.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.geometry.Line;
import hr.fer.zemris.java.hw17.jvdraw.utils.CommonUtils;

/**
 * Factory for creating concrete subclasses of {@link GeometricalObject} based
 * on their JVD representation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class GeometricalObjectFactory {

	/**
	 * @param line Line which describes {@link GeometricalObject}
	 * @return Returns new {@link GeometricalObject}
	 * @throws IllegalArgumentException if requested {@link GeometricalObject}
	 *                                  subclass does not exist yet
	 */
	public static GeometricalObject createGeometricalObject(String line) {
		String[] data = CommonUtils.parseCommand(line);
		if (data.length != 2) {
			throw new IllegalArgumentException();
		}

		String objectType = data[0].toUpperCase().replaceAll("\\P{Print}", "");
		String parameters = data[1];
		GeometricalObject go = null;

		if (objectType.equals("LINE")) {
			go = createNewLine(parameters);
		} else if (objectType.equals("CIRCLE")) {
			go = createNewCircle(parameters);
		} else if (objectType.equals("FCIRCLE")) {
			go = createNewFilledCircle(parameters);
		} else {
			throw new IllegalArgumentException();
		}

		return go;
	}

	/**
	 * Creates new {@link Line} defined by a given parameters.
	 * 
	 * @param parameters
	 * @return new Line
	 * @throws IllegalArgumentException if some of the parameters is not valid
	 */
	private static GeometricalObject createNewLine(String parameters) {
		if (!CommonUtils.isValidNumberOfArgs(parameters, 7)) {
			throw new IllegalArgumentException();
		}

		List<String> data = Arrays.asList(CommonUtils.splitByWhitespace(parameters));
		// Validate parameters
		data.forEach((param) -> {
			if (!CommonUtils.isPositiveInteger(param)) {
				throw new IllegalArgumentException();
			}
		});

		Point start = new Point(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));
		Point end = new Point(Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
		Color color = new Color(Integer.parseInt(data.get(4)), Integer.parseInt(data.get(5)),
				Integer.parseInt(data.get(6)));

		return new Line(color, start, end);
	}

	/**
	 * Creates new {@link Circle} defined by a given parameters.
	 * 
	 * @param parameters
	 * @return new Line
	 * @throws IllegalArgumentException if some of the parameters is not valid
	 */
	private static GeometricalObject createNewCircle(String parameters) {
		if (!CommonUtils.isValidNumberOfArgs(parameters, 6)) {
			throw new IllegalArgumentException();
		}

		List<String> data = Arrays.asList(CommonUtils.splitByWhitespace(parameters));
		// Validate parameters
		data.forEach((param) -> {
			if (!CommonUtils.isPositiveInteger(param)) {
				throw new IllegalArgumentException();
			}
		});

		Point center = new Point(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));
		int radius = Integer.parseInt(data.get(2));
		Color color = new Color(Integer.parseInt(data.get(3)), Integer.parseInt(data.get(4)),
				Integer.parseInt(data.get(5)));

		return new Circle(color, center, radius);
	}

	/**
	 * Creates new {@link FilledCircle} defined by a given parameters.
	 * 
	 * @param parameters
	 * @return new Line
	 * @throws IllegalArgumentException if some of the parameters is not valid
	 */
	private static GeometricalObject createNewFilledCircle(String parameters) {
		if (!CommonUtils.isValidNumberOfArgs(parameters, 9)) {
			throw new IllegalArgumentException();
		}

		List<String> data = Arrays.asList(CommonUtils.splitByWhitespace(parameters));
		// Validate parameters
		data.forEach((param) -> {
			if (!CommonUtils.isPositiveInteger(param)) {
				throw new IllegalArgumentException();
			}
		});

		Point center = new Point(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)));
		int radius = Integer.parseInt(data.get(2));
		Color fgColor = new Color(Integer.parseInt(data.get(3)), Integer.parseInt(data.get(4)),
				Integer.parseInt(data.get(5)));
		Color bgColor = new Color(Integer.parseInt(data.get(6)), Integer.parseInt(data.get(7)),
				Integer.parseInt(data.get(8)));

		return new FilledCircle(fgColor, bgColor, center, radius);
	}

}
