package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import hr.fer.zemris.java.hw17.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.IAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.OpenFileAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveFileAction;
import hr.fer.zemris.java.hw17.jvdraw.actions.SaveFileAsAction;
import hr.fer.zemris.java.hw17.jvdraw.components.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.components.JCustomLabel;
import hr.fer.zemris.java.hw17.jvdraw.components.JCustomList;
import hr.fer.zemris.java.hw17.jvdraw.components.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModel;
import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.document.models.DocumentModelListener;
import hr.fer.zemris.java.hw17.jvdraw.drawing.models.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.tools.EmptyCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.tools.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.utils.IconLoader;

/**
 * Simple paint tool which allows to draw some geometrical objects such as
 * lines, empty & filled circles. Image can be exported to multiple formats:
 * .png, .gif, .jpg. Image can also be saved/loaded to .jvd files.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class JVDraw extends JFrame implements DocumentModelListener {

	/**
	 * Initial program width.
	 */
	private static final int DEFAULT_WINDOW_WIDTH = 1366;
	/**
	 * Initial program height.
	 */
	private static final int DEFAULT_WINDOW_HEIGHT = 768;
	/**
	 * Initial program width/height offset from the top-left corner.
	 */
	private static final int DEFAULT_WINDOW_OFFSET = 50;
	/**
	 * Default padding size.
	 */
	private static final int DEFAULT_PADDING = 5;
	/**
	 * Default foreground color.
	 */
	private static final Color DEFAULT_FOREGROUND_COLOR = new Color(255, 0, 0);
	/**
	 * Default background color.
	 */
	private static final Color DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 255);

	/**
	 * Default icon width/height.
	 */
	private static final int DEFAULT_ICON_DIMENSION = 15;

	/**
	 * Foreground color provider.
	 */
	private JColorArea foregroundColorArea;
	/**
	 * Background color provider.
	 */
	private JColorArea backgroundColorArea;
	/**
	 * Object used for drawing.
	 */
	private JDrawingCanvas canvas;
	/**
	 * Model which contains all rendered geometrical objects.
	 */
	private DrawingModelImpl drawingModel;
	/**
	 * Model which keeps track what is currently loaded file.
	 */
	private DocumentModel documentModel;

	/**
	 * Constructs new instance of this class.
	 */
	public JVDraw() {
		setTitle("JVDraw - Untitled");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(DEFAULT_WINDOW_OFFSET, DEFAULT_WINDOW_OFFSET);
		setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		drawingModel = new DrawingModelImpl();
		documentModel = new DocumentModelImpl(null);
		documentModel.addDocumentModelListener(this);

		initGUI();
		addWindowListener(windowAdapter);
	}

	/**
	 * Window closing listener.
	 */
	private final WindowAdapter windowAdapter = new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			var result = new ExitAction(JVDraw.this, drawingModel, documentModel);
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
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		// JColorAreas
		foregroundColorArea = new JColorArea(DEFAULT_FOREGROUND_COLOR);
		backgroundColorArea = new JColorArea(DEFAULT_BACKGROUND_COLOR);

		setJMenuBar(initMenuBar());

		canvas = new JDrawingCanvas(new LineTool(drawingModel, foregroundColorArea), drawingModel);
		cp.add(canvas, BorderLayout.CENTER);

		cp.add(new JScrollPane(initList()), BorderLayout.LINE_END);
		cp.add(initToolBar(), BorderLayout.PAGE_START);
		cp.add(initColorInfoLabel(cp), BorderLayout.PAGE_END);

	}

	/**
	 * Initializes {@link JCustomList}.
	 * 
	 * @return {@link JCustomList}
	 */
	private Component initList() {
		JCustomList list = new JCustomList(drawingModel, canvas);
		return list;
	}

	/**
	 * Initializes menu bar.
	 * 
	 * @return Initialized JMenuBar
	 */
	private JMenuBar initMenuBar() {
		JMenuBar jmb = new JMenuBar();
		jmb.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JMenu file = initFileMenu();

		jmb.add(file);

		return jmb;
	}

	/**
	 * Initializes file menu and its actions.
	 * 
	 * @return Initialized file menu
	 */
	private JMenu initFileMenu() {
		JMenu fileMenu = new JMenu("File");

		createMenuItem(fileMenu, "Open", "Opens .jvd file and loads its content.", KeyEvent.VK_O,
				KeyStroke.getKeyStroke("ctrl O"), new OpenFileAction(this, drawingModel, documentModel));

		fileMenu.addSeparator();
		createMenuItem(fileMenu, "Save", "Saves this document as .jvd file.", KeyEvent.VK_S,
				KeyStroke.getKeyStroke("ctrl S"),
				new SaveFileAction(this, drawingModel, documentModel));
		createMenuItem(fileMenu, "Save As", "Saves document to a new location as .jvd file.", KeyEvent.VK_A,
				KeyStroke.getKeyStroke("ctrl shift S"),
				new SaveFileAsAction(this, drawingModel, documentModel));

		createMenuItem(fileMenu, "Export", "Exports this document as an image type.", KeyEvent.VK_X,
				KeyStroke.getKeyStroke("ctrl X"),
				new ExportAction(this, drawingModel));

		fileMenu.addSeparator();
		createMenuItem(fileMenu, "Exit", "Close the program.", KeyEvent.VK_E, KeyStroke.getKeyStroke("ctrl E"),
				() -> windowAdapter.windowClosing(null));
		return fileMenu;
	}

	/**
	 * Initializes label which displays informations about currently selected
	 * background and foreground colors.
	 * 
	 * @param cp Parent container
	 * @return JPanel Panel where this label is stored
	 */
	private JPanel initColorInfoLabel(Container cp) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
		jp.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		jp.add(Box.createGlue());
		jp.add(new JCustomLabel(foregroundColorArea, backgroundColorArea));
		jp.add(Box.createGlue());

		return jp;
	}

	/**
	 * Initializes this program toolBar.
	 * 
	 * @return JToolBar initialized toolBar
	 */
	private JToolBar initToolBar() {
		JToolBar tb = new JToolBar();
		tb.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.BLACK),
				new EmptyBorder(DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING, DEFAULT_PADDING)));

		tb.addSeparator();
		tb.add(foregroundColorArea);
		tb.addSeparator();
		tb.add(backgroundColorArea);

		tb.addSeparator(new Dimension(DEFAULT_PADDING * 4, 0));
		// Components
		ButtonGroup bg = new ButtonGroup();
		addJToggleButton(bg, tb, "icons/line.png", true,
				(e) -> canvas.setCurrentState(new LineTool(drawingModel, foregroundColorArea)));
		tb.addSeparator();
		addJToggleButton(bg, tb, "icons/empty-circle.png", false,
				(e) -> canvas.setCurrentState(new EmptyCircleTool(drawingModel, foregroundColorArea)));
		tb.addSeparator();
		addJToggleButton(bg, tb, "icons/filled-circle.png", true,
				(e) -> canvas
						.setCurrentState(new FilledCircleTool(drawingModel, foregroundColorArea, backgroundColorArea)));

		return tb;
	}

	/**
	 * Adds new {@link JToggleButton} to the passed group and a container. Buttons
	 * in the same group are mutually exclusive.
	 * 
	 * @param group     Button group
	 * @param container Button container
	 * @param iconPath  Path to the button icon
	 * @param selected  Is button selected
	 * @param l         Button action
	 */
	private void addJToggleButton(ButtonGroup group, JToolBar container, String iconPath, boolean selected,
			ActionListener l) {

		JToggleButton jtb = new JToggleButton();
		jtb.setSelected(selected);
		jtb.setIcon(IconLoader.loadImage(this, iconPath, DEFAULT_ICON_DIMENSION, DEFAULT_ICON_DIMENSION));
		group.add(jtb);
		container.add(jtb);
		jtb.addActionListener(l);
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
		item.setAction(new AbstractAction() {
			{
				this.putValue(Action.NAME, name);
				this.putValue(Action.SHORT_DESCRIPTION, desc);
				this.putValue(Action.MNEMONIC_KEY, mnemonic);
				this.putValue(Action.ACCELERATOR_KEY, accelerator);
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
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line, not used here
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JVDraw().setVisible(true));
	}

	@Override
	public void filePathChanged(Path newPath) {
		this.setTitle("JVDraw - " + newPath.toString());
	}
}
