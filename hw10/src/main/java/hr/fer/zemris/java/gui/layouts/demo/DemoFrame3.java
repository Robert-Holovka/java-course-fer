package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
public class DemoFrame3 extends JFrame {
	/**
	 * Default constructor.
	 */
	public DemoFrame3() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		setSize(212, 200);
	}

	/**
	 * Initializes graphical user interface of this component.
	 */
	private void initGUI() {
		Container cp = getContentPane();

		JPanel p = new JPanel(new CalcLayout(3));
		p.add(generate("x"), "1,1");
		p.add(generate("y"), "2,3");
		p.add(generate("z"), "2,7");
		p.add(generate("w"), "4,2");
		p.add(generate("a"), "4,5");
		p.add(generate("b"), "4,7");

		cp.add(p);
	}

	/**
	 * Label generator.
	 * 
	 * @param text
	 * @return
	 */
	private JLabel generate(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.BLUE);
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
			new DemoFrame3().setVisible(true);
		});
	}
}