package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Demonstrates usage of {@link CalcLayout}.
 * 
 * @author RObert Holovka
 * @version 1.0
 *
 */
public class DemoFrame2 extends JFrame {
	/**
	 * Default constructor.
	 */
	public DemoFrame2() {
		setTitle("demo2");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Initializes graphical user interface of this component.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	/**
	 * Label generator.
	 * 
	 * @param text
	 * @return
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame2().setVisible(true);
		});
	}
}