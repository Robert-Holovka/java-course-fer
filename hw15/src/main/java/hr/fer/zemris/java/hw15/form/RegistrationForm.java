package hr.fer.zemris.java.hw15.form;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.common.Util;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Models registration form and validates it.
 * @author Robo
 *
 */
public class RegistrationForm extends Form {

	/**
	 * User first name.
	 */
	private String firstName;
	/**
	 * User last name.
	 */
	private String lastName;
	/**
	 * User nickname.
	 */
	private String nick;
	/**
	 * User email.
	 */
	private String email;
	/**
	 * User inital password entry.
	 */
	private String password;
	/**
	 * User confirmation password entreys.
	 */
	private String confirmationPassword;

	/**
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return nickname
	 */
	public String getNick() {
		return nick;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public void fill(HttpServletRequest req) {
		firstName = toEmptyIfNull(req.getParameter("firstName"));
		lastName = toEmptyIfNull(req.getParameter("lastName"));
		nick = toEmptyIfNull(req.getParameter("nick"));
		email = toEmptyIfNull(req.getParameter("email"));
		password = toEmptyIfNull(req.getParameter("password"));
		confirmationPassword = toEmptyIfNull(req.getParameter("confirmPassword"));
	}

	@Override
	public void validate() {
		validateField("firstName", firstName, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
		validateField("lastName", lastName, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
		validateField("nick", nick, MIN_NICK_LENGTH, MAX_NICK_LENGTH);
		validateField("password", password, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
		validateField("confirmPassword", confirmationPassword, MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
		validateEmail();
		if (!errors.containsKey("password") && !errors.containsKey("confirmPassword")) {
			validatePasswords();
		}
		if (!errors.containsKey("nick")) {
			validateNick();
		}
	}

	/**
	 * Validates user nickname.
	 */
	private void validateNick() {
		BlogUser user = DAOProvider.getDAO().getUserByNickname(nick);
		if (user != null) {
			errors.put("nick", "User with defined nickname already exists");
		}
	}

	/**
	 * Checks whether initial password entry matches with confirmation entry.
	 */
	private void validatePasswords() {
		if (password.equals(confirmationPassword)) {
			return;
		}
		errors.put("password", "Passwords must match!");
		errors.put("confirmPassword", "Passwords must match!");
	}

	/**
	 * Valides user email input.
	 */
	private void validateEmail() {
		if (email.isEmpty()) {
			errors.put("email", "Field must not be empty!");
			return;
		}
		if (!email.matches("(.+)@(.+)(\\.)(.+)")) {
			errors.put("email", "Email is not valid!");
			return;
		}
	}

	/**
	 * @return this user instance
	 */
	public BlogUser getBlogUser() {
		BlogUser user = new BlogUser();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setNick(nick);
		String passwordHash = Util.generatePasswordHash(password);
		user.setPasswordHash(passwordHash);
		return user;
	}

}
