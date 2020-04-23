package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Represents single vector in a 2D space. Vector is defined by its start
 * position, direction, color of the line and length.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class TurtleState {

	/**
	 * Start point of a vector
	 */
	private Vector2D position;
	/**
	 * Direction(angle) of a vector.
	 */
	private Vector2D direction;
	/**
	 * Color of a line.
	 */
	private Color color;
	/**
	 * Vector length.
	 */
	private double movementLength;

	/**
	 * Constructs new state of the vector defined by its start position, direction,
	 * color of the line and length.
	 * 
	 * @param position       Start point of the vector
	 * @param direction      Direction of the vector
	 * @param color          Color of the vector line
	 * @param movementLength Vector length
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double movementLength) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.movementLength = movementLength;
	}

	/**
	 * Returns start position of this vector.
	 * 
	 * @return Vector2D start point
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets new start position of this vector.
	 * 
	 * @param position Vector2D new start point
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * Returns direction of this vector.
	 * 
	 * @return Vector2D direction.
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets new direction of this vector.
	 * 
	 * @param direction Vector2D new direction
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * Returns the color of a line of this vector.
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets new color of a line for this vector.
	 * 
	 * @param Color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns length of this vector.
	 * 
	 * @return double vector length
	 */
	public double getMovementLength() {
		return movementLength;
	}

	/**
	 * Sets new length for this vector.
	 * 
	 * @param movementLength length
	 */
	public void setMovementLength(double movementLength) {
		this.movementLength = movementLength;
	}

	/**
	 * Returns new instance of this vector and its state.
	 * 
	 * @return TurtleState copy of this vector
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), color, movementLength);
	}
}
