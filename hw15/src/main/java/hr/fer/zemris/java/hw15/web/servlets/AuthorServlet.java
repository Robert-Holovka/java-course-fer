package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * 
 * @author Robo
 *
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1);
		String[] pathInfoData = pathInfo.split("/");

		// servleti/author/nick
		if (pathInfoData.length == 1) {
			String nick = pathInfoData[0];
			BlogUser user = DAOProvider.getDAO().getUserByNickname(nick);
			if (user == null) {
				// greska
				return;
			}
			req.setAttribute("entries", user.getEntries());
			req.setAttribute("nick", nick);
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
		}

		if (pathInfoData.length == 2) {
			DAO dao = DAOProvider.getDAO();

			// servleti/author/nick/new
			if (pathInfoData[1].endsWith("new")) {
				req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
				return;
			}

			// servleti/author/nick/eid
			BlogEntry entry = dao.getBlogEntry(Long.parseLong(pathInfoData[1]));
			if (entry == null) {
				// greska
				return;
			}
			req.setAttribute("entry", entry);
			req.setAttribute("nick", pathInfoData[0]);
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			return;
		}
		if (pathInfoData.length == 3) {
			// servleti/author/nick/edit
			if (pathInfoData[1].endsWith("edit")) {
				DAO dao = DAOProvider.getDAO();
				BlogEntry entry = dao.getBlogEntry(Long.parseLong(pathInfoData[2]));
				req.setAttribute("entry", entry);
				req.getRequestDispatcher("/WEB-INF/pages/newEntry.jsp").forward(req, resp);
				return;
			} else {
				// error
			}
		}

		// inace error

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo().substring(1);
		// Ovo su komentari
		if (pathInfo.endsWith("comment")) {
			String[] pathInfoData = pathInfo.split("/");
			Long entryId = Long.parseLong(pathInfoData[1]);
			BlogComment comment = new BlogComment();
			comment.setMessage(req.getParameter("message"));
			comment.setUsersEMail(req.getParameter("email"));
			comment.setPostedOn(new Date());

			DAO dao = DAOProvider.getDAO();
			BlogEntry entry = dao.getBlogEntry(entryId);
			entry.getComments().add(comment);
			comment.setBlogEntry(entry);
			dao.createComment(comment);
			resp.sendRedirect(".");
			return;
		}

		if(pathInfo.endsWith("new")) {
			// Create Blog Entry
			BlogUser user = DAOProvider.getDAO()
					.getUserByNickname((String) req.getSession().getAttribute("current.user.nick"));
			String title = req.getParameter("title");
			String text = req.getParameter("text");
			BlogEntry entry = new BlogEntry();
			entry.setText(text);
			entry.setTitle(title);
			entry.setLastModifiedAt(new Date());
			entry.setCreatedAt(new Date());
			entry.setCreator(user);
			user.getEntries().add(entry);

			DAOProvider.getDAO().createBlogEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + req.getSession().getAttribute("current.user.nick"));
		} else {
			String[] pathInfoData = pathInfo.split("/");
			
			if(!pathInfoData[1].equals("edit")) {
				// errorr
				return;
			}
			BlogUser user = DAOProvider.getDAO()
					.getUserByNickname((String) req.getSession().getAttribute("current.user.nick"));
			String title = req.getParameter("title");
			String text = req.getParameter("text");
			BlogEntry entry = new BlogEntry();
			entry.setText(text);
			entry.setTitle(title);
			entry.setLastModifiedAt(new Date());
			entry.setCreatedAt(new Date());
			entry.setCreator(user);
			entry.setId(Long.parseLong(pathInfoData[2]));
			user.getEntries().add(entry);

			DAOProvider.getDAO().updateBlogEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + req.getSession().getAttribute("current.user.nick"));
		}
	}

}