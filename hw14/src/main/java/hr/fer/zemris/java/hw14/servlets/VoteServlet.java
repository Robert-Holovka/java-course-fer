package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.Poll;

/**
 * Performs voting for a specified {@link Poll} and redirects to the page that
 * calculates and displays poll results.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "vote", urlPatterns = { "/servleti/glasanje-glasaj" })
public class VoteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String optionID = req.getParameter("id");
		if (optionID == null || optionID.isBlank() || !Utils.isInteger(optionID)) {
			resp.sendRedirect("./index.html");
			return;
		}

		DAO dao = DAOProvider.getDao();
		long id = Long.parseLong(optionID);

		dao.voteForOption(id);

		Long pollID = dao.getPollOption(id).getPollID();
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati?pollID=" + pollID);
	}

}
