package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.LinkedList;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * The tree command expects a single argument: directory name and prints a
 * recursive tree of that directory successors.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class TreeCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private TreeCommand() {
		name = "tree";
		description = new LinkedList<>();

		description.add("The tree command expects a single argument:");
		description.add("directory name and prints a tree of that directory successors.");
		description.add("This command is recursive.");
		description.add("Usage example: \"> tree c:/folder \"");
		description = Collections.unmodifiableList(description);
	}

	/**
	 * Helper class for traversing directory structure.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class TreeOutput implements FileVisitor<Path> {
		/**
		 * Level of recursion.
		 */
		private int level;
		/**
		 * Instance of Environment class.
		 */
		private Environment env;

		/**
		 * Constructs instance of this class with provided Environment.
		 * 
		 * @param env Environment
		 */
		public TreeOutput(Environment env) {
			this.env = env;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if(dir.getFileName() != null) {
				env.writeln(Utils.indent(dir.getFileName().toString(), 2 * level));
			}
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			env.writeln(Utils.indent(file.getFileName().toString(), 2 * (level)));
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		Path path = Utils.parsePath(arguments);
		if (path == null) {
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(path)) {
			env.writeln(String.format("\"%s\" does not exist.", path));
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(path)) {
			env.writeln(String.format("\"%s\" is not a directory.", path));
			return ShellStatus.CONTINUE;
		}

		try {
			Files.walkFileTree(path, new TreeOutput(env));
		} catch (IOException e) {
			env.writeln(Utils.UNKNOWN_ERROR_MESSAGE);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new TreeCommand();
		}
		return instance;
	}

}
