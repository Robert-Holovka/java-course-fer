package hr.fer.zemris.java.servlets.voting;

import java.io.IOException;
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

import hr.fer.zemris.java.common.Database;
import hr.fer.zemris.java.models.ResultInfo;

/**
 * Returns excel file to the client. Excel file contains statistics about vote
 * results.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "voteXLS", urlPatterns = { "/glasanje-xls" })
public class GlasanjeXLSServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"vote-results.xls\"");
		List<ResultInfo> results = Database.getResults();

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
	private Workbook generateExcelFile(List<ResultInfo> results) {
		Workbook xls = new HSSFWorkbook();
		Sheet sheet = xls.createSheet("Vote results");

		// Cell styles
		CellStyle style = xls.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		sheet.setDefaultColumnStyle(0, style);
		sheet.setDefaultColumnStyle(1, style);

		// Headers
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue("Bend:");
		header.createCell(1).setCellValue("Broj glasova:");

		// Data
		int rowNum = 1;
		for (ResultInfo result : results) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(result.getName());
			row.createCell(1).setCellValue(result.getVotes());
		}

		// Resize
		sheet.autoSizeColumn(0);
		sheet.autoSizeColumn(1);

		return xls;
	}

}
