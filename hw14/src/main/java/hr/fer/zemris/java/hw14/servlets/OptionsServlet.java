package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Loads list of options for a selected poll and sends them to the page that
 * displays them. If provided id for a poll is invalid then it redirects to home
 * page instead.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "options", urlPatterns = { "/servleti/glasanje" })
public class OptionsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		if (pollID == null || pollID.isBlank() || !Utils.isInteger(pollID)) {
			resp.sendRedirect("./index.html");
			return;
		}

		DAO dao = DAOProvider.getDao();
		long id = Long.parseLong(pollID);

		Poll poll = dao.getPoll(id);
		if (poll == null) {
			resp.sendRedirect("./index.html");
			return;
		}

		List<PollOption> options = dao.getPollOptions(id);

		req.setAttribute("poll", poll);
		req.setAttribute("pollOptions", options);

		req.getRequestDispatcher("/WEB-INF/pages/poll-options.jsp").forward(req, resp);
	}

}
