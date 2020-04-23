package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Actions which every multiple document model must implement.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	/**
	 * Creates new instance of {@link SingleDocumentModel}.
	 * 
	 * @return new {@link SingleDocumentModel} instance
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * @return {@link SingleDocumentModel} that has focus.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads file from the disk.
	 * 
	 * @param path File location on the disk
	 * @return Loaded file represented by a {@link SingleDocumentModel}
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves file to the disk.
	 * 
	 * @param model   Model of a file which will be saved
	 * @param newPath Location on the disk where file will be saved
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes given document.
	 * 
	 * @param model File represented by {@link SingleDocumentModel}
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Subscribes new listener to this class.
	 * 
	 * @param l subscriber
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes given subscriber from this class.
	 * 
	 * @param l subscriber
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * @return Number of documents that this model currently stores.
	 */
	int getNumberOfDocuments();

	/**
	 * Return document positioned on a given index.
	 * 
	 * @param index Index of the {@link SingleDocumentModel}
	 * @return SingleDocumentModel
	 */
	SingleDocumentModel getDocument(int index);
}