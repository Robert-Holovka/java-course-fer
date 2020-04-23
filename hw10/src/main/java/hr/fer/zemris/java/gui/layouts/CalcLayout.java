package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Specific layout that can contain 31 element. All elements are equally sized
 * except first one. First element takes 5 places and occupies positions (1, s)
 * where s is [1, 5]. This layout is responsive.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Total number of rows in this layout.
	 */
	private static final int ROWS = 5;
	/**
	 * Total number of columns in this layout.
	 */
	private static final int COLUMNS = 7;
	/**
	 * Position of the first element.
	 */
	private static final RCPosition FIRST_ELEMENT_POSITION = new RCPosition(1, 1);
	/**
	 * Number of places that first element takes.
	 */
	private static final int FIRST_ELEMENT_PLACES = 5;

	/**
	 * Gap between components.
	 */
	private int gapWidth;
	/**
	 * Currently stored positions of Components in this layout.
	 */
	private Map<Component, RCPosition> positions = new HashMap<>();

	/**
	 * Default constructor
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructs instance of this class defined by a given gap.
	 * 
	 * @param gapWidth Gap between components.
	 */
	public CalcLayout(int gapWidth) {
		this.gapWidth = gapWidth;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		positions.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return calculateDimension(parent, Component::getPreferredSize);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return calculateDimension(parent, Component::getMinimumSize);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets parentInsets = parent.getInsets();

		// Calculate available width and height
		int width = parent.getWidth() - parentInsets.left - parentInsets.right;
		int height = parent.getHeight() - parentInsets.top - parentInsets.bottom;
		width -= (COLUMNS - 1) * gapWidth;
		height -= (ROWS - 1) * gapWidth;

		// Minimum dimension for each component
		int componentWidth = width / COLUMNS;
		int componentHeight = height / ROWS;

		// True dimensions for each component, uniform distributions of residuals
		ArrayList<Integer> widths = distributeValues(componentWidth, COLUMNS, width - componentWidth * COLUMNS);
		ArrayList<Integer> heights = distributeValues(componentHeight, ROWS, height - componentHeight * ROWS);

		int numOfComponents = parent.getComponentCount();
		for (int i = 0; i < numOfComponents; i++) {
			Component component = parent.getComponent(i);
			if (!positions.containsKey(component)) {
				// Skip components that are not binded with this layout
				continue;
			}

			RCPosition pos = positions.get(component);

			int x = 0;
			for (int k = 0; k < pos.getColumn() - 1; k++) {
				x += widths.get(k);
			}
			x += parentInsets.left + (pos.getColumn() - 1) * gapWidth;

			int y = 0;
			for (int k = 0; k < pos.getRow() - 1; k++) {
				y += heights.get(k);
			}
			y += parentInsets.top + (pos.getRow() - 1) * gapWidth;

			int widthIndex = (pos.getColumn() - 1) % COLUMNS;
			int heightIndex = (pos.getRow() - 1) % ROWS;
			if (pos.equals(FIRST_ELEMENT_POSITION)) {
				int w = 0;
				for (int j = 0; j < FIRST_ELEMENT_PLACES; j++) {
					w += widths.get(j);
				}
				component.setBounds(x, y, w + (FIRST_ELEMENT_PLACES - 1) * gapWidth,
						heights.get(heightIndex));
			} else {
				component.setBounds(x, y, widths.get(widthIndex), heights.get(heightIndex));
			}
		}

	}

	private ArrayList<Integer> distributeValues(int initial, int n, int leftToDistribute) {
		ArrayList<Integer> list = new ArrayList<Integer>(Collections.nCopies(n, initial));

		distribute(0, n - 1, leftToDistribute, list);
		return list;
	}

	private int distribute(int start, int end, int leftToDistribute, List<Integer> list) {
		if (leftToDistribute <= 0)
			return leftToDistribute;

		list.set(end, list.get(end) + 1);
		leftToDistribute--;

		if (leftToDistribute <= 0)
			return leftToDistribute;

		list.set(start, list.get(start) + 1);
		leftToDistribute--;

		leftToDistribute = distribute(start, (start + end) / 2, leftToDistribute, list);
		leftToDistribute = distribute((start + end) / 2, end, leftToDistribute, list);

		return leftToDistribute;
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (constraints instanceof String) {
			String constraint = (String) constraints;
			constraint = constraint.replaceAll("\\s+", "");
			String[] data = constraint.split(",");

			try {
				constraints = new RCPosition(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
			} catch (IndexOutOfBoundsException | NumberFormatException e) {
				throw new UnsupportedOperationException();
			}
		}

		if (constraints instanceof RCPosition) {
			RCPosition pos = (RCPosition) constraints;
			if (positions.containsValue(pos)) {
				throw new CalcLayoutException("Given position is already taken by another element");
			}

			int column = pos.getColumn();
			int row = pos.getRow();
			if (column <= 0 || column > COLUMNS || row <= 0 || row > ROWS) {
				throw new CalcLayoutException("Indexes outside layout.");
			}

			if (row == 1 && (column >= 2 && column <= 5)) {
				throw new CalcLayoutException("Given position is not available.");
			}

			positions.put(comp, (RCPosition) constraints);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return calculateDimension(target, Component::getMaximumSize);
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Getter for component dimension.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private interface SizeGetter {
		Dimension getSize(Component c);
	}

	/**
	 * Calculates dimension of this layout based on components size.
	 * 
	 * @param parent Container for components
	 * @param getter Function for retrieving component dimension
	 * @return Dimension layout dimension
	 */
	private Dimension calculateDimension(Container parent, SizeGetter getter) {
		int numOfComponents = parent.getComponentCount();
		Dimension preferred = new Dimension(0, 0);

		for (int i = 0; i < numOfComponents; i++) {
			Component c = parent.getComponent(i);
			Dimension compDimension = getter.getSize(c);

			if (positions.get(c).equals(FIRST_ELEMENT_POSITION)) {
				preferred.width = Math.max(preferred.width,
						(compDimension.width - (FIRST_ELEMENT_PLACES - 1) * gapWidth) / FIRST_ELEMENT_PLACES);
				preferred.height = Math.max(preferred.height, (compDimension.height - (ROWS - 1) * gapWidth) / ROWS);
				continue;
			}

			if (compDimension != null && positions.containsKey(c)) { // Skip components outside this layout
				preferred.width = Math.max(preferred.width, compDimension.width);
				preferred.height = Math.max(preferred.height, compDimension.height);
			}
		}

		preferred.width = COLUMNS * preferred.width + (COLUMNS - 1) * gapWidth;
		preferred.height = ROWS * preferred.height + (ROWS - 1) * gapWidth;

		Insets parentInsets = parent.getInsets();
		preferred.width += parentInsets.left + parentInsets.right;
		preferred.height += parentInsets.top + parentInsets.bottom;

		return preferred;
	}
}
