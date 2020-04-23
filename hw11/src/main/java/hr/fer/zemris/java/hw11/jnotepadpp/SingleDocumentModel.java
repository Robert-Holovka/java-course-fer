package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Actions which every single document model must implement.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface SingleDocumentModel {
	/**
	 * @return Document text component.
	 */
	JTextArea getTextComponent();

	/**
	 * @return Document location on the disk.
	 */
	Path getFilePath();

	/**
	 * Sets new document disk location.
	 * 
	 * @param path location
	 */
	void setFilePath(Path path);

	/**
	 * @return modification flag
	 */
	boolean isModified();

	/**
	 * Sets modification flag.
	 * 
	 * @param modified Tells whether current document version differs from a lastly
	 *                 saved one.
	 */
	void setModified(boolean modified);

	/**
	 * Subscribes new listener on this document.
	 * 
	 * @param l subscriber
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener from this class.
	 * 
	 * @param l subscriber to be removed
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}