package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Prepares data for a page that displays many informations about poll results.
 * Such informations are result table, pie-chart, XLS file. If provided id for a
 * poll is invalid then it redirects to home page instead.
 * 
 * @author Robert Holovka
 * @version 1.0 s
 */
@SuppressWarnings("serial")
@WebServlet(name = "results", urlPatterns = { "/servleti/glasanje-rezultati" })
public class ResultsServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		if (pollID == null || pollID.isBlank() || !Utils.isInteger(pollID)) {
			resp.sendRedirect("./index.html");
			return;
		}

		List<PollOption> options = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
		if (options.isEmpty()) {
			resp.sendRedirect("./index.html");
			return;
		}

		Optional<Long> maxVotes = options
				.stream()
				.map(r -> r.getVotesCount())
				.max((r1, r2) -> r1.compareTo(r2));

		List<PollOption> winners = options
				.stream()
				.filter(r -> r.getVotesCount() == maxVotes.get())
				.collect(Collectors.toList());

		// Sort by number of votes, then by a name
		Comparator<PollOption> comp = Comparator
				.comparing(PollOption::getVotesCount)
				.reversed()
				.thenComparing(PollOption::getOptionTitle);
		options.sort(comp);
		winners.sort(comp);

		req.setAttribute("results", options);
		req.setAttribute("winners", winners);
		req.setAttribute("pollID", pollID);
		req.getRequestDispatcher("/WEB-INF/pages/poll-results.jsp").forward(req, resp);
	}

}
