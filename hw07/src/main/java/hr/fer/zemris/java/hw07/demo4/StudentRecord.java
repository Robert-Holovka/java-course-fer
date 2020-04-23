package hr.fer.zemris.java.hw07.demo4;

import java.util.Objects;

/**
 * Represents one record of a student. It contains informations about student
 * and about the class he enrolled into.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class StudentRecord {
	/**
	 * Identifier of a student.
	 */
	private String jmbag;
	/**
	 * Student last name.
	 */
	private String lastName;
	/**
	 * Student first name.
	 */
	private String firstName;
	/**
	 * Points achieved at mid-term exam.
	 */
	private double MI;
	/**
	 * Points achieved at final exam.
	 */
	private double ZI;
	/**
	 * Points achieved at laboratory exams.
	 */
	private double LAB;
	/**
	 * Final grade for this course.
	 */
	private int finalGrade;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param jmbag      Identifier of a student
	 * @param lastName   Student last name
	 * @param firstName  Student first name
	 * @param MI         Points achieved on a mid-term exam
	 * @param ZI         Points achieved on final exam
	 * @param LAB        Points achieved on laboratory exams
	 * @param finalGrade Final grade of some course
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double MI, double ZI, double LAB,
			int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.MI = MI;
		this.ZI = ZI;
		this.LAB = LAB;
		this.finalGrade = finalGrade;
	}

	/**
	 * Returns student identifier.
	 * 
	 * @return String
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student last name.
	 * 
	 * @return String
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student first name.
	 * 
	 * @return String
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns points achieved on a mid-term exam .
	 * 
	 * @return double
	 */
	public double getMI() {
		return MI;
	}

	/**
	 * Returns points achieved on a final exam .
	 * 
	 * @return double
	 */
	public double getZI() {
		return ZI;
	}

	/**
	 * Returns points achieved on a laboratory exams.
	 * 
	 * @return double
	 */
	public double getLAB() {
		return LAB;
	}

	/**
	 * Returns final grade.
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

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" + MI + "\t" + ZI + "\t" + LAB + "\t" + finalGrade;
	}

}
