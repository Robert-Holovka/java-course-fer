package hr.fer.zemris.java.gui.calc.components;

import java.awt.event.ActionListener;

/**
 * Special button which can do normal and invertible operation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InvertibleButton extends CalcButton implements InverseListener {

	/**
	 * Normal action.
	 */
	private ActionListener normalAction;
	/**
	 * Inverse of the normal action.
	 */
	private ActionListener inverseAction;
	/**
	 * Normal action name.
	 */
	private String normalActionText;
	/**
	 * Inverse action name.
	 */
	private String inverseActionText;
	/**
	 * Flag for setting appropriate action and name.
	 */
	private boolean isInversed;

	/**
	 * Constructs instance of this class.
	 * 
	 * @param normalActionText  Normal action name
	 * @param normalAction      Normal action
	 * @param inverseActionText Inverse action name
	 * @param inverseAction     Inverse of the normal action
	 */
	public InvertibleButton(String normalActionText, ActionListener normalAction,
			String inverseActionText, ActionListener inverseAction) {
		this(normalActionText, normalAction, inverseActionText, inverseAction, false);
	}

	/**
	 * Constructs instance of this class.
	 * 
	 * @param normalActionText  Normal action name
	 * @param normalAction      Normal action
	 * @param inverseActionText Inverse action name
	 * @param inverseAction     Inverse of the normal action
	 * @param isInversed
	 */
	public InvertibleButton(String normalActionText, ActionListener normalAction,
			String inverseActionText, ActionListener inverseAction, boolean isInversed) {
		super();
		this.normalAction = normalAction;
		this.inverseAction = inverseAction;
		this.normalActionText = normalActionText;
		this.inverseActionText = inverseActionText;
		this.isInversed = isInversed;
		changeState();
	}

	/**
	 * Changes {@link #isInversed} flag.
	 */
	public void inverse() {
		isInversed = !isInversed;
		changeState();
	}

	/**
	 * Sets appropriate action and name base on {@link #isInversed} flag value.
	 */
	private void changeState() {
		removeActionListener(isInversed ? normalAction : inverseAction);
		addActionListener(isInversed ? inverseAction : normalAction);
		setText(isInversed ? inverseActionText : normalActionText);
	}

}
