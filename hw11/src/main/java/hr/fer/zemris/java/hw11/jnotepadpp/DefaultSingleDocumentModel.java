package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Concrete {@link SingleDocumentModel} implementation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * This document location on the disk.
	 */
	private Path path;
	/**
	 * This document text component.
	 */
	private JTextArea textArea;
	/**
	 * Flag which tells whether this version of the document differs from the one
	 * save on the disk.
	 */
	private boolean modified;
	/**
	 * Subscribers.
	 */
	private Set<SingleDocumentListener> listeners = new CopyOnWriteArraySet<>();

	/**
	 * Types of notifications that this class can produce.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private enum NOTIFICATION_TYPE {
		PATH_UPDATED,
		FILE_MODIFIED
	}

	/**
	 * Constructs new instance of this model.
	 * 
	 * @param path     Document location on the disk
	 * @param text     Text to be saved into this document
	 * @param modified Flag which tells whether this version of the document differs
	 *                 from the one save on the disk.
	 */
	public DefaultSingleDocumentModel(Path path, String text, boolean modified) {
		this.path = path;
		textArea = new JTextArea();
		if (text != null) {
			textArea.setText(text);
		}
		textArea.getDocument().addDocumentListener(modificationListener);
		this.modified = modified;
	}

	/**
	 * Listener on this document changes.
	 */
	private final DocumentListener modificationListener = new DocumentListener() {

		@Override
		public void removeUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			setModified(true);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setModified(true);
		}
	};

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if given path is a {@code null} reference
	 */
	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.path = path;
		notifyListeners(NOTIFICATION_TYPE.PATH_UPDATED);
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified != modified) {
			this.modified = modified;
			notifyListeners(NOTIFICATION_TYPE.FILE_MODIFIED);
		}
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all subscribes about this document changes.
	 * 
	 * @param notification Specifies which notification will be sent
	 */
	private void notifyListeners(NOTIFICATION_TYPE notification) {
		for (var listener : listeners) {
			if (notification == NOTIFICATION_TYPE.FILE_MODIFIED) {
				listener.documentModifyStatusUpdated(this);
			} else {
				listener.documentFilePathUpdated(this);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DefaultSingleDocumentModel))
			return false;
		DefaultSingleDocumentModel other = (DefaultSingleDocumentModel) obj;
		if (path == null || other.path == null) {
			return false;
		}
		return Objects.equals(path, other.path);
	}

}
