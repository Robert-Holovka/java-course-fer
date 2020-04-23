package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import hr.fer.zemris.java.custom.collections.SimpleHashtable;

/**
 * Shows usage of a class {@link SimpleHashtable}.
 * 
 * @author Robo
 * @version 1.0
 */
public class HashTableDemo {

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line.
	 */
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		fillTable(examMarks);

		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4

		System.out.println();
		System.out.println("To string:");
		System.out.println(examMarks.toString());
		System.out.println();

		System.out.println("4.c prvi");
		// create collection:s
		examMarks = new SimpleHashtable<>(2);
		// fill data:
		fillTable(examMarks);
		System.out.println("size " + examMarks.size());
		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
		}

		System.out.println();
		System.out.println();
		System.out.println("4.c drugi");

		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
						pair2.getValue());
			}
		}

		System.out.println();
		System.out.println();
		System.out.println("Ukloni ocjenu za Ivanu");
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
			}
		}
		System.out.println(examMarks.toString());
		System.out.println();
		System.out.println();

		examMarks = new SimpleHashtable<>(2);
		// fill data:
		fillTable(examMarks);

		System.out.println("Uzastponi poziv remove() nad Ivanom treba bacit IllegalStateException");
		iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				try {
					iter.remove();
					System.out.println("Nije uopce bacena iznimka");
				} catch (IllegalStateException e) {
					System.out.println("Ispravna iznimka");
				} catch (Exception e) {
					System.out.println("Kriva iznimka");
				}

			}
		}

		examMarks = new SimpleHashtable<>(2);
		iter = examMarks.iterator();
		fillTable(examMarks);
		System.out.println();
		System.out.println();

		System.out.println("Sljedeći kod bacio bi ConcurrentModificationException jer se uklanjanje poziva “izvana");

		try {
			while (iter.hasNext()) {
				SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
				if (pair.getKey().equals("Ivana")) {
					System.out.println("tu sam");
				}
			}
		} catch (ConcurrentModificationException e) {
			System.out.println("Ispravna iznimka");
		} catch (Exception e) {
			System.out.println("Kriva iznimka");
		}

		System.out.println();
		System.out.println();
		System.out.println("Sljedeći kod trebao bi ispisati sve parove i po završetku ostaviti kolekciju praznom");
		examMarks = new SimpleHashtable<>(2);
		fillTable(examMarks);
		iter = examMarks.iterator();

		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());

		System.out.printf("%nkraj");
	}

	/**
	 * Adds a few values to a given table.
	 * 
	 * @param table
	 */
	private static void fillTable(SimpleHashtable<String, Integer> table) {
		table.put("Ivana", 2);
		table.put("Ante", 2);
		table.put("Jasna", 2);
		table.put("Kristina", 5);
		table.put("Ivana", 5); // overwrites old grade for Ivana
	}
}
