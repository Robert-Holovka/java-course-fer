package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Redirects to the main page.
 * @author Robo
 *
 */
@SuppressWarnings("serial")
@WebServlet(name = "home", urlPatterns = { "/index.jsp", "/index.html" })
public class HomeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		resp.sendRedirect("./servleti/main");
	}
}
