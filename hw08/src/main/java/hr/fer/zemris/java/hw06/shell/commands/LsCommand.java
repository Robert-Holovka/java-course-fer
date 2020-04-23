package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;

/**
 * Takes a single argument –directory– and writes a directory listing. This
 * command is not recursive. Output is formatted and provides informations such
 * as if file is: directory, readable, writable or executable. Also, it provides
 * additional info such as: Size and date/time of the creation.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LsCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Helper class for storing additional informations about some path.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class PathInfo {
		/**
		 * Maximum size of all visited files/directories.
		 */
		private static long maxSize = 0;

		/**
		 * Path to file/directory this class represents.
		 */
		private Path path;
		/*
		 * Date/time creation of this file/directory.
		 */
		private String creationDate;

		/**
		 * Size of this file/directory.
		 */
		private long size;
		/**
		 * Information that tells if this is a path to directory.
		 */
		private char directory;
		/**
		 * Information that tells if this path is readable.
		 */
		private char readable;
		/**
		 * Information that tells if this path is writable.
		 */
		private char writable;
		/**
		 * Information that tells if this path is executable.
		 */
		private char executable;
	}

	/**
	 * Constructs instance of this class.
	 */
	private LsCommand() {
		name = "ls";
		description = new LinkedList<>();
		description.add("Command ls takes a single argument –directory– and writes a directory listing.");
		description.add("This command is not recursive.");
		description.add("Output is formatted and provides informations such as if file is: ");
		description.add("directory, readable, writable or executable.");
		description.add("Also, it provides additional info such as:");
		description.add("Size and date/time of the creation.");
		description.add("Usage example: \"> ls c:/test \"");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 1)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		Path path = Utils.parsePath(arguments, env);
		if (path == null) {
			env.writeln("Invalid path format.");
			return ShellStatus.CONTINUE;
		}

		if (!Files.exists(path)) {
			env.writeln(String.format("Directory \"%s\" does not exist.", path.toString()));
			return ShellStatus.CONTINUE;
		}

		if (!Files.isDirectory(path)) {
			env.writeln(String.format("\"%s\" is not a directory.", path.toString()));
			return ShellStatus.CONTINUE;
		}

		List<PathInfo> children = null;
		try {
			children = extractChildrenInfo(path);
		} catch (IOException | RuntimeException e) {
			env.writeln(Utils.UNKNOWN_ERROR_MESSAGE);
		}

		StringBuilder sb = new StringBuilder();
		for (PathInfo info : children) {
			// 1st column
			sb.append(info.directory);
			sb.append(info.readable);
			sb.append(info.writable);
			sb.append(info.executable);
			sb.append(" ");
			// 2nd column
			sb.append(String.format("%" + String.valueOf(PathInfo.maxSize).length() + "d", info.size));
			sb.append(" ");
			// 3rd column
			sb.append(info.creationDate);
			sb.append(" ");
			// 4th column
			sb.append(info.path.getFileName().toString());

			env.writeln(sb.toString());
			sb.setLength(0);
		}

		return ShellStatus.CONTINUE;
	}

	/**
	 * Visits all children of provided path and returns additional info about those
	 * children as a list of PathInfo.
	 * 
	 * @param path Path to the directory
	 * @return List<PathInfo> additional info about directory children
	 * @throws IOException      If provided path is not valid
	 * @throws RuntimeException If info about some child can't be extracted
	 */
	private List<PathInfo> extractChildrenInfo(Path path) throws IOException, RuntimeException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<PathInfo> children;

		PathInfo.maxSize = 0;
		children = Files.list(path).map((p) -> {
			PathInfo info = new PathInfo();
			info.directory = (Files.isDirectory(p) ? 'd' : '-');
			info.readable = (Files.isReadable(p) ? 'r' : '-');
			info.writable = (Files.isWritable(p) ? 'w' : '-');
			info.executable = (Files.isExecutable(p) ? 'x' : '-');
			info.path = p;

			try {
				info.size = Files.size(p);
				if (info.size > PathInfo.maxSize) {
					PathInfo.maxSize = info.size;
				}

				BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);
				FileTime fileTime = faView.readAttributes().creationTime();
				info.creationDate = sdf.format(new Date(fileTime.toMillis()));
			} catch (IOException e) {
				throw new RuntimeException();
			}

			return info;
		}).collect(Collectors.toList());

		return children;
	}

	/**
	 * Returns single instance of this class.
	 * 
	 * @return ShellCommand
	 */
	public static ShellCommand getInstance() {
		if (instance == null) {
			instance = new LsCommand();
		}
		return instance;
	}

}
