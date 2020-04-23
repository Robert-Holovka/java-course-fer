package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which handles logout process.
 * 
 * @author Robo
 *
 */
@SuppressWarnings("serial")
@WebServlet(name = "logout", urlPatterns = { "/servleti/logout" })
public class LogoutServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().invalidate();
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}
}
