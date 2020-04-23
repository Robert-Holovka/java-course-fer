package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.model.PollOption;

/**
 * Returns excel file to the client. Excel file contains statistics about poll
 * results. If provided id for a poll is invalid then it redirects to home page
 * instead.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "voteXLS", urlPatterns = { "/servleti/glasanje-xls" })
public class ResultXLSServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pollID = req.getParameter("pollID");
		if (pollID == null || pollID.isBlank() || !Utils.isInteger(pollID)) {
			resp.sendRedirect("./index.html");
			return;
		}

		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"vote-results.xls\"");

		List<PollOption> results = DAOProvider.getDao().getPollOptions(Long.parseLong(pollID));
		// Sort by number of votes, then by a name
		Comparator<PollOption> comp = Comparator
				.comparing(PollOption::getVotesCount)
				.reversed()
				.thenComparing(PollOption::getOptionTitle);
		results.sort(comp);

		Workbook xls = generateExcelFile(results);

		xls.write(resp.getOutputStream());
		xls.close();
	}

	/**
	 * Generates excel file from a given results. 1st column represents band name
	 * and second column number of votes that band got.
	 * 
	 * @param results ResultInfo
	 * @return {@link Workbook} constructed excel file
	 */
	private Workbook generateExcelFile(List<PollOption> results) {
		Workbook xls = new HSSFWorkbook();
		Sheet sheet = xls.createSheet("Vote results");

		// Cell styles
		CellStyle style = xls.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);

		// Headers
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Izbor:");
		header.createCell(1).setCellValue("Broj glasova:");

		// Data
		int rowNum = 1;
		for (PollOption result : results) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(result.getOptionTitle());
			row.createCell(1).setCellValue(result.getVotesCount());
		}

		// Resize
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		return xls;
	}

}
