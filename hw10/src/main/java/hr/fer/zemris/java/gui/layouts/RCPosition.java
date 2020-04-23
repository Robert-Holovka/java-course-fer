package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Represents one position in CalcLayout.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RCPosition {

	/**
	 * "y" coordinate of element position.
	 */
	private int row;
	/**
	 * "x" coordinate of element position.
	 */
	private int column;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * @return y" coordinate of element position.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @return "x" coordinate of element position.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
}
