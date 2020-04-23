package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Demonstrates usage of {@link BarChartComponent}.
 * 
 * @author Robert Holovka
 * @version 1.0
 *
 */
@SuppressWarnings("serial")
public class BarChartDemo extends JFrame {

	/**
	 * Bar chart model implementation.
	 */
	private BarChart model;
	/**
	 * Path to the config file.
	 */
	private String file;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param model
	 * @param file
	 * @throws NullPointerException if given model is a {@code null} reference.
	 */
	public BarChartDemo(BarChart model, String file) {
		Objects.requireNonNull(model);
		this.model = model;
		this.file = file;

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(50, 50);
		setSize(800, 500);
		initGUI();
	}

	/**
	 * Initializes graphical user interface of this component.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		BarChartComponent chartComponent = new BarChartComponent(model);
		cp.add(chartComponent, BorderLayout.CENTER);
		JLabel fileLabel = new JLabel(Paths.get(file).toAbsolutePath().toString(), SwingConstants.CENTER);
		cp.add(fileLabel, BorderLayout.PAGE_START);
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args Argument from the command line
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Expected file to the path.");
			return;
		}

		List<String> lines = readFile(args[0]);
		if (lines == null || lines.size() < 6) {
			System.out.println("Invalid file.");
			return;
		}

		BarChart bc = parseChartInfo(lines);

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(bc, args[0]).setVisible(true);
		});
	}

	/**
	 * Creates new chart model from its text representation. Returns {@code null} if
	 * format is not valid.
	 * 
	 * @param lines
	 * @return
	 */
	private static BarChart parseChartInfo(List<String> lines) {

		List<XYValue> xyValues = new LinkedList<XYValue>();
		try {
			String values = lines.get(2).trim();
			String[] pairs = values.split("\\s+");
			for (var pair : pairs) {
				String[] data = pair.split(",");
				xyValues.add(new XYValue(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
			}
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			return null;
		}

		int minY = 0;
		int maxY = 0;
		int gap = 0;
		try {
			minY = Integer.parseInt(lines.get(3));
			maxY = Integer.parseInt(lines.get(4));
			gap = Integer.parseInt(lines.get(5));
		} catch (NumberFormatException e) {
			return null;
		}
		return new BarChart(xyValues, lines.get(0), lines.get(1), minY, maxY, gap);
	}

	/**
	 * Reads given file and returns its line or null if reading was unsuccesful.
	 * @param file
	 * @return
	 */
	private static List<String> readFile(String file) {
		try {
			List<String> lines = Files.readAllLines(Paths.get(file));
			return lines;
		} catch (IOException e) {
			return null;
		}
	}
}
