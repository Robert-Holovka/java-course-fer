package hr.fer.zemris.java.hw15.form;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.common.Util;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Validates user log in process.
 * 
 * @author Robo
 *
 */
public class LoginForm extends Form {

	/**
	 * User nickname
	 */
	private String nick;
	/**
	 * User password
	 */
	private String password;
	/**
	 * User istance
	 */
	private BlogUser user;

	/**
	 * @return user nickname
	 */
	public String getNick() {
		return nick;
	}

	@Override
	public void fill(HttpServletRequest req) {
		nick = toEmptyIfNull(req.getParameter("nick"));
		password = toEmptyIfNull(req.getParameter("password"));
	}

	@Override
	public void validate() {
		validateField("nick", nick, MIN_NICK_LENGTH, MAX_NICK_LENGTH);
		validateField("password", password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
		if (!errors.containsKey("nick")) {
			validateNick();
		}
		if (!errors.containsKey("password")) {
			validatePassword();
		}
	}

	/**
	 * Checks whether initial and confirmation passwords match.
	 */
	private void validatePassword() {
		if (getUser() != null) {
			String enteredPasswordHash = Util.generatePasswordHash(password);
			String storedPasswordHash = user.getPasswordHash();
			if (!enteredPasswordHash.equals(storedPasswordHash)) {
				errors.put("password", "Wrong nick and/or password!");
			}
		}
	}

	/**
	 * Validates user nickname.
	 */
	private void validateNick() {
		if (getUser() == null) {
			errors.put("nick", "Wrong nick and/or password!");
		}
	}
	
	/**
	 * @return instance
	 */
	public BlogUser getUser() {
		if (user == null) {
			user = DAOProvider.getDAO().getUserByNickname(nick);
		}
		return user;
	}

}
