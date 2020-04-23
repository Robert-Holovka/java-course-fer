package hr.fer.zemris.java.hw14.dao;

/**
 * Defines exception that can appear when accessing data persistence layer.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DAOException extends RuntimeException {

	public DAOException() {
	}

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param message            Explanation why exception has been thrown
	 * @param cause              Reason why exception has been thrown
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param message Explanation why exception has been thrown
	 * @param cause   Reason why exception has been thrown
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param message Explanation why exception has been thrown
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param cause Reason why exception has been thrown
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}