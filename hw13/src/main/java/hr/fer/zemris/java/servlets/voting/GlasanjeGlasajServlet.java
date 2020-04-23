package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.common.Database;
import hr.fer.zemris.java.models.Band;

/**
 * Performs voting for a specified {@link Band} and redirects to the page that
 * displays voting results.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "vote", urlPatterns = { "/glasanje-glasaj" })
public class GlasanjeGlasajServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String id = req.getParameter("id");
		if (id == null || id.isBlank()) {
			// Redirect but don't count vote
			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
			return;
		}

		Database.vote(id);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

}
