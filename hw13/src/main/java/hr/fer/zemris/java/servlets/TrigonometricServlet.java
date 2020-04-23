package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.common.Utils;
import hr.fer.zemris.java.models.Trigonometric;

/**
 * Calculates sine and cosine for each number in a specified range. If range is
 * not properly specified, default values are used instead.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
@WebServlet(name = "trigonometric", urlPatterns = { "/trigonometric" })
public class TrigonometricServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String argA = req.getParameter("a");
		String argB = req.getParameter("b");
		int a = 0;
		int b = 360;

		if (argA != null && Utils.isInteger(argA)) {
			a = Integer.parseInt(argA);
		}
		if (argB != null && Utils.isInteger(argB)) {
			b = Integer.parseInt(argB);
		}
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		if (b > (a + 720)) {
			b = a + 720;
		}

		List<Trigonometric> trigonometrics = new LinkedList<>();
		for (int i = a; i <= b; i++) {
			trigonometrics.add(new Trigonometric(i, Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i))));
		}
		req.setAttribute("trigonometrics", trigonometrics);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

}
