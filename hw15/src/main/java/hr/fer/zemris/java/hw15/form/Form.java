package hr.fer.zemris.java.hw15.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * General form model used mostly for data validation.
 * 
 * @author Robo
 */
public abstract class Form {

	/**
	 * Minimum name length.
	 */
	protected static final int MIN_NAME_LENGTH = 2;
	/**
	 * Maximum name length.
	 */
	protected static final int MAX_NAME_LENGTH = 30;
	/**
	 * Minimum nickname length.
	 */
	protected static final int MIN_NICK_LENGTH = 3;
	/**
	 * Maximum nickname length.
	 */
	protected static final int MAX_NICK_LENGTH = 20;
	/**
	 * Minimum password length.
	 */
	protected static final int MIN_PASSWORD_LENGTH = 8;
	/**
	 * Maximum name length.
	 */
	protected static final int MAX_PASSWORD_LENGTH = 20;

	/**
	 * Map which contains all errors from validation.
	 */
	protected Map<String, String> errors = new HashMap<>();

	/**
	 * Fills this form from a HTTP request parameters.
	 * 
	 * @param req
	 */
	public abstract void fill(HttpServletRequest req);

	/**
	 * Performs validation.
	 */
	public abstract void validate();

	/**
	 * Transforms null Strings to an empty ones.
	 * 
	 * @param parameter
	 * @return
	 */
	protected String toEmptyIfNull(String parameter) {
		return (parameter == null) ? "" : parameter;
	}

	/**
	 * Validates generic text fields, minimum and maximum length.
	 * 
	 * @param tag Field tag
	 * @param name Field name
	 * @param min Minimum field length
	 * @param max Maximum field length
	 */
	protected void validateField(String tag, String name, int min, int max) {
		if (name.isEmpty()) {
			errors.put(tag, "Field must not be empty!");
			return;
		}

		if (name.length() < min) {
			errors.put(tag, "Field must contain at least " + min + " characters.");
			return;
		}

		if (name.length() > max) {
			errors.put(tag, "Field must contain max. " + max + " characters.");
			return;
		}
	}

	/**
	 * @return True if this form is valid, false otherwise
	 */
	public boolean isValid() {
		return errors.isEmpty();
	}

	/**
	 * Checks whether given parameter has an error.
	 * @param name
	 * @return
	 */
	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Returns error message for specific parameter.
	 * @param name
	 * @return
	 */
	public String getErrorMessage(String name) {
		return errors.get(name);
	}

}
