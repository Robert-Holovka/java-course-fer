package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import hr.fer.zemris.java.common.Utils;

/**
 * Calculates n-th power for each number from a specified range. If range is not
 * valid appropriate error is shown, otherwise this class returns excel file
 * with powers of a numbers from defined range. Valid range is [-100, 100].
 * Valid power number n is in range [1, 5]. Each power iteration is displayed on
 * a separate sheet.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "power", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Fetch parameters
		String parA = req.getParameter("a");
		String parB = req.getParameter("b");
		String parN = req.getParameter("n");

		// Validate parameters
		String errorMessage = "";
		errorMessage += Utils.validateIntegerParam(parA, "a", -100, 100);
		errorMessage += Utils.validateIntegerParam(parB, "b", -100, 100);
		errorMessage += Utils.validateIntegerParam(parN, "n", 1, 5);
		if (Utils.isInteger(parA) && Utils.isInteger(parB)) {
			int a = Integer.parseInt(parA);
			int b = Integer.parseInt(parB);
			if (a > b) {
				errorMessage += "Parameter 'a' is greater than parameter 'b'.\t";
			}
		}
		if (!errorMessage.isBlank()) {
			req.setAttribute("error", errorMessage);
			req.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req, resp);
			return;
		}

		// Generate excel file
		int a = Integer.parseInt(parA);
		int b = Integer.parseInt(parB);
		int n = Integer.parseInt(parN);
		Workbook xls = generateXLS(n, a, b);

		// Send back excel file
		resp.setContentType("application/octet-stream");
		resp.setHeader("Content-Disposition", "attachment;filename=\"tablica.xls\"");
		xls.write(resp.getOutputStream());
		xls.close();
	}

	/**
	 * Generates excel file structure from a given parameters.
	 * 
	 * @param n Number of sheets, also upper limit for a power of the number
	 * @param a From
	 * @param b To
	 * @return Excel file structure
	 */
	private Workbook generateXLS(int n, int a, int b) {
		Workbook xls = new HSSFWorkbook();

		for (int i = 1; i <= n; i++) {
			Sheet sheet = xls.createSheet("n^" + i);
			int rowNum = 0;
			for (int j = a; j <= b; j++) {
				Row row = sheet.createRow(rowNum++);
				row.createCell(0).setCellValue(j);
				row.createCell(1).setCellValue(Math.pow(j, i));
			}
			sheet.autoSizeColumn(0);
			sheet.autoSizeColumn(1);
		}
		return xls;
	}

}
