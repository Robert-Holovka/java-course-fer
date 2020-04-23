package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.gui.calc.components.CalcButton;
import hr.fer.zemris.java.gui.calc.components.CalcCheckBox;
import hr.fer.zemris.java.gui.calc.components.InvertibleButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Calculator with some basic actions. Shows usage of models and components
 * created in subpackages.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Calculator extends JFrame {

	/**
	 * Default border line thickness.
	 */
	private static final int DEFAULT_BORDER_WIDTH = 2;
	/**
	 * Gap between components.
	 */
	private static final int GAP_WIDTH = 3;
	/**
	 * Enlarged text font.
	 */
	private static final float LARGE_TEXT = 40f;

	/**
	 * Calculator model implementation.
	 */
	private CalcModel model;
	/**
	 * Stack for storing/getting current values.
	 */
	private Stack<Double> stack = new Stack<>();
	
	private JLabel display;

	/**
	 * Constructs default instance of this class.
	 * 
	 * @param model Calculator model implementation
	 */
	public Calculator(CalcModel model) {
		this.model = model;

		setTitle("Java Calculator v1.0");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocation(0, 0);
		setSize(800, 500);
		initGUI();
	}

	/**
	 * Initializes graphical user interface of this container.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(GAP_WIDTH));

		CalcCheckBox cb = new CalcCheckBox("Inv");
		cp.add(cb, new RCPosition(5, 7));

		initDisplay(cp);
		initDigitButtons(cp);
		initInvertibleButtons(cp, cb);
		initBinaryButtons(cp);
		initOtherButtons(cp);
	}

	/**
	 * Adds buttons that perform binary operations.
	 * 
	 * @param cp Container
	 */
	private void initBinaryButtons(Container cp) {
		CalcButton cb = new CalcButton("+");
		cb.addActionListener(toBinaryOperation(Double::sum));
		cp.add(cb, new RCPosition(5, 6));

		cb = new CalcButton("-");
		cb.addActionListener(toBinaryOperation((a, b) -> a - b));
		cp.add(cb, new RCPosition(4, 6));

		cb = new CalcButton("*");
		cb.addActionListener(toBinaryOperation((a, b) -> a * b));
		cp.add(cb, new RCPosition(3, 6));

		cb = new CalcButton("/");
		cb.addActionListener(toBinaryOperation((a, b) -> a / b));
		cp.add(cb, new RCPosition(2, 6));

	}

	/**
	 * Adds other buttons, with no special group and rules.
	 * 
	 * @param cp Container
	 */
	private void initOtherButtons(Container cp) {
		CalcButton cb = new CalcButton("clr");
		cb.addActionListener(a -> {
			model.clear();
		});
		cp.add(cb, new RCPosition(1, 7));

		cb = new CalcButton("reset");
		cb.addActionListener(a -> {
			model.clearAll();
		});
		cp.add(cb, new RCPosition(2, 7));

		cb = new CalcButton(".");
		cb.addActionListener(a -> {
			if (!model.isEditable()) {
				showError();
				return;
			}
			model.insertDecimalPoint();
		});
		cp.add(cb, new RCPosition(5, 5));

		cb = new CalcButton("+/-");
		cb.addActionListener(a -> {
			if (!model.isEditable()) {
				showError();
				return;
			}
			model.swapSign();
		});
		cp.add(cb, new RCPosition(5, 4));

		cb = new CalcButton("=");
		cb.addActionListener(a -> {
			doPendingOperation(null);
		});
		cp.add(cb, new RCPosition(1, 6));

		cb = new CalcButton("1/x");
		cb.addActionListener(toUnaryOperation(val -> 1.0 / val));
		cp.add(cb, new RCPosition(2, 1));

		cb = new CalcButton("push");
		cb.addActionListener(a -> stack.push(model.getValue()));
		cp.add(cb, new RCPosition(3, 7));

		cb = new CalcButton("pop");
		cb.addActionListener(a -> {
			if (stack.isEmpty()) {
				showError("Stack is empty!");
			} else {
				model.setValue(stack.pop());
			}
		});
		cp.add(cb, new RCPosition(4, 7));
	}

	private void showError() {
		JOptionPane.showMessageDialog(this, "Invalid operation.", "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, "Stack is empty!", "Error", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Adds invertible buttons. Those are buttons that can perform 2 operations and
	 * they have two names.
	 * 
	 * @param cp Container
	 * @param cb CheckBox Inv
	 */
	private void initInvertibleButtons(Container cp, CalcCheckBox cb) {
		InvertibleButton ib = new InvertibleButton("sin",
				toUnaryOperation(Math::sin),
				"arcsin",
				toUnaryOperation(Math::asin));
		cp.add(ib, new RCPosition(2, 2));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"cos",
				toUnaryOperation(Math::cos),
				"arccos",
				toUnaryOperation(Math::acos));
		cp.add(ib, new RCPosition(3, 2));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"tan",
				toUnaryOperation(Math::tan),
				"arctan",
				toUnaryOperation(Math::atan));
		cp.add(ib, new RCPosition(4, 2));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"ctg",
				toUnaryOperation(val -> 1.0 / Math.tan(val)),
				"arcctg",
				toUnaryOperation(val -> 1.0 / (1.0 / Math.tan(val))));
		cp.add(ib, new RCPosition(5, 2));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"log",
				toUnaryOperation(Math::log10),
				"10^x",
				toUnaryOperation(val -> Math.pow(10, val)));
		cp.add(ib, new RCPosition(3, 1));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"ln",
				toUnaryOperation(Math::log),
				"e^x",
				toUnaryOperation(Math::exp));
		cp.add(ib, new RCPosition(4, 1));
		cb.addListener(ib);

		ib = new InvertibleButton(
				"x^n",
				toBinaryOperation((a, b) -> Math.pow(a, b)),
				"x^(1/n)",
				toBinaryOperation((a, b) -> Math.pow(a, 1 / b)));
		cp.add(ib, new RCPosition(5, 1));
		cb.addListener(ib);

	}

	/**
	 * Adds buttons responsible for inserting digits.
	 * 
	 * @param cp Container
	 */
	private void initDigitButtons(Container cp) {
		int digitStartRow = 5;
		int digitStartColumn = 3;

		for (int i = 0; i <= 9; i++) {
			CalcButton cb = new CalcButton(String.valueOf(i), LARGE_TEXT);

			cb.addActionListener(a -> {
				if (!model.isEditable()) {
					showError();
					return;
				}
				JButton src = (JButton) a.getSource();
				Integer digit = Integer.parseInt(src.getText());
				model.insertDigit(digit);
			});

			int row = (int) (digitStartRow - Math.ceil((double) i / digitStartColumn));

			int column = 0;
			switch (i % digitStartColumn) {
			case 0:
				column = digitStartColumn + 2;
				break;
			case 1:
				column = digitStartColumn;
				break;
			case 2:
				column = digitStartColumn + 1;
			}
			column = (i == 0) ? digitStartColumn : column;

			cp.add(cb, new RCPosition(row, column));
		}
	}

	/**
	 * Initialization of a calculator display.
	 * 
	 * @param cp Container
	 */
	private void initDisplay(Container cp) {
		display = new JLabel("0", SwingConstants.RIGHT);
		display.setBackground(Color.YELLOW);
		display.setOpaque(true);
		display.setBorder(BorderFactory.createLineBorder(Color.BLACK, DEFAULT_BORDER_WIDTH));
		display.setFont(display.getFont().deriveFont(LARGE_TEXT));

		cp.add(display, new RCPosition(1, 1));
		model.addCalcValueListener(m -> display.setText(m.toString()));
	}

	/**
	 * Action where operation is immediately applied on a current value.
	 * 
	 * @param func Operation
	 * @return Task wrapped in ActionListener interface
	 */
	private ActionListener toUnaryOperation(Function<Double, Double> func) {
		return e -> model.setValue(func.apply(model.getValue()));
	}

	/**
	 * Action where operation is immediately applied on a current value.
	 * 
	 * @param func Operation
	 * @return Task wrapped in ActionListener interface
	 */
	private ActionListener toBinaryOperation(DoubleBinaryOperator func) {
		return e -> {
			if (model.getPendingBinaryOperation() == null) {
				model.setPendingBinaryOperation(func);
				String value = display.getText();
				model.setActiveOperand(Double.parseDouble(value));
				model.clear();
				display.setText(value);
			} else {
				doPendingOperation(func);
			}
		};
	}

	/**
	 * Performs pending operation stored in model.
	 */
	private void doPendingOperation(DoubleBinaryOperator newOperation) {
		var pendingAction = model.getPendingBinaryOperation();
		if (!model.isActiveOperandSet() || pendingAction == null) {
			showError();
			return;
		}
		var firstOperand = model.getActiveOperand();
		var secondOperand = model.getValue();
		Double value = pendingAction.applyAsDouble(firstOperand, secondOperand);
		if(newOperation == null) {
			model.clearAll();
		} else {
			model.clear();
			model.setPendingBinaryOperation(newOperation);
			model.setActiveOperand(value);
		}
		display.setText(String.valueOf(value));
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			new Calculator(new CalcModelImpl()).setVisible(true);
		});
	}
}
