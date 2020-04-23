package hr.fer.zemris.java.hw17.trazilica;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Model for a single document who contains vectors used for comparing
 * similarity.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Document {
	/**
	 * Document path.
	 */
	private Path documentPath;
	/**
	 * Term frequency vector.
	 */
	private Map<String, Integer> tfVector;
	/**
	 * Term frequency - inverse document frequency vector
	 */
	private Map<String, Double> tfIdfVector;

	/**
	 * Constructs new instance of this class.
	 * 
	 * @param documentPath Document path
	 * @param vocabulary   Available vocabulary
	 */
	public Document(Path documentPath, Set<String> vocabulary) {
		this.documentPath = documentPath;
		this.tfVector = new HashMap<>(vocabulary.size() / 10);
		this.tfIdfVector = new HashMap<>();
	}

	/**
	 * @return document path
	 */
	public Path getDocumentPath() {
		return documentPath;
	}

	/**
	 * @return Term frequency vector
	 */
	public Map<String, Integer> getTfVector() {
		return tfVector;
	}

	/**
	 * @return Term frequency - inverse document frequency vector
	 */
	public Map<String, Double> getTfIdfVector() {
		return tfIdfVector;
	}

}
