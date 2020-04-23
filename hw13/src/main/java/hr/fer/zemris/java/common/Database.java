package hr.fer.zemris.java.common;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.models.Band;
import hr.fer.zemris.java.models.Result;
import hr.fer.zemris.java.models.ResultInfo;

/**
 * Emulates simple database that stores bands and voting results. Provides
 * methods for fetching mentioned entities on various conditions.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Database {

	/**
	 * Location of the file that contains list of bands.
	 */
	public static String bandsFile;
	/**
	 * Location of the file that contains vote results.
	 */
	public static String resultsFile;
	/**
	 * Map of bands. Key is band identifier.
	 */
	private static Map<String, Band> bands;

	/**
	 * @return list of bands
	 */
	public static List<Band> getBands() {
		if (bands == null) {
			loadBands();
		}
		List<Band> bandList = new LinkedList<>(bands.values());
		bandList.sort(Comparator.comparing(b -> b.getID()));
		return bandList;
	}

	/**
	 * @return list of results
	 */
	public static List<ResultInfo> getResults() {
		if (bands == null) {
			loadBands();
		}

		Map<String, Result> resultsMap = loadResults();
		LinkedList<ResultInfo> results = new LinkedList<>();

		for (Result result : resultsMap.values()) {
			String bandName = bands.get(result.getID()).getName();
			results.add(new ResultInfo(bandName, result.getVotes()));
		}

		if (results.isEmpty()) {
			for (Band band : getBands()) {
				results.add(new ResultInfo(band.getName(), 0));
			}
		}

		// Sort by number of votes
		Comparator<ResultInfo> comp = Comparator
				.comparing(ResultInfo::getVotes)
				.reversed()
				.thenComparing(ResultInfo::getName);
		results.sort(comp);

		return results;
	}

	/**
	 * Fills {@linkplain #bands} map with a band list from the
	 * {@linkplain #bandsFile}.
	 */
	private static void loadBands() {
		bands = new HashMap<>();
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(bandsFile));
		} catch (IOException e) {
			return;
		}

		for (String line : lines) {
			String[] data = line.split("\\s+");
			if (data.length < 3) {
				System.out.println("Invalid line: " + line);
				continue;
			}
			String id = data[0];
			String song = data[data.length - 1];

			String name = "";
			for (int i = 1; i < data.length - 1; i++) {
				name += (i != 1) ? " " + data[i] : data[i];
			}
			Band band = new Band(id, name, song);
			bands.put(id, band);
		}
	}

	/**
	 * @return returns Map filled with data from the {@linkplain #resultsFile}
	 */
	private static Map<String, Result> loadResults() {
		Map<String, Result> results = new HashMap<>();
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(resultsFile));
		} catch (IOException e) {
			return results;
		}

		for (String line : lines) {
			String[] data = line.split("\\s+");
			String id = data[0];
			results.put(id, new Result(id, Integer.parseInt(data[1])));
		}
		return results;
	}

	/**
	 * Performs voting operation and updates state of this "database".
	 * 
	 * @param id Band identifier
	 */
	public static synchronized void vote(String id) {
		Map<String, Result> results = loadResults();
		if (results.isEmpty()) {
			for (Band band : getBands()) {
				int numOfVotes = (id.equals(band.getID())) ? 1 : 0;
				results.put(band.getID(), new Result(band.getID(), numOfVotes));
			}
			refreshResults(new LinkedList<Result>(results.values()));
			return;
		}
		if (!results.containsKey(id)) {
			// Ignore vote
			return;
		}
		results.get(id).increaseVote();
		refreshResults(new LinkedList<Result>(results.values()));
	}

	/**
	 * Updates {@linkplain #resultsFile}.
	 * 
	 * @param results List of results
	 */
	private static void refreshResults(List<Result> results) {
		try (OutputStream os = Files.newOutputStream(Paths.get(resultsFile))) {
			StringBuilder sb = new StringBuilder();
			for (Result result : results) {
				sb.append(result.getID());
				sb.append("\t");
				sb.append(result.getVotes());
				sb.append("\r\n");
			}
			os.write(sb.toString().getBytes());
		} catch (IOException e) {
			System.out.println("Writing results failed.");
			return;
		}
	}

	/**
	 * Returns {@linkplain Band} instance which matches given name or {@code null}
	 * if such does not exist.
	 * 
	 * @param name Name of the band
	 * @return Band instance
	 */
	public static Band getBand(String name) {
		for (Band band : bands.values()) {
			if (band.getName().equals(name)) {
				return band;
			}
		}
		return null;
	}

}
