package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Loads list of available polls and sends them to the page that can display
 * them.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "polls", urlPatterns = { "/servleti/index.html" })
public class PollsServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDao().getPolls();
		req.setAttribute("polls", polls);
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
