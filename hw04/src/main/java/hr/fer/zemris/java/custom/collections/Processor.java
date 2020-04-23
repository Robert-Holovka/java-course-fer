package hr.fer.zemris.java.custom.collections;

/**
 * Represents model of an object capable of performing some operation on the
 * other object.
 * 
 * @author Robert Holovka
 * @version 1.2
 * 
 * @param <T> Type of a value that processor works on
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * Method capable of performing some operations on other objects.
	 * 
	 * @param value Object to be processed
	 */
	void process(T value);

}
