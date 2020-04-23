package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Copies content from one file to another. The copy command expects two
 * arguments: source and destination file name. If destination file exists, it
 * asks user does he really want to overwrite it. If the destination argument is
 * directory, original file is copied into that directory using original file
 * name.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class CopyCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private CopyCommand() {
		name = "copy";
		description = new LinkedList<>();
		description.add("Copies content from one file to another.");
		description.add("The copy command expects two arguments:");
		description.add("- source file name ");
		description.add("- destination file name ");
		description.add("If destination file exists, it asks user does he really want to overwrite it.");
		description.add("If the destination argument is directory, original file is copied into");
		description.add("that directory using original file name.");
		description.add("Usage example: \"> copy c:/a.txt c:/b.txt \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 2)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] data = Utils.splitByWhitespace(arguments);
		Path src = Utils.parsePath(data[0]);
		Path dest = Utils.parsePath(data[1]);

		if (src == null || dest == null) {
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		// Validate source file
		if (!Files.exists(src)) {
			env.writeln(String.format("File \"%s\" does not exist.", src));
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(src)) {
			env.writeln(String.format("\"%s\" is not a file.", src));
			return ShellStatus.CONTINUE;
		}

		// Validate destination
		data = data[1].split("(?<=[\\/])");
		String destinationPath = String.join("", Arrays.copyOfRange(data, 0, data.length - 1));
		destinationPath = Utils.parsePath(destinationPath).toString();

		if (!Files.exists(Paths.get(destinationPath))) {
			env.writeln("Invalid destination path: " + destinationPath);
			return ShellStatus.CONTINUE;
		}

		// If second arguments was path to directory
		if (Files.isDirectory(dest)) {
			destinationPath = dest.toString();
			destinationPath += (destinationPath.endsWith("\\") || destinationPath.endsWith("/")) ? "" : "\\";
			destinationPath += src.getFileName();
			dest = Paths.get(destinationPath);
			env.writeln("File name not provided, copying to: " + destinationPath);
		}

		if (Files.exists(dest)) {
			env.writeln(String.format("File \"%s\" already exists on a specified path.", dest.getFileName()));
			String line = "";

			do {
				env.writeln("Do you want to overwrite this file? [y/n]");
				line = env.readLine();

				if (line.equals("n")) {
					return ShellStatus.CONTINUE;
				}
			} while (!line.equals("y"));
		}

		if (src.equals(dest)) {
			env.writeln("Source and destination files must differ!");
			return ShellStatus.CONTINUE;
		}

		if (copyFile(src, dest)) {
			env.writeln("Copied successfully.");
		} else {
			env.writeln("An error has occurred while copying files.");
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies given source file to provided destination.
	 * 
	 * @param src  File to be copied
	 * @param dest Destination where copied file will be stored
	 * @return True if copying was successful, false otherwise
	 */
	private boolean copyFile(Path src, Path dest) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(src));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(dest))) {
			byte[] buff = new byte[DEFAULT_BUFFER_SIZE];

			while (true) {
				int size = is.read(buff);
				if (size < 0) {
					break;
				}
				os.write(buff, 0, size);
			}
		} catch (IOException e) {
			return false;
		}

		return true;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new CopyCommand();
		}
		return instance;
	}

}
