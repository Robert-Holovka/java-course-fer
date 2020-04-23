package hr.fer.zemris.java.hw17.jvdraw.document.models;

import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Concrete implementation of a {@link DocumentModel}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DocumentModelImpl implements DocumentModel {

	/**
	 * Storage for current file path.
	 */
	private Path currentFile;
	/**
	 * Storage for observers.
	 */
	private Set<DocumentModelListener> listeners;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param initialFile file Path
	 */
	public DocumentModelImpl(Path initialFile) {
		currentFile = initialFile;
		listeners = new CopyOnWriteArraySet<>();
	}

	@Override
	public void setCurrentFile(Path file) {
		currentFile = file;
		notifyListeners();
	}

	@Override
	public Path getCurrentFile() {
		return currentFile;
	}

	@Override
	public void addDocumentModelListener(DocumentModelListener l) {
		listeners.add(l);
	}

	@Override
	public void removeDocumentModelListener(DocumentModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void notifyListeners() {
		for (var listener : listeners) {
			listener.filePathChanged(currentFile);
		}
	}

}
