package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.form.RegistrationForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Class that handles user registration and validation.
 * 
 * @author Robo
 *
 */
@SuppressWarnings("serial")
@WebServlet(name = "register", urlPatterns = { "/servleti/register" })
public class RegisterServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("method");
		if (method == null || !method.equals("Save")) {
			resp.sendRedirect("./");
		}

		// Create form and validate
		RegistrationForm form = new RegistrationForm();
		form.fill(req);
		form.validate();

		// Render register form again with errors if they exist
		if (!form.isValid()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/registration.jsp").forward(req, resp);
			return;
		}

		// Create new user
		BlogUser user = form.getBlogUser();
		DAOProvider.getDAO().createUser(user);
		resp.sendRedirect("./main");
	}
}
