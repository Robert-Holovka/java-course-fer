package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.Utils;
import hr.fer.zemris.java.hw06.shell.commands.Command;
import hr.fer.zemris.java.hw06.shell.commands.massrename.parser.NameBuilderParser;

/**
 * 
 * Moves and renames all files from one folder that matches some given filtering
 * pattern.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class MassrenameCommand extends Command {

	/**
	 * Single instance of this class.
	 */
	private static ShellCommand instance;

	/**
	 * Constructs instance of this class.
	 */
	private MassrenameCommand() {
		name = "massrename";
		description = new LinkedList<>();
		description.add("Moves and renames all files from one folder that matches some given filtering\r\n" +
				" * pattern.");
		description = Collections.unmodifiableList(description);
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!Utils.isValidNumberOfArgs(arguments, 4, 5)) {
			env.writeln(Utils.wrongUsageMessage(name));
			return ShellStatus.CONTINUE;
		}

		String[] data = Utils.splitByWhitespace(arguments);
		Path src = Utils.parsePath(data[0], env);
		Path dest = Utils.parsePath(data[1], env);
		String command = data[2];
		String pattern = Utils.removeQuotesEscapeBackslash(data[3]);

		List<FilterResult> results = null;
		try {
			results = filter(src, pattern);
		} catch (IOException e) {
			env.writeln(e.getMessage());
		}

		switch (command) {
		case "filter":
			results.forEach((r) -> env.writeln(r.toString()));
			break;
		case "groups":
			results.forEach((r) -> {
				env.write(r.toString());
				for (int i = 0; i < r.numberOfGroups(); i++) {
					env.write(" " + i + ": ");
					env.write(r.group(i) + " ");
				}
				env.write(String.format("%n"));
			});
			break;
		case "show":
		case "execute":
			NameBuilderParser parser = new NameBuilderParser(data[4]);
			NameBuilder builder = parser.getNameBuilder();

			results.forEach((r) -> {
				StringBuilder sb = new StringBuilder();
				builder.execute(r, sb);
				String newName = sb.toString();

				String firstPrint = r.toString();
				String secondPrint = " => ";
				String thirdPrint = newName;

				if (command.equals("execute")) {
					var fileDest = dest.resolve(newName);
					var fileSrc = src.resolve(r.toString());
					try {
						if (!Files.exists(dest)) {
							Files.createDirectories(dest);
						}
						Files.move(fileSrc, fileDest, StandardCopyOption.REPLACE_EXISTING);
					} catch (IOException e) {
						env.writeln("Operation failed");
					}
					firstPrint = fileSrc.toString();
					thirdPrint = fileDest.toString();
				}

				env.write(firstPrint);
				env.write(secondPrint);
				env.writeln(thirdPrint);
			});
			break;
		default:
			env.writeln("Invalid command " + command);
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
			instance = new MassrenameCommand();
		}
		return instance;
	}

	/**
	 * Filters files from a given directory. Filtering is based on a provided
	 * pattern.
	 * 
	 * @param dir     Directory that contains files that will be filtered
	 * @param pattern Pattern for filtering files
	 * @return
	 * @throws IOException If file info couldn't,,s
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		if (!Files.exists(dir)) {
			throw new IOException("Given directory does not exist.");
		}
		if (!Files.isDirectory(dir)) {
			throw new IOException("Given path is not directory.");
		}

		LinkedList<FilterResult> results = new LinkedList<>();
		Files.list(dir).forEach((path) -> {
			if (!Files.isDirectory(path)) {
				Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE);
				Matcher m = p.matcher(path.getFileName().toString());

				if (m.matches()) {
					ArrayList<String> groups = new ArrayList<>();

					for (int i = 0; i <= m.groupCount(); i++) {
						groups.add(m.group(i));
					}
					results.add(new FilterResult(path.getFileName().toString(), groups));
				}
			}
		});

		return results;
	}

}
