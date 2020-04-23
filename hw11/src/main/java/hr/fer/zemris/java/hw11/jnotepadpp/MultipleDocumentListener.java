package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Actions on observable changes from {@link MultipleDocumentModel}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface MultipleDocumentListener {

	/**
	 * Callback function that fires when current {@link SingleDocumentModel} has
	 * been changed.
	 * 
	 * @param previousModel Model before change
	 * @param currentModel  Model after change
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel,
			SingleDocumentModel currentModel);

	/**
	 * Callback function that fires when {@link SingleDocumentModel} has been added.
	 * 
	 * @param model SingleDocumentModel
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Callback function that fires when {@link SingleDocumentModel} has been
	 * removed.
	 * 
	 * @param model SingleDocumentModel
	 */
	void documentRemoved(SingleDocumentModel model);
}