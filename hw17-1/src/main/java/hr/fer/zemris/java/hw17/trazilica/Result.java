package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Represents similarity result between document and a query.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Result implements Comparable<Result> {

	/**
	 * Document path.
	 */
	private Path path;
	/**
	 * Similarity between document and query.
	 */
	private double similarity;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param path       Document path
	 * @param similarity Similarity between document and a query
	 */
	public Result(Path path, double similarity) {
		this.path = path;
		this.similarity = similarity;
	}

	/**
	 * @return document path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * @return similarity between document and a query
	 */
	public double getSimilarity() {
		return similarity;
	}

	@Override
	public String toString() {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		otherSymbols.setGroupingSeparator(',');
		DecimalFormat df = new DecimalFormat("0.0000", otherSymbols);
		return "(" + df.format(similarity) + ") " + path.toString();
	}

	@Override
	public int compareTo(Result o) {
		if (this.getSimilarity() > o.getSimilarity()) {
			return -1;
		}
		if (this.getSimilarity() < o.getSimilarity()) {
			return 1;
		}
		return 0;
	}

}
