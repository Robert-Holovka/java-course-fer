package hr.fer.zemris.java.hw14;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.common.Utils;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

/**
 * Filter which intercepts every requests that starts with '/servleti' and
 * registers database connection in {@link SQLConnectionProvider} before request
 * is executed. After request execution it unregisters that connection from
 * provider.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@WebFilter(filterName = "csf", urlPatterns = { "/servleti/*" })
public class ConnectionSetterFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		// Get connection form pool
		DataSource ds = (DataSource) request.getServletContext().getAttribute(Initialization.POOL_TAG);
		Connection con = Utils.fetchConnection(ds);
		if (con == null) {
			System.out.println("Database is not available");
		}

		// Register connection
		SQLConnectionProvider.setConnection(con);

		// Process request
		try {
			chain.doFilter(request, response);
		} finally {
			// Unregister and close connection
			SQLConnectionProvider.setConnection(null);
			try {
				con.close();
			} catch (SQLException ignorable) {
			}
		}
	}

}