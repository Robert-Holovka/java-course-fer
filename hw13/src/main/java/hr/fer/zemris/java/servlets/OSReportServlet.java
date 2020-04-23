package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.common.Utils;

/**
 * Returns report on a Operation System usage. Report is returned as an image
 * that contains pie chart.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "osReport", urlPatterns = { "/reportImage" })
public class OSReportServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);

		JFreeChart chart = Utils.generatePieChart(dataset, "OS usage:");
		ChartUtils.writeChartAsPNG(resp.getOutputStream(), chart, 600, 400);
	}

}
