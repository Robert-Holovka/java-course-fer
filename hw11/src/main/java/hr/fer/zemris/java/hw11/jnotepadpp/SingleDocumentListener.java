package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Actions on observable changes from {@link SingleDocumentModel}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface SingleDocumentListener {
	/**
	 * Callback function that fires when {@link SingleDocumentModel} modification
	 * flag changes.
	 * 
	 * @param model SingleDocumentModel
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Callback function that fires when {@link SingleDocumentModel} path changes.
	 * 
	 * @param model SingleDocumentModel
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}