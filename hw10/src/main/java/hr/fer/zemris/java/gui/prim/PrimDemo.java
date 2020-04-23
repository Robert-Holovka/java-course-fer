package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * Sample program which demonstrates usage of {@link PrimListModel}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
@SuppressWarnings("serial")
public class PrimDemo extends JFrame {

	/**
	 * Default constructor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setSize(300, 300);
		initGUI();
	}

	/**
	 * Initializes graphical user interface of this component.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		PrimListModel model = new PrimListModel();
		JList<Long> list1 = new JList<>(model);
		JList<Long> list2 = new JList<>(model);

		cp.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 2));
		centerPanel.add(new JScrollPane(list1));
		centerPanel.add(new JScrollPane(list2));

		JButton next = new JButton("sljedeći");
		next.addActionListener(e -> {
			model.next();
		});

		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(next, BorderLayout.PAGE_END);
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}
}
