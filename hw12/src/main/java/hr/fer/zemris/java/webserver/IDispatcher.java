package hr.fer.zemris.java.webserver;

/**
 * Dispatch request based on its URL.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IDispatcher {
	/**
	 * Dispatch request based on its URL.
	 * 
	 * @param urlPath Request URL
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}