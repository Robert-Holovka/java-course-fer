package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

public class CalcLayoutTest {

	@Test
	public void testPreferredSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testPreferredSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setPreferredSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setPreferredSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getPreferredSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testMinimumSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setMinimumSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setMinimumSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getMinimumSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testMinimumSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setMinimumSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setMinimumSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getMinimumSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testMaximumSize() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setMaximumSize(new Dimension(10, 30));
		JLabel l2 = new JLabel("");
		l2.setMaximumSize(new Dimension(20, 15));
		p.add(l1, new RCPosition(2, 2));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getMaximumSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void testPreferredMaximumSize2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		l1.setMaximumSize(new Dimension(108, 15));
		JLabel l2 = new JLabel("");
		l2.setMaximumSize(new Dimension(16, 30));
		p.add(l1, new RCPosition(1, 1));
		p.add(l2, new RCPosition(3, 3));
		Dimension dim = p.getMaximumSize();
		assertEquals(152, dim.width);
		assertEquals(158, dim.height);
	}

	@Test
	public void rowOutsideOfRange() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(0, 1)));
	}

	@Test
	public void rowOutsideOfRange2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(6, 1)));
	}

	@Test
	public void columnOutsideOfRange() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 0)));
	}

	@Test
	public void columnOutsideOfRange2() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 8)));
	}

	@Test
	public void unavailablePosition() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		assertThrows(CalcLayoutException.class, () -> p.add(l1, new RCPosition(1, 2)));
	}

	@Test
	public void moreComponentsSamePosition() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		p.add(l1, new RCPosition(1, 6));
		assertThrows(CalcLayoutException.class, () -> p.add(new JLabel(), new RCPosition(1, 5)));
	}

	@Test
	public void invalidConstraintType() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		p.add(l1, new RCPosition(1, 6));
		assertThrows(UnsupportedOperationException.class, () -> p.add(l1, new Object()));
	}

	@Test
	public void invalidStringFormat() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		p.add(l1, new RCPosition(1, 6));
		assertThrows(UnsupportedOperationException.class, () -> p.add(l1, "2"));
	}

	@Test
	public void validStringFormat() {
		JPanel p = new JPanel(new CalcLayout(2));
		JLabel l1 = new JLabel("");
		p.add(l1, new RCPosition(1, 6));
		// Exception changed because it is invalid position!!!
		assertThrows(CalcLayoutException.class, () -> p.add(l1, "1,4"));
	}

}
