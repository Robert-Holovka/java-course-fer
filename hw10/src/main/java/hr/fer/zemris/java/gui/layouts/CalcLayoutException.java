package hr.fer.zemris.java.gui.layouts;

/**
 * Exception which can occur in CalcLayout manager.
 * 
 * @author Robert Holovka	
 * @version 1.0
 */
@SuppressWarnings("serial")
public class CalcLayoutException extends RuntimeException {

	/**
	 * Default constructor
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructs instance of this class defined by a given message.
	 * 
	 * @param message Reason why exception has been thrown
	 */
	public CalcLayoutException(String message) {
		super(message);
	}
}
