package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;

/**
 * Displays main page of this application.
 * @author Robo
 *
 */
@SuppressWarnings("serial")
@WebServlet(name = "main", urlPatterns = { "/servleti/main" })
public class MainServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("users", DAOProvider.getDAO().getUsers());
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
