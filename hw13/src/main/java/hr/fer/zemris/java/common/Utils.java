package hr.fer.zemris.java.common;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.PieDataset;

/**
 * Common utilities that this project classes use.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Utils {

	/**
	 * Checks whether given String contains valid Integer value. Integer is valid if
	 * given String contains number in range defined by parameters {@code min} and
	 * {@code max}. If String does not contain valid number appropriate error
	 * message is returned.
	 * 
	 * @param paramValue Value to be checked
	 * @param paramName  Parameter name, used only for constructing error messages
	 * @param min        Minimum allowed number value
	 * @param max        Maximum allowed number value
	 * @return Empty String if number is valid, error message otherwise
	 */
	public static String validateIntegerParam(String paramValue, String paramName, int min, int max) {
		if (paramValue == null) {
			return "Parameter '" + paramName + "' is not set.\t";
		}
		if (!isInteger(paramValue)) {
			return "Parameter '" + paramName + "' must be an integer.\t";
		}
		int param = Integer.parseInt(paramValue);
		if (param < min || param > max) {
			return "Parameter '" + paramName + "' must be in range [" + min + ", " + max + "].\t";
		}
		return "";
	}

	/**
	 * Constructs pie chart from a given data set and a title. Other parameters are
	 * set to default.
	 * 
	 * @param dataset Values to be plotted in pie chart
	 * @param title   Title of the pie chart
	 * @return {@link JFreeChart} Constructed pie chart
	 */
	public static JFreeChart generatePieChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(
				title,
				dataset,
				true,
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.8f);

		return chart;
	}

	/**
	 * Checks whether given String is Integer.
	 * 
	 * @param s String
	 * @return True if given String is Integer, false otherwise
	 */
	public static boolean isInteger(String s) {
		return s.matches("^-?\\d+$");
	}
}
