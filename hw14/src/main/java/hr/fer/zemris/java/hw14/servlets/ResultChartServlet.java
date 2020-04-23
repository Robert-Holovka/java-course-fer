package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Returns PNG image that contains pie chart. Pie chart represents results from
 * a poll specified by a given id. If provided id for a poll is invalid then it
 * redirects to home page instead.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "voteReport", urlPatterns = { "/servleti/glasanje-grafika" })
public class ResultChartServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		if (pollID == null || pollID.isBlank() || !Utils.isInteger(pollID)) {
			resp.sendRedirect("./index.html");
			return;
		}

		resp.setContentType("image/png");
		DefaultPieDataset dataset = loadDataset(DAOProvider.getDao().getPollOptions(Long.parseLong(pollID)));

		JFreeChart chart = generatePieChart(dataset, "Poll results");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
	}

	/**
	 * Prepares data set for a {@link JFreeChart}.
	 * 
	 * @param results Results to be inserted in {@link DefaultPieDataset}
	 * @return DefaultPieDataset
	 */
	private DefaultPieDataset loadDataset(List<PollOption> results) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption r : results) {
			dataset.setValue(r.getOptionTitle(), r.getVotesCount());
		}
		return dataset;
	}

	/**
	 * Constructs pie chart from a given data set and a title. Other parameters are
	 * set to default.
	 * 
	 * @param dataset Values to be plotted in pie chart
	 * @param title   Title of the pie chart
	 * @return {@link JFreeChart} Constructed pie chart
	 */
	private JFreeChart generatePieChart(PieDataset dataset, String title) {
		JFreeChart chart = ChartFactory.createPieChart3D(
				title,
				dataset,
				true,
				true,
				false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.8f);

		return chart;
	}

}
