package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Model that holds info about bar chart.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class BarChart {

	/**
	 * XY pairs, values of this chart.
	 */
	private List<XYValue> values;
	/**
	 * Description for horizontal axis.
	 */
	private String descriptionX;
	/**
	 * Description for vertical axis.
	 */
	private String descriptionY;
	/**
	 * Lower bound of this chart range.
	 */
	private int minY;
	/**
	 * Upper bound of this chart ranges
	 */
	private int maxY;
	/**
	 * Step for displaying y values.
	 */
	private int gap;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param values
	 * @param descriptionX
	 * @param descriptionY
	 * @param minY
	 * @param maxY
	 * @param gap
	 * @throws NullPointerException if given values, or description for x/y axis is a {@code null} reference;
	 */
	public BarChart(List<XYValue> values, String descriptionX, String descriptionY, int minY, int maxY, int gap) {
		Objects.requireNonNull(values);
		Objects.requireNonNull(descriptionX);
		Objects.requireNonNull(descriptionY);
		if (minY < 0) {
			throw new IllegalArgumentException("Minimum y value can not be negative");
		}
		if (minY >= maxY) {
			throw new IllegalArgumentException("Maximum y value must be greater than minimum y value");
		}

		for (var value : values) {
			if (value.getY() < minY) {
				throw new IllegalArgumentException(
						"Invalid value: " + value.getY() + " is smaller than specified y minimum: " + minY);
			}
		}

		if ((maxY - minY) % gap != 0) {
			maxY += (maxY - minY) % gap;
		}

		this.values = values;
		this.descriptionX = descriptionX;
		this.descriptionY = descriptionY;
		this.minY = minY;
		this.maxY = maxY;
		this.gap = gap;
	}

	/**
	 * @return values
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * @return description of x-axis
	 */
	public String getDescriptionX() {
		return descriptionX;
	}

	/**
	 * @return description for y-axis
	 */
	public String getDescriptionY() {
		return descriptionY;
	}

	/**
	 * @return lower range limit
	 */
	public int getMinY() {
		return minY;
	}

	/**
	 * @return upper range limit.
	 */
	public int getMaxY() {
		return maxY;
	}

	/**
	 * 
	 * @return step for displaying y values.
	 */
	public int getGap() {
		return gap;
	}

}
