package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.common.Database;
import hr.fer.zemris.java.common.Utils;
import hr.fer.zemris.java.models.ResultInfo;

/**
 * Returns PNG image contains pie chart. Pie chart represents vote results.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "voteReport", urlPatterns = { "/glasanje-grafika" })
public class GlasanjeGrafikaServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");
		DefaultPieDataset dataset = loadDataset(Database.getResults());

		JFreeChart chart = Utils.generatePieChart(dataset, "Vote results");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 400, 400);
	}

	/**
	 * Prepares data set for a {@link JFreeChart}.
	 * 
	 * @param results Results to be inserted in {@link DefaultPieDataset}
	 * @return DefaultPieDataset
	 */
	private DefaultPieDataset loadDataset(List<ResultInfo> results) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (ResultInfo r : results) {
			dataset.setValue(r.getName(), r.getVotes());
		}
		return dataset;
	}

}
