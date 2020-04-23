package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JComponent;

/**
 * Bar char component whose main task is rendering given model.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class BarChartComponent extends JComponent {
	/**
	 * Light orange color.
	 */
	private static final Color LIGHT_ORANGE = new Color(255, 209, 139);
	/**
	 * Basic orange color.
	 */
	private static final Color ORANGE = new Color(255, 162, 100);
	/**
	 * Default margins.
	 */
	private Insets margin = new Insets(40, 10, 10, 10);
	/**
	 * Default gap between elements in this class.
	 */
	private static int GAP_WIDTH;

	/**
	 * Chart bar model.
	 */
	private BarChart chartInfo;

	/**
	 * COnstructs instance of this class.
	 * 
	 * @param chartInfo
	 */
	public BarChartComponent(BarChart chartInfo) {
		Objects.requireNonNull(chartInfo);
		setBackground(Color.WHITE);
		setOpaque(true);
		this.chartInfo = chartInfo;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		FontMetrics fm = getFontMetrics(getFont());
		GAP_WIDTH = fm.getHeight();

		// Set white background
		if (isOpaque()) {
			fillRectWrapper(g2, getBounds(), getBackground());
		}

		// Calculate area available for drawing
		Dimension size = getSize();
		Insets parentInsets = getParent().getInsets();
		int startPointX = parentInsets.left + margin.left;
		int startPointY = parentInsets.top + margin.top;
		int availableWidth = (int) (size.getWidth() - parentInsets.left - parentInsets.right - margin.left
				- margin.right);
		int availableHeight = (int) (size.getHeight() - parentInsets.top - parentInsets.bottom - margin.top
				- margin.bottom);
		Rectangle drawingRectangle = new Rectangle(startPointX, startPointY, availableWidth, availableHeight);

		drawChart(g2, new DrawingArea(drawingRectangle));
	}

	/**
	 * Manager method for drawing chart. Divides tasks into smaller jobs and calls
	 * appropriate functions.
	 * 
	 * @param g
	 * @param drawingArea
	 */
	private void drawChart(Graphics2D g, DrawingArea drawingArea) {
		FontMetrics fm = getFontMetrics(getFont());
		int width = drawingArea.getWidth();
		int height = drawingArea.getHeight();

		Point p = new Point(0, height / 2);
		drawVerticalString(g, drawingArea, chartInfo.getDescriptionY(), p.x, p.y, Color.BLACK);

		p = drawingArea.translate(width / 2 - fm.stringWidth(chartInfo.getDescriptionX()) / 2, height);
		g.drawString(chartInfo.getDescriptionX(), p.x, p.y);

		Insets padding = new Insets(0, GAP_WIDTH, GAP_WIDTH, 0);
		DrawingArea axisArea = drawingArea.indent(padding);

		// Draw y-axis
		List<Integer> values = new LinkedList<>();
		for (int i = chartInfo.getMinY(); i <= chartInfo.getMaxY(); i += chartInfo.getGap()) {
			values.add(i);
		}
		drawAxis(g, axisArea, values, true, Color.BLACK, Color.BLACK);

		// Draw x-axis
		drawAxis(g, axisArea,
				chartInfo.getValues().stream().map(XYValue::getX).collect(Collectors.toList()),
				false, Color.BLACK, Color.BLACK);

		int offsetX = fm.stringWidth(String.valueOf(chartInfo.getMaxY())) + GAP_WIDTH;
		int offsetY = fm.getHeight() / 2 + GAP_WIDTH;
		padding = new Insets(0, offsetX, offsetY, offsetX / 2);
		DrawingArea chartMeshArea = axisArea.indent(padding);
		drawChartMesh(g,
				chartMeshArea,
				(int) Math.ceil((chartInfo.getMaxY() - chartInfo.getMinY()) / chartInfo.getGap()),
				chartInfo.getValues().size(),
				LIGHT_ORANGE);

		plotChartValues(g, chartMeshArea, chartInfo.getValues(), ORANGE);
	}

	/**
	 * Draws horizontal and vertical axis.
	 * 
	 * @param g           graphics object
	 * @param drawingArea area available for drawing
	 * @param values      list of values
	 * @param isVertical  Specifies which axis will be drawn, horizontal or vertical
	 * @param valueColor
	 * @param axisColor
	 */
	private void drawAxis(Graphics2D g, DrawingArea drawingArea, List<Integer> values, boolean isVertical,
			Color valueColor, Color axisColor) {
		Color saveColor = g.getColor();
		int height = drawingArea.getHeight();
		int width = drawingArea.getWidth();

		FontMetrics fm = getFontMetrics(getFont());
		int valueMaxWidth = fm.stringWidth(String.valueOf(chartInfo.getMaxY()));

		g.setColor(axisColor);

		// Draw axis lines
		int offsetX = valueMaxWidth + GAP_WIDTH;
		int offsetY = fm.getHeight() / 2 + GAP_WIDTH;
		Point start = new Point(drawingArea.translatedX(valueMaxWidth + GAP_WIDTH),
				drawingArea.translatedY(height - offsetY));
		int endX = (isVertical) ? offsetX : width;
		int endY = (isVertical) ? -15 : height - offsetY;
		Point end = drawingArea.translate(endX, endY);

		var saveStroke = g.getStroke();
		g.setStroke(new BasicStroke(2));
		g.drawLine(start.x, start.y, end.x, end.y);

		// Draw arrows
		if (!isVertical) {
			g.drawLine(end.x, end.y + 5, end.x, end.y - 5);
			g.drawLine(end.x, end.y + 5, end.x + 5, end.y);
			g.drawLine(end.x, end.y - 5, end.x + 5, end.y);
		} else {
			g.drawLine(end.x + 5, end.y, end.x - 5, end.y);
			g.drawLine(end.x + 5, end.y, end.x, end.y - 5);
			g.drawLine(end.x - 5, end.y, end.x, end.y - 5);
		}

		g.setStroke(saveStroke);

		// Draw values
		int numOfValues = values.size();
		g.setColor(valueColor);
		height -= offsetY;
		// Ovo je novi fix 
		Insets padding = new Insets(0, offsetX, 0, offsetX/2);
		DrawingArea newArea = drawingArea.indent(padding);

		int rowSize = height / (numOfValues - 1);
		int heightResiduals = height - rowSize * (numOfValues - 1);

		int y = fm.getDescent() + 2;
		for (int i = numOfValues - 1; i >= 0; i--) {
			if (i != numOfValues - 1) {
				y += rowSize;
			}

			if (heightResiduals > 0) {
				y += 1;
				heightResiduals--;
			}

			String value = String.valueOf(values.get(i));
			int len = value.length();
			int realSizeMax = String.valueOf(chartInfo.getMaxY()).length();

			value = "  ".repeat(realSizeMax - len) + value;
			int xRow = newArea.getWidth()/numOfValues;
			int x = (isVertical) ? 0
					: i *xRow + xRow/2 - fm.stringWidth(value) / 2;
			y = (isVertical) ? y : height + offsetY;
			g.drawString(value, isVertical ? drawingArea.translatedX(x) : newArea.translatedX(x), drawingArea.translatedY(y));
		}

		g.setColor(saveColor);
	}

	/**
	 * Plots values retrieved from the model.
	 * 
	 * @param g           graphics object
	 * @param drawingArea area available for drawing
	 * @param values
	 * @param color
	 */
	private void plotChartValues(Graphics2D g, DrawingArea drawingArea, List<XYValue> values, Color color) {
		Color saveColor = g.getColor();
		g.setColor(color);

		int height = drawingArea.getHeight();
		int width = drawingArea.getWidth();
		int numOfValues = values.size();

		int columnWidth = width / numOfValues;
		int rows = (chartInfo.getMaxY() - chartInfo.getMinY()) / chartInfo.getGap();
		int rowHeight = height / rows;

		int heightResiduals = height - rowHeight * rows;
		int widthResiduals = width - columnWidth * numOfValues;
		for (int i = 0; i < numOfValues; i++) {
			double until = values.get(i).getY() / (double) chartInfo.getGap()
					- chartInfo.getMinY() / chartInfo.getGap();
			int rectHeight = (int) ((double) until * rowHeight);
			// Add residuals
			rectHeight += (until > heightResiduals) ? heightResiduals: heightResiduals/2;

			g.fillRect(
					drawingArea.translatedX((int) (i * columnWidth) + widthResiduals),
					drawingArea.translatedY(height - rectHeight),
					(int) (columnWidth * 0.9),
					rectHeight - 1);
		}

		g.setColor(saveColor);
	}

	/**
	 * Constructs chart background mesh.
	 * 
	 * @param g           Graphics object.
	 * @param drawingArea area available for drawing
	 * @param rows        Number of total rows
	 * @param columns     Number of total columns
	 * @param meshColor   Color of mesh
	 */
	private void drawChartMesh(Graphics2D g, DrawingArea drawingArea, int rows, int columns, Color meshColor) {
		Color saveColor = g.getColor();
		g.setColor(meshColor);
		var saveStroke = g.getStroke();
		g.setStroke(new BasicStroke(2));

		int height = drawingArea.getHeight();
		int width = drawingArea.getWidth();
		System.out.println("w" + width);
		System.out.println(drawingArea.translatedX(0));

		int columnSize = width / columns;
		int rowSize = height / rows;
		int heightResiduals = height - rowSize * rows;
		int widthResiduals = width - columnSize * columns;

		int y = 0;
		// Draw rows
		for (int i = 0; i < rows; i++) {
			if (i != 0) {
				y += rowSize;
			}

			// Distribute residuals
			if (heightResiduals > 0 && i != 0) {
				heightResiduals--;
				y += 1;
			}

			Point start = drawingArea.translate(-GAP_WIDTH / 2, y);
			Point end = drawingArea.translate(width, y);
			g.drawLine(start.x, start.y, end.x, end.y);
		}

		int x = 0;
		// Draw columns
		for (int i = 1; i <= columns; i++) {
			if (i != 0) {
				x += columnSize;
			}

			// Distribute residuals
			if (widthResiduals > 0 && i != 0) {
				widthResiduals--;
				x += 1;
			}

			Point start = drawingArea.translate(x, 0);
			Point end = drawingArea.translate(x, height + GAP_WIDTH);
			g.drawLine(start.x, start.y, end.x, end.y);
		}

		g.setStroke(saveStroke);
		g.setColor(saveColor);
	}

	/**
	 * Draws string rotated for 270 degrees around given point.
	 * 
	 * @param g           graphics object
	 * @param drawingArea Available area for drawing
	 * @param text        Text to be rendered
	 * @param x
	 * @param y
	 * @param stringColor Text color
	 */
	private void drawVerticalString(Graphics2D g, DrawingArea drawingArea, String text, int x, int y,
			Color stringColor) {
		Color saveColor = g.getColor();
		g.setColor(stringColor);

		FontMetrics fm = getFontMetrics(getFont());
		int textHeight = fm.getHeight();
		int textWidth = fm.stringWidth(text);

		x = drawingArea.translatedX(x);
		y = drawingArea.translatedY(y) + textWidth / 2;

		AffineTransform saveAT = g.getTransform();
		AffineTransform at = new AffineTransform();
		at.setToRotation(-Math.PI / 2, x, y - textHeight / 2);
		g.transform(at);
		g.drawString(text, x, y);
		g.setTransform(saveAT);

		g.setColor(saveColor);
	}

	/**
	 * Wrapper for drawing a rectangle.
	 * 
	 * @param g     graphics object
	 * @param rec   Dimensions of a rectangle
	 * @param color Rectangle color
	 */
	private void fillRectWrapper(Graphics2D g, Rectangle rec, Color color) {
		Color saveColor = g.getColor();
		g.setColor(color);
		g.fillRect(rec.x, rec.y, rec.width, rec.height);
		g.setColor(saveColor);
	}

}
