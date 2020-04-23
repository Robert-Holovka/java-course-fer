package hr.fer.zemris.java.custom.collections;

/**
 * Represents model of an object capable of performing some operation on the
 * other object.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
@FunctionalInterface
public interface Processor {

	/**
	 * Method capable of performing some operations on other objects.
	 * 
	 * @param value Object to be processed
	 */
	void process(Object value);

}
