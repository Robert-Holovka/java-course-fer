package hr.fer.zemris.java.gui.calc.model;

import java.util.HashSet;
import java.util.Optional;
import java.util.function.DoubleBinaryOperator;

/**
 * Concrete {@link CalcModel} implementation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Current value stored as text.
	 */
	private String valueAsText;
	/**
	 * Current value.
	 */
	private double value;
	/**
	 * Does calculator allows entries.
	 */
	private boolean isEditable;
	/**
	 * Is currently stored number positive.
	 */
	private boolean isPositive;
	/**
	 * Operand waiting for next binary operation.
	 */
	private Optional<Double> activeOperand;
	/**
	 * Pending binary operation.
	 */
	private DoubleBinaryOperator pendingOperation;
	/**
	 * List of calculators observing this model.
	 */
	private HashSet<CalcValueListener> observers = new HashSet<>();
	
	/**
	 * Default constructor.
	 */
	public CalcModelImpl() {
		valueAsText = "";
		activeOperand = Optional.empty();
		isEditable = true;
		isPositive = true;
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		if (!observers.contains(l)) {
			observers.add(l);
		}
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		observers.remove(l);
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		isPositive = value >= 0;
		valueAsText = String.valueOf(isPositive ? value : value * -1.0);
		isEditable = false;
		notifyObservers();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		isPositive = true;
		value = 0.0;
		valueAsText = "";
		isEditable = true;
		notifyObservers();
	}

	@Override
	public void clearAll() {
		clearActiveOperand();
		this.pendingOperation = null;
		clear();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException();
		}

		isPositive = !isPositive;
		value = -1.0 * value;
		notifyObservers();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!isEditable || valueAsText.contains(".") || valueAsText.isBlank()) {
			throw new CalculatorInputException();
		}

		valueAsText += ".";
		notifyObservers();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException();
		}

		// Ignore leading zeros
		if (digit == 0 && valueAsText.equals("0")) {
			return;
		}

		String newValue = valueAsText.equals("0") ? digit + "" : valueAsText + digit;
		try {
			value = Double.parseDouble(newValue);
			if (Double.isInfinite(value)) {
				throw new CalculatorInputException();
			}

			value = isPositive ? value : value * (-1);
			valueAsText = newValue;
		} catch (NumberFormatException e) {
			throw new CalculatorInputException();
		}

		notifyObservers();
	}

	@Override
	public boolean isActiveOperandSet() {
		return !activeOperand.isEmpty();
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException();
		}

		return activeOperand.get();
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = Optional.of(activeOperand);
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = Optional.empty();
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		String sign = isPositive ? "" : "-";
		if (Double.isInfinite(value)) {
			return sign + String.valueOf(Double.POSITIVE_INFINITY);
		}

		if (Double.isNaN(value)) {
			return "NaN";
		}

		return sign + (valueAsText.isBlank() ? 0 : valueAsText);
	}

	/**
	 * Notifies all observers that this calculator state has been changed.
	 */
	private void notifyObservers() {
		observers.forEach(o -> o.valueChanged(this));
	}
}
