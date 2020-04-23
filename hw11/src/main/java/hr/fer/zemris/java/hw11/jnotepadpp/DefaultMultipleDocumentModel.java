package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 * Represents model that can store informations about multiple documents.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Current(visible) document.
	 */
	private SingleDocumentModel currentDoc;
	/**
	 * List of all opened documents.
	 */
	private List<SingleDocumentModel> openedDocs = new ArrayList<>();
	/**
	 * List of listeners who are subscribed to this model.
	 */
	private Set<MultipleDocumentListener> listeners = new CopyOnWriteArraySet<>();

	/**
	 * Red disk icon location.
	 */
	private static final String UNSAVED_ICON = "icons/disk_red.png";
	/**
	 * Green disk icon location.
	 */
	private static final String SAVED_ICON = "icons/disk_green.png";

	/**
	 * Constructs new instance of this class.
	 */
	public DefaultMultipleDocumentModel() {
		this.addChangeListener(e -> {
			SingleDocumentModel previous = currentDoc;
			int index = getSelectedIndex();
			if (index != -1) {
				currentDoc = openedDocs.get(index);
				notifyChanged(previous, currentDoc);
			}
		});
		createNewDocument();
	}

	/**
	 * Concrete Iterator implementation for this model. It iterates over collection
	 * of SingleDocumentModel instances and returns next one on demand.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private class DocumentModelIterator implements Iterator<SingleDocumentModel> {
		/**
		 * Current iterator position.
		 */
		private int position;

		@Override
		public boolean hasNext() {
			return position != openedDocs.size();
		}

		@Override
		public SingleDocumentModel next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return openedDocs.get(position++);
		}
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentModelIterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		DefaultSingleDocumentModel doc = new DefaultSingleDocumentModel(null, null, false);
		setNewDocument(doc);
		return doc;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDoc;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if given path is a {@code null} reference
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		SingleDocumentModel testModel = new DefaultSingleDocumentModel(path, null, false);

		if (openedDocs.contains(testModel)) {
			// File already exist, switch to it
			setSelectedIndex(openedDocs.indexOf(testModel));
		} else {
			String text = null;
			try {
				text = Files.readString(path);
			} catch (IOException e) {
				text = "Error reading file.";
			}
			DefaultSingleDocumentModel doc = new DefaultSingleDocumentModel(path, text, false);
			setNewDocument(doc);
		}

		return currentDoc;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		String text = model.getTextComponent().getText();

		// Check if file with newPath is already opened
		if (model.getFilePath() != newPath) {
			SingleDocumentModel testModel = new DefaultSingleDocumentModel(newPath, null, false);
			if (openedDocs.contains(testModel)) {
				throw new IllegalArgumentException("Provided file is already opened. Choose another location.");
			}
		}

		try (OutputStream os = new BufferedOutputStream(Files.newOutputStream(newPath))) {
			os.write(text.getBytes());
			model.setModified(false);
			model.setFilePath(newPath);
		} catch (IOException e) {
		}
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		model.removeSingleDocumentListener(singleDocumentListener);
		remove(openedDocs.indexOf(model));
		openedDocs.remove(model);

		if (getNumberOfDocuments() == 0) {
			createNewDocument();
		}

		int last = getNumberOfDocuments() - 1;
		setSelectedIndex(last);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return openedDocs.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index >= openedDocs.size()) {
			throw new IllegalArgumentException("Index out of bounds.");
		}
		return openedDocs.get(index);
	}

	/**
	 * Binds new {@link DefaultSingleDocumentModel} with this class.
	 * 
	 * @param newDoc DefaultSingleDocumentModel
	 */
	private void setNewDocument(DefaultSingleDocumentModel newDoc) {
		openedDocs.add(newDoc);

		add(new JScrollPane(newDoc.getTextComponent()));
		newDoc.addSingleDocumentListener(singleDocumentListener);

		int index = getNumberOfDocuments() - 1;
		setSelectedIndex(index);
		updateTabInfo(newDoc);
		setIcon(getDocument(index), newDoc.isModified() ? UNSAVED_ICON : SAVED_ICON);
	}

	/**
	 * Listener for changes from {@link SingleDocumentModel}.
	 */
	private final SingleDocumentListener singleDocumentListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {
			int index = openedDocs.indexOf(model);
			if (index < 0) {
				return;
			}
			setIcon(model, model.isModified() ? UNSAVED_ICON : SAVED_ICON);
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			int index = openedDocs.indexOf(model);
			if (index < 0) {
				return;
			}
			updateTabInfo(model);
			notifyChanged(model, model);
		}
	};

	/**
	 * Updates tab info that contains given document.
	 * 
	 * @param document Document contained inside tab
	 */
	private void updateTabInfo(SingleDocumentModel document) {
		int index = openedDocs.indexOf(document);
		Path path = document.getFilePath();

		String title = (path == null) ? "unnamed" : path.getFileName().toString();
		setTitleAt(index, title);

		String toolTip = (path == null) ? "File not saved yet." : path.toString();
		setToolTipTextAt(index, toolTip);
	}

	/**
	 * Notifies subscribers that current model has been updated.
	 * 
	 * @param previous SingleDocumentModel
	 * @param current  SingleDocumentModel
	 */
	private void notifyChanged(SingleDocumentModel previous, SingleDocumentModel current) {
		for (var listener : listeners) {
			listener.currentDocumentChanged(previous, current);
		}
	}

	/**
	 * Sets given icon to the specified document.
	 * 
	 * @param model SingleDocumentModel
	 * @param path  Icon path
	 */
	private void setIcon(SingleDocumentModel model, String path) {
		int index = openedDocs.indexOf(model);
		int dim = getFontMetrics(getFont()).getAscent();
		setIconAt(index, IconLoader.loadImage(this, path, dim, dim));
	}

	public List<SingleDocumentModel> getDocuments() {
		return new ArrayList<SingleDocumentModel>(openedDocs);
	}

}
