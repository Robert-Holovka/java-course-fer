package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Represents single record of a student.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StudentRecord {

	/**
	 * Identifier for the student.
	 */
	private String jmbag;
	/**
	 * Last name of the student.
	 */
	private String lastName;
	/**
	 * First name of the student.
	 */
	private String firstName;
	/**
	 * Final grade for some course.
	 */
	private int finalGrade;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param jmbag      String
	 * @param lastName   String
	 * @param firstName  String
	 * @param finalGrade int
	 * @throws NullPointerException if given identifier, last or first name is a
	 *                              {@code null} reference.
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		Objects.requireNonNull(jmbag);
		Objects.requireNonNull(lastName);
		Objects.requireNonNull(firstName);

		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns identifier for this student.
	 * 
	 * @return String
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns last name of this student.
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns first name of this student.
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns final grade of some course that this student achieved.
	 * 
	 * @return int
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}

}
