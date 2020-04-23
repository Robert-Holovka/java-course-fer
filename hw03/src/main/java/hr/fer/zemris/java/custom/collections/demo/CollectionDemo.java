package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;

/**
 * Program that demonstrates usage of the package
 * {@link hr.fer.zemris.java.custom.collections}.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CollectionDemo {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line.
	 */
	public static void main(String[] args) {

		/*
		 * Ispis bi trebao biti:
		 * 
		 * 1. true, Ivo, true, Ana, true, Jasna, false, exception
		 * 
		 * 2. 5 x true, Ivo, true, true, Ana, 3 x true, Jasna, false, false
		 * 
		 * 3. Ivo, Ana, Jasna, exception
		 * 
		 * 4. Ivo, Ana, Ivo, Jasna, Ana
		 * 
		 * 5. Ivo, Ana, Ivo, Jasmina, Štefanija
		 * 
		 * 6. Ivo, Ana, exception
		 * 
		 * 7. Ivo, Ana, exception
		 * 
		 * 8. Ana, Jasna.
		 * 
		 * 9. 12, 2, 4, 6
		 * 
		 * 10. Ivana, Jasna, Ivana, Jasna
		 * 
		 */

		System.out.printf("Podzadatak 2.1:%n%n");
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException e) {
			System.out.println("exception");
		}

		System.out.printf("%nPodzadatak 2.2%n%n");

		col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement());

		System.out.printf("%nPodzadatak 2.3%n%n");
		col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (NoSuchElementException e) {
			System.out.println("exception");
		}

		System.out.printf("%nPodzadatak 2.4%n%n");
		col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter1 = col.createElementsGetter();
		ElementsGetter getter2 = col.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());

		System.out.printf("%nPodzadatak 2.5%n%n");
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		getter1 = col1.createElementsGetter();
		getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter2.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());
		System.out.println("Jedan element: " + getter3.getNextElement());

		System.out.printf("%n Podzadatak 3.1%n%n");
		col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (ConcurrentModificationException e) {
			System.out.println("Concurrent...");
		}

		System.out.printf("%n Podzadatak 3.2%n%n");
		col = new LinkedListIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement());
		System.out.println("Jedan element: " + getter.getNextElement());
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement());
		} catch (ConcurrentModificationException e) {
			System.out.println("Concurrent...");
		}

		System.out.printf("%nPodzadatak 4.%n%n");
		col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println);

		System.out.printf("%nPodzadatak 5.%n%n");
		col1 = new LinkedListIndexedCollection();
		col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println);

		System.out.printf("%nPodzadatak 6.%n%n");
		List col11 = new ArrayIndexedCollection();
		List col21 = new LinkedListIndexedCollection();
		col11.add("Ivana");
		col21.add("Jasna");
		List col31 = col11;
		List col41 = col21;
		col11.get(0);
		col21.get(0);
		col31.get(0); // neće se prevesti! Razumijete li zašto?
		col41.get(0); // neće se prevesti! Razumijete li zašto?
		col11.forEach(System.out::println); // Ivana
		col21.forEach(System.out::println); // Jasna
		col31.forEach(System.out::println); // Ivana
		col41.forEach(System.out::println); // Jasna
	}
}
