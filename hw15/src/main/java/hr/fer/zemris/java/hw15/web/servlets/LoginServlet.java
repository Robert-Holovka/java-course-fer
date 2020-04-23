package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.form.LoginForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet which handles login process.
 * 
 * @author Robo
 *
 */
@SuppressWarnings("serial")
@WebServlet(name = "login", urlPatterns = { "/servleti/login" })
public class LoginServlet extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String method = req.getParameter("login");
		if (method == null || !method.equals("Login")) {
			resp.sendRedirect("./main");
		}

		// Create form and validate
		LoginForm form = new LoginForm();
		form.fill(req);
		form.validate();

		// Render register form again with errors if they exist
		if (!form.isValid()) {
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
			return;
		}

		// Log in user
		BlogUser user = form.getUser();
		HttpSession session = req.getSession();
		session.setAttribute("current.user.id", user.getId());
		session.setAttribute("current.user.ln", user.getLastName());
		session.setAttribute("current.user.fn", user.getFirstName());
		session.setAttribute("current.user.nick", user.getNick());
		session.setAttribute("current.user.email", user.getEmail());

		resp.sendRedirect("./main");
	}
}
