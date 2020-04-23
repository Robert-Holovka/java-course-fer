package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet which maps given color to a session map. Redirects again to the same
 * page forcing it to reload.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "bgColor", urlPatterns = { "/setcolor" })
public class BackgroundColorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String bgColor = req.getParameter("pickedBgCol");
		req.getSession().setAttribute("pickedBgColor", bgColor);
		resp.sendRedirect("./colors.jsp");
	}
}
