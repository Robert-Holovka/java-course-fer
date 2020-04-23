package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.CaseOperations;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeLanguage;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFile;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.DefaultAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitProgram;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.IAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFile;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.RemoveDuplicates;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFile;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAs;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.Sort;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.Statistics;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Text editor with various functionalities.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JNotepadPP extends JFrame {

	/**
	 * Storage for multiple instances of {@link SingleDocumentModel}.
	 */
	private DefaultMultipleDocumentModel model;
	/*
	 * i18n provider.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Label for the Caret position(line).
	 */
	private JLabel ln;
	/**
	 * Label for the Caret position(column).
	 */
	private JLabel col;
	/**
	 * Label for the length of the current file.
	 */
	private JLabel len;
	/**
	 * Label for the length of the selected text.
	 */
	private JLabel sel;
	/**
	 * Displays time.
	 */
	private JLabel clock;

	/**
	 * Set of operations which performs on a selected text.
	 */
	private HashSet<JComponent> selectableItems = new HashSet<>();

	/**
	 * Constructs new instance of this text editor.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(this, LocalizationProvider.getInstance());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(20, 20);
		setSize(800, 600);
		initGUI();
		addWindowListener(windowAdapter);
	}

	/**
	 * Window closing listener
	 */
	private final WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			var result = new ExitProgram(JNotepadPP.this, model);
			result.execute();
			if (result.shouldClose()) {
				dispose();
			}
		};
	};

	/**
	 * Initializes graphical user interface.
	 */
	private void initGUI() {
		// Initialize model
		model = new DefaultMultipleDocumentModel();
		model.addMultipleDocumentListener(mdl);

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		setJMenuBar(initMenuBar(cp));

		cp.add(initToolBar(cp), BorderLayout.PAGE_START);

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		jp.add(model, BorderLayout.CENTER);
		cp.add(jp, BorderLayout.CENTER);
		initStatusBar(jp);

		mdl.currentDocumentChanged(null, model.getCurrentDocument());
	}

	/**
	 * Initializes {@link JToolBar} of this editor.
	 * 
	 * @param cp Container for JToolbar
	 * @return JToolBar instance
	 */
	private JToolBar initToolBar(Container cp) {
		JToolBar tb = new JToolBar();

		tb.add(Box.createGlue());
		createToolbarButton(tb, "new", "new_desc", () -> model.createNewDocument());
		createToolbarButton(tb, "open", "open_desc", new OpenFile(this, model));

		tb.add(Box.createGlue());
		createToolbarButton(tb, "save", "save_desc", new SaveFile(this, model));
		createToolbarButton(tb, "save_as", "save_as_desc", new SaveFileAs(this, model));

		tb.add(Box.createGlue());
		JButton copy = createToolbarButton(tb, "copy", "copy_desc",
				() -> new DefaultEditorKit.CopyAction().actionPerformed(null));
		JButton cut = createToolbarButton(tb, "cut", "cut_desc",
				() -> new DefaultEditorKit.CutAction().actionPerformed(null));
		createToolbarButton(tb, "paste", "paste_desc",
				() -> new DefaultEditorKit.PasteAction().actionPerformed(null));

		tb.add(Box.createGlue());
		createToolbarButton(tb, "stats", "stats_desc", new Statistics(this, model));

		tb.add(Box.createGlue());
		createToolbarButton(tb, "close", "close_desc", new CloseFile(this, model));
		createToolbarButton(tb, "exit", "exit_desc", () -> windowAdapter.windowClosing(null));
		tb.add(Box.createGlue());

		selectableItems.add(copy);
		selectableItems.add(cut);

		return tb;
	}

	/**
	 * Initializes {@link JMenuBar} of this editor.
	 * 
	 * @param cp Container for JMenuBar
	 * @return JMenuBar instance
	 */
	private JMenuBar initMenuBar(Container cp) {
		JMenuBar mb = new JMenuBar();

		// Languages menu
		JMenu languages = new JMenu(new DefaultAction("lang", null, flp));
		createMenuItem(languages, "en", null, KeyEvent.VK_E, KeyStroke.getKeyStroke("alt 1"), new ChangeLanguage("en"));
		createMenuItem(languages, "hr", null, KeyEvent.VK_R, KeyStroke.getKeyStroke("alt 2"), new ChangeLanguage("hr"));
		createMenuItem(languages, "de", null, KeyEvent.VK_M, KeyStroke.getKeyStroke("alt 3"), new ChangeLanguage("de"));

		// Info menu
		JMenu info = new JMenu(new DefaultAction("info", null, flp));
		createMenuItem(info, "stats", "stats_desc", KeyEvent.VK_S, KeyStroke.getKeyStroke("ctrl T"),
				new Statistics(this, model));

		mb.add(initFileMenu());
		mb.add(initEditMenu());
		mb.add(initToolsMenu());
		mb.add(languages);
		mb.add(info);

		return mb;
	}

	/**
	 * Initializes file menu.
	 * 
	 * @return instance of file menu
	 */
	private JMenu initFileMenu() {
		JMenu file = new JMenu(new DefaultAction("file", null, flp));

		createMenuItem(file, "new", "new_desc", KeyEvent.VK_N, KeyStroke.getKeyStroke("ctrl N"),
				() -> model.createNewDocument());
		createMenuItem(file, "open", "open_desc", KeyEvent.VK_O, KeyStroke.getKeyStroke("ctrl O"),
				new OpenFile(this, model));

		file.addSeparator();
		createMenuItem(file, "save", "save_desc", KeyEvent.VK_S, KeyStroke.getKeyStroke("ctrl S"),
				new SaveFile(this, model));
		createMenuItem(file, "save_as", "save_as_desc", KeyEvent.VK_A, KeyStroke.getKeyStroke("ctrl shift S"),
				new SaveFileAs(this, model));

		file.addSeparator();
		createMenuItem(file, "close", "close_desc", KeyEvent.VK_C, KeyStroke.getKeyStroke("ctrl Q"),
				new CloseFile(this, model));
		createMenuItem(file, "exit", "exit_desc", KeyEvent.VK_E, KeyStroke.getKeyStroke("ctrl E"),
				() -> windowAdapter.windowClosing(null));

		return file;
	}

	/**
	 * Initializes edit menu.
	 * 
	 * @return new instance of edit menu
	 */
	private JMenu initEditMenu() {
		JMenu edit = new JMenu(new DefaultAction("edit", null, flp));

		JMenuItem copy = createMenuItem(edit, "copy", "copy_desc", KeyEvent.VK_C, KeyStroke.getKeyStroke("alt C"),
				() -> new DefaultEditorKit.CopyAction().actionPerformed(null));
		JMenuItem cut = createMenuItem(edit, "cut", "cut_desc", KeyEvent.VK_U, KeyStroke.getKeyStroke("alt X"),
				() -> new DefaultEditorKit.CutAction().actionPerformed(null));
		createMenuItem(edit, "paste", "paste_desc", KeyEvent.VK_P, KeyStroke.getKeyStroke("alt V"),
				() -> new DefaultEditorKit.PasteAction().actionPerformed(null));

		selectableItems.add(copy);
		selectableItems.add(cut);
		return edit;
	}

	/**
	 * Initializes tools menu.
	 * 
	 * @return new instance of tools menu
	 */
	private JMenu initToolsMenu() {
		JMenu tools = new JMenu(new DefaultAction("tools", null, flp));

		// Submenu for case change operations
		JMenu caseing = new JMenu(new DefaultAction("change_case", "change_case_desc", flp));
		JMenuItem uppercase = createMenuItem(caseing, "uppercase", "uppercase_desc", KeyEvent.VK_U,
				KeyStroke.getKeyStroke("ctrl U"),
				new CaseOperations(model, CaseOperations.UPPERCASE));
		JMenuItem lowercase = createMenuItem(caseing, "lowercase", "lowercase_desc", KeyEvent.VK_L,
				KeyStroke.getKeyStroke("ctrl L"),
				new CaseOperations(model, CaseOperations.LOWERCASE));
		JMenuItem invert = createMenuItem(caseing, "invert", "invert_desc", KeyEvent.VK_I,
				KeyStroke.getKeyStroke("ctrl I"),
				new CaseOperations(model, CaseOperations.INVERT));
		tools.add(caseing);

		// Submenu for sorting lines
		JMenu sort = new JMenu(new DefaultAction("sort", null, flp));
		JMenuItem ascending = createMenuItem(sort, "ascending", null, KeyEvent.VK_A, KeyStroke.getKeyStroke("alt A"),
				new Sort(model, flp.getCurrentLanguage(), Sort.ASCENDING));
		JMenuItem descending = createMenuItem(sort, "descending", null, KeyEvent.VK_D, KeyStroke.getKeyStroke("alt D"),
				new Sort(model, flp.getCurrentLanguage(), Sort.DESCENDING));
		tools.add(sort);

		// Remove duplicates action
		tools.addSeparator();
		JMenuItem unique = createMenuItem(tools, "unique", "unique_desc", KeyEvent.VK_Q,
				KeyStroke.getKeyStroke("alt Q"),
				new RemoveDuplicates(model));

		selectableItems.add(uppercase);
		selectableItems.add(lowercase);
		selectableItems.add(invert);
		selectableItems.add(ascending);
		selectableItems.add(descending);
		selectableItems.add(unique);

		return tools;
	}

	/**
	 * Initializes status bar of this editor.
	 * 
	 * @param cp Container for status bar
	 */
	private void initStatusBar(Container cp) {
		JPanel statusBar = new JPanel();
		statusBar.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		len = new JLabel();
		ln = new JLabel();
		col = new JLabel();
		sel = new JLabel();
		clock = new JLabel(new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(new Date()),
				SwingConstants.RIGHT);

		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
		statusBar.add(Box.createRigidArea(new Dimension(5, 0)));

		statusBar.add(len);
		statusBar.add(Box.createGlue());

		statusBar.add(ln);
		statusBar.add(Box.createRigidArea(new Dimension(10, 0)));

		statusBar.add(col);
		statusBar.add(Box.createRigidArea(new Dimension(10, 0)));

		statusBar.add(sel);
		statusBar.add(Box.createGlue());

		statusBar.add(clock);
		statusBar.add(Box.createRigidArea(new Dimension(5, 0)));

		Timer t = new Timer(1000, updateDateTime);
		t.start();
		cp.add(statusBar, BorderLayout.PAGE_END);
	}

	/**
	 * Creates new menu item, sets its action and adds it to the parent container.
	 * 
	 * @param parent      Parent of new JMenuItem
	 * @param name        JMenuItem name
	 * @param desc        JMenuItem description
	 * @param mnemonic    JMenuItem mnemonic key
	 * @param accelerator JMenuItem accelerator key combination
	 * @param action      JMenuItem action
	 * @return JMenuItem instances
	 */
	private JMenuItem createMenuItem(JMenu parent, String name, String desc, int mnemonic, KeyStroke accelerator,
			IAction action) {
		JMenuItem item = new JMenuItem();

		item.setAction(new LocalizableAction(name, desc, flp) {
			{
				if (mnemonic != -1) {
					this.putValue(Action.MNEMONIC_KEY, mnemonic);
				}
				if (accelerator != null) {
					this.putValue(Action.ACCELERATOR_KEY, accelerator);
				}
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				action.execute();
			}
		});

		parent.add(item);
		return item;
	}

	/**
	 * Creates new {@link JToolBar} button, sets its action and adds it to the
	 * parent container.
	 * 
	 * @param parent JButton parent
	 * @param name   JButton name
	 * @param desc   JButton description
	 * @param action JButton action
	 * @return JButton instance
	 */
	private JButton createToolbarButton(JToolBar parent, String name, String desc, IAction action) {
		JButton button = new JButton();

		button.setAction(new LocalizableAction(name, desc, flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				action.execute();
			}
		});

		parent.add(button);
		return button;
	}

	/**
	 * Action for updating date and time.
	 */
	private final ActionListener updateDateTime = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			clock.setText(new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss").format(new Date()));
		}
	};

	/**
	 * Observes {@link #model} changes.
	 */
	private final MultipleDocumentListener mdl = new MultipleDocumentListener() {

		@Override
		public void documentRemoved(SingleDocumentModel model) {
			JTextArea textArea = model.getTextComponent();
			textArea.getDocument().removeDocumentListener(documentListener);
			textArea.removeCaretListener(caretListener);
		}

		@Override
		public void documentAdded(SingleDocumentModel model) {
			JTextArea textArea = model.getTextComponent();
			textArea.getDocument().addDocumentListener(documentListener);
			textArea.addCaretListener(caretListener);

		}

		@Override
		public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
			// Notify connected observers
			if (currentModel != null) {
				updateTitle(currentModel);
				JTextArea textArea = currentModel.getTextComponent();
				updateStatusBar(textArea);
				
				Caret caret = textArea.getCaret();
				int selected = Math.abs(caret.getDot() - caret.getMark());
				activateSelectableItems(selected > 0);
			}
			documentListener.changedUpdate(null);

			if (previousModel == currentModel) {
				return;
			}
			if (previousModel != null) {
				documentRemoved(previousModel);
			}
			if (currentModel != null) {
				documentAdded(currentModel);
			}
		}

	};

	/**
	 * Updates this window title.
	 * 
	 * @param model Current document model
	 */
	private void updateTitle(SingleDocumentModel model) {
		String jNotepad = "- JNotepad++";
		setTitle(model.getFilePath() == null ? "(unnamed) " + jNotepad : model.getFilePath() + " " + jNotepad);
	}

	/**
	 * Observes current document changes.
	 */
	private final DocumentListener documentListener = new DocumentListener() {

		@Override
		public void changedUpdate(DocumentEvent e) {
			update();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			update();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			update();
		}

		public void update() {
			len.setText("length: " + model.getCurrentDocument().getTextComponent().getText().length());
		}
	};

	/**
	 * Observes current document Caret changes.
	 */
	private final CaretListener caretListener = new CaretListener() {

		@Override
		public void caretUpdate(CaretEvent c) {
			SingleDocumentModel currentDoc = model.getCurrentDocument();
			JTextArea textArea = currentDoc.getTextComponent();

			updateStatusBar(textArea);
			int selected = Math.abs(c.getDot() - c.getMark());
			activateSelectableItems(selected > 0);
		}
	};

	/**
	 * Enables/disables selectable components.
	 * 
	 * @param activate Activation flag
	 */
	private void activateSelectableItems(boolean activate) {
		for (var item : selectableItems) {
			item.setEnabled(activate);
		}
	}

	/**
	 * Updates this editor status bar.
	 * 
	 * @param textArea Text component of currently focused text area
	 */
	private void updateStatusBar(JTextArea textArea) {
		Caret caret = textArea.getCaret();
		sel.setText("Sel: " + Math.abs(caret.getMark() - caret.getDot()));

		try {
			int position = textArea.getCaretPosition();
			int line = textArea.getLineOfOffset(position);

			ln.setText("Ln: " + (line + 1));
			col.setText("Col: " + (position - textArea.getLineStartOffset(line) + 1));
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
