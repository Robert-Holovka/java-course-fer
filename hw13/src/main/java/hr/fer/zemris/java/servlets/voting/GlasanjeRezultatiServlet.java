package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.common.Database;
import hr.fer.zemris.java.models.Band;
import hr.fer.zemris.java.models.ResultInfo;

/**
 * Prepares data for a HTML table which displays voting results and links to the
 * songs of the band that won elections.
 * 
 * @author Robert Holovka
 * @version 1.0 s
 */
@SuppressWarnings("serial")
@WebServlet(name = "results", urlPatterns = { "/glasanje-rezultati" })
public class GlasanjeRezultatiServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<ResultInfo> results = Database.getResults();

		Optional<Integer> maxVotes = results
				.stream()
				.map(r -> r.getVotes())
				.max((r1, r2) -> r1.compareTo(r2));

		List<Band> winners = results
				.stream()
				.filter(r -> r.getVotes() == maxVotes.get())
				.map(r -> Database.getBand(r.getName()))
				.collect(Collectors.toList());

		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}

}
