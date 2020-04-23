package hr.fer.zemris.java.hw17.jvdraw.document.models;

import java.nio.file.Path;

/**
 * Function interface which defines action to be executed when path from the
 * registered {@link DocumentModel} has changed.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@FunctionalInterface
public interface DocumentModelListener {

	/**
	 * On path change action.
	 * 
	 * @param newPath new file path
	 */
	void filePathChanged(Path newPath);
}
