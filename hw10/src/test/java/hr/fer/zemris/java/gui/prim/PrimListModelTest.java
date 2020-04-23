package hr.fer.zemris.java.gui.prim;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.swing.JList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrimListModelTest {

	private PrimListModel model;

	@BeforeEach
	public void init() {
		model = new PrimListModel();
	}

	@Test
	public void createListModel() {
		try {
			PrimListModel plm = new PrimListModel();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void size() {
		model.next();
		model.next();
		model.next();
		assertEquals(4, model.getSize());
	}

	@Test
	public void getElementAt() {
		model.next();
		model.next();
		model.next();
		assertEquals(2, model.getElementAt(1));
	}

	@Test
	public void next() {
		JList<Long> list = new JList<>(model);
		model.next();
		model.next();
		model.next();
		model.next();
		assertEquals(5, list.getModel().getSize());
	}
}
