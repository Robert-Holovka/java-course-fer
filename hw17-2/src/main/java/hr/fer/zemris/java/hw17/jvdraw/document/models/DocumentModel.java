package hr.fer.zemris.java.hw17.jvdraw.document.models;

import java.nio.file.Path;

/**
 * Represents model for a single document. Notifies its listeners when document
 * path has changed.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public interface DocumentModel {

	/**
	 * Changes this document file path.
	 * 
	 * @param file New file path
	 */
	void setCurrentFile(Path file);

	/**
	 * @return Current file path
	 */
	Path getCurrentFile();

	/**
	 * Register new listener on this component.
	 * 
	 * @param l observer
	 */
	void addDocumentModelListener(DocumentModelListener l);

	/**
	 * Removes listener from this component.
	 * 
	 * @param l observer
	 */
	void removeDocumentModelListener(DocumentModelListener l);

	/**
	 * Notifies all observers that document path has changed.
	 */
	void notifyListeners();
}
