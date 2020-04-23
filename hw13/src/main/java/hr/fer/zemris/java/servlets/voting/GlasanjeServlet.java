package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.common.Database;
import hr.fer.zemris.java.models.Band;

/**
 * Loads list of {@link Band} instances and redirects to the voting page. Voting
 * page then displays that list of bands.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "voting", urlPatterns = { "/glasanje" })
public class GlasanjeServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Band> bands = Database.getBands();
		req.setAttribute("bandList", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
