package hr.fer.zemris.java.hw17.trazilica;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw17.shell.ShellIOException;
import hr.fer.zemris.java.hw17.shell.Utils;

/**
 * Storage for instances of Document class. Provides methods for manipulating
 * with those and comparing similarities between them and queries.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class DocumentDatabase {

	/**
	 * Path to the ignorable words
	 */
	private static final String IGNORABLE_WORDS_FILE = "hrvatski_stoprijeci.txt";
	/**
	 * Initial size of a vocabulary.
	 */
	private static final int ESTIMATED_VOCABULARY_SIZE = 10000;
	/**
	 * Initial number of ignorable words.
	 */
	private static final int ESTIMATED_NUMBER_OF_IGNORABLE_WORDS = 1000;

	/**
	 * Set of ignorable words.
	 */
	private Set<String> ignorableWords = new HashSet<>(ESTIMATED_NUMBER_OF_IGNORABLE_WORDS);
	/**
	 * Set of words contained in all known documents, ignorable words are excluded.
	 */
	private Set<String> vocabulary = new HashSet<>(ESTIMATED_VOCABULARY_SIZE);
	/**
	 * List of all known documents.
	 */
	private List<Document> documents = new LinkedList<>();
	/**
	 * For every word in a vocabulary tracks in how many documents it is present.
	 */
	private Map<String, Integer> wordFrequency = new HashMap<>(ESTIMATED_VOCABULARY_SIZE);
	/**
	 * Inverse document frequency component.
	 */
	private Map<String, Double> idf = new HashMap<>(ESTIMATED_VOCABULARY_SIZE);

	/**
	 * Constructs new instance of this base and initializes it.
	 * 
	 * @param documentsPath Path to the documents
	 */
	public DocumentDatabase(Path documentsPath) {
		try {
			loadIgnorableWords();
		} catch (IOException e) {
			throw new ShellIOException("Could not initialize ignorable words.");
		}
		try {
			initVocabulary(documentsPath);
			initVectors(documentsPath);
		} catch (IOException e) {
			throw new ShellIOException("Could not read documents.");
		}
	}

	/**
	 * Loads {@link #ignorableWords} with content from
	 * {@link #IGNORABLE_WORDS_FILE}.
	 * 
	 * @throws IOException If {@link #IGNORABLE_WORDS_FILE} does not exist
	 */
	private void loadIgnorableWords() throws IOException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		InputStream is = classloader.getResourceAsStream(IGNORABLE_WORDS_FILE);
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

		String line = null;
		while ((line = reader.readLine()) != null) {
			ignorableWords.add(line.trim().toLowerCase());
		}
	}

	/**
	 * Scans for files in provided documents path and loads all words from those
	 * files. These words forms the vocabulary, ignorable words are excluded.
	 * 
	 * @param documentsPath Path to the files
	 * @throws IOException If given documents path is not valid
	 */
	private void initVocabulary(Path documentsPath) throws IOException {
		Files.list(documentsPath).forEach((p) -> {
			if (Files.isReadable(p)) {
				try {
					List<String> lines = Files.readAllLines(p);
					lines.forEach((line) -> {
						List<String> words = Utils.extractWords(line);
						words.removeAll(ignorableWords);
						vocabulary.addAll(words);
					});
				} catch (IOException e) {
				}
			}
		});
	}

	/**
	 * Initializes {@link #idf} vector. Also, loads all documents and calculates
	 * their tfIdf vectors.
	 * 
	 * @param documentsPath Path to the documents
	 * @throws IOException If provided path is invalid
	 */
	private void initVectors(Path documentsPath) throws IOException {
		// Initialize tf vectors in documents
		Files.list(documentsPath).forEach((p) -> {
			if (Files.isReadable(p)) {
				try {
					// Register document
					Document doc = new Document(p, vocabulary);
					documents.add(doc);

					// Calculate document tf vector
					List<String> lines = Files.readAllLines(p);
					lines.forEach((line) -> {
						List<String> words = Utils.extractWords(line);
						words.removeIf((word) -> !vocabulary.contains(word));
						words.forEach((word) -> {
							doc.getTfVector().merge(word, 1, Integer::sum);
						});
					});

					// Update word frequency info
					doc.getTfVector().keySet().forEach((word) -> {
						wordFrequency.merge(word, 1, Integer::sum);
					});
				} catch (IOException ignorable) {
				}
			}
		});

		// Calculate common idf vector
		int numOfDocs = documents.size();
		vocabulary.forEach((word) -> {
			int frequency = convertToZeroIfNull(wordFrequency.get(word));
			idf.put(word, Math.log((double) numOfDocs / frequency));
		});

		// Calculate tfIdf for documents
		documents.forEach((doc) -> {
			for (String word : doc.getTfVector().keySet()) {
				int tf = convertToZeroIfNull(doc.getTfVector().get(word));
				doc.getTfIdfVector().put(word, (double) tf * idf.get(word));
			}
		});
	}

	/**
	 * @return number of words in the vocabulary
	 */
	public long getVocabularySize() {
		return vocabulary.size();
	}

	/**
	 * @return {@link #vocabulary}
	 */
	public Collection<?> getVocabulary() {
		return vocabulary;
	}

	/**
	 * Calculates similarities between {@link #documents} and a given query and
	 * returns that similarities as a List of instances of class Result.
	 * 
	 * @param words query
	 * @return List of {@link Result}
	 */
	public List<Result> calculateSimilarity(ArrayList<String> words) {
		List<Result> results = new LinkedList<Result>();

		// Calculate tfIdf for query (treat is as a new document)
		HashMap<String, Integer> tfQuery = new HashMap<>(words.size());
		words.forEach((word) -> tfQuery.merge(word, 1, Integer::sum));
		HashMap<String, Double> tfIdfQuery = new HashMap<>(words.size());
		words.forEach((word) -> {
			int tf = tfQuery.get(word);
			tfIdfQuery.put(word, tf * idf.get(word));
		});

		// Compare query with other documents
		documents.forEach((doc) -> {
			double similarity = 0.0;
			Map<String, Double> docTfIdfVector = doc.getTfIdfVector();

			double numerator = 0.0;
			for (var word : words) {
				numerator += convertToZeroIfNull(docTfIdfVector.get(word)) * tfIdfQuery.get(word);
			}

			similarity = numerator / (vectorNorm(docTfIdfVector) * vectorNorm(tfIdfQuery));
			Path path = doc.getDocumentPath();
			results.add(new Result(path, similarity));
		});

		results.sort(null);
		var filteredResults = results.stream().filter((r) -> r.getSimilarity() > 0.0).limit(10)
				.collect(Collectors.toList());
		return filteredResults;
	}

	/**
	 * Converts value to the primitive zero if its value is a {@code null}
	 * reference.
	 * 
	 * @param value Reference to the value
	 * @return primitive value
	 */
	private double convertToZeroIfNull(Double value) {
		return (value == null) ? 0 : value;
	}

	/**
	 * Converts value to the primitive zero if its value is a {@code null}
	 * reference.
	 * 
	 * @param value Reference to the value
	 * @return primitive value
	 */
	private int convertToZeroIfNull(Integer value) {
		return (value == null) ? 0 : value;
	}

	/**
	 * Calculates norm of a given vector.
	 * 
	 * @param tfIdfVector vector
	 * @return Vector norm
	 */
	private double vectorNorm(Map<String, Double> tfIdfVector) {
		double sum = 0.0;
		for (var key : tfIdfVector.keySet()) {
			double val = tfIdfVector.get(key);
			sum += val * val;
		}
		return Math.sqrt(sum);
	}
}
