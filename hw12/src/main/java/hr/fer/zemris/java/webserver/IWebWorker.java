package hr.fer.zemris.java.webserver;

/**
 * Interface toward any object that can process current request.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface IWebWorker {
	/**
	 * Processes given request.
	 * 
	 * @param context Request context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;
}