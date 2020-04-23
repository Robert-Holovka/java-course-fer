package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class Vector2DTest {

	@Test
	public void createVector() {
		var v = new Vector2D(1, 2);
		assertEquals(v, new Vector2D(1, 2));
	}
	
	@Test
	public void getX() {
		var v = new Vector2D(2, 3);
		assertEquals(2, v.getX());
	}
	
	@Test
	public void getY() {
		var v = new Vector2D(2, 3);
		assertEquals(3, v.getY());
	}
	
	@Test
	public void translate() {
		var v = new Vector2D(3, 3);
		v.translate(new Vector2D(1, 1));
		
		assertEquals(v, new Vector2D(4, 4));
	}
	
	@Test
	public void getTranslated() {
		var v1 = new Vector2D(3, 3);
		var v2 = v1.translated(new Vector2D(1, 1));
		

		assertEquals(v1, new Vector2D(3, 3));
		assertEquals(v2, new Vector2D(4, 4));
	}
	
	@Test
	public void scale() {
		var v = new Vector2D(4, 4);
		v.scale(0.5);
		
		assertEquals(v, new Vector2D(2, 2));
	}
	
	@Test
	public void getScaled() {
		var v1 = new Vector2D(4, 4);
		var v2 = v1.scaled(0.5);
		
		assertEquals(v1, new Vector2D(4, 4));
		assertEquals(v2, new Vector2D(2, 2));
	}
	
	@Test
	public void rotate() {
		var v = new Vector2D(5, 0);
		v.rotate(Math.PI);
		
		assertEquals(v, new Vector2D(-5, 0));
	}
	
	@Test
	public void getRotated() {
		var v1 = new Vector2D(4, 4);
		var v2 = v1.rotated(Math.PI);
		
		assertEquals(v1, new Vector2D(4, 4));
		assertEquals(v2, new Vector2D(-4, -4));
	}
	
	@Test
	public void copy() {
		var v1 = new Vector2D(10, 10);
		var v2 = v1.copy();
		
		assertEquals(v2, new Vector2D(10, 10));
	}
	
	@Test
	public void equalVectors() {
		var v1 = new Vector2D(10, 10);
		var v2 = new Vector2D(10.000001, 10.000001);
		
		assertTrue(v1.equals(v2));
	}
	
	@Test
	public void notEqualVectors() {
		var v1 = new Vector2D(10, 10);
		var v2 = new Vector2D(10.0001, 10.0001);
		
		assertFalse(v1.equals(v2));
	}
}
