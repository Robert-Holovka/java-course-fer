package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Defines all necessary methods for rendering Lindenmayer system.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	/**
	 * Base length of a vector.
	 */
	private double unitLength;
	/**
	 * Level scaler for a vector length in Lindenmayer system.
	 */
	private double unitLengthDegreeScaler;
	/**
	 * Angle of a vector.
	 */
	private double angle;

	/**
	 * Base rule for rendering zero level of a Lindenmayer system.
	 */
	private String axiom;
	/**
	 * Start point a vector.
	 */
	private Vector2D origin;

	/**
	 * Storage for productions of a Lindenmayer system.
	 */
	Dictionary<Character, String> productions = new Dictionary<>();
	/**
	 * Storage for the commands of a Lindenmayer system.
	 */
	Dictionary<Character, Command> commands = new Dictionary<>();

	/**
	 * Constructs new instance of this class.
	 */
	public LSystemBuilderImpl() {
		unitLength = 0.1;
		unitLengthDegreeScaler = 1.0;
		origin = new Vector2D(0, 0);
		angle = 0.0;
		axiom = "";
	}

	/**
	 * Helper class which implements methods for drawing and generating next
	 * iteration of the Lindenmayer system.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private class LSystemImpl implements LSystem {

		/**
		 * Instance of the Context class. Storage for states of all vectors that were
		 * rendered by a {@link #draw) method.
		 */
		private Context ctx;

		/**
		 * Constructs new instance of this class.
		 */
		public LSystemImpl() {
			ctx = new Context();
		}

		@Override
		public void draw(int level, Painter painter) {
			// Initialization
			Vector2D direction = new Vector2D(1, 0);
			direction.rotate(angle);
			double scaledUnitLength = unitLength * Math.pow(unitLengthDegreeScaler, level);
			TurtleState state = new TurtleState(origin, direction, Color.BLACK, scaledUnitLength);
			ctx.pushState(state);

			String production = generate(level);
			for (char commandName : production.toCharArray()) {
				Command cmd = commands.get(commandName);
				if (cmd != null) {
					cmd.execute(ctx, painter);
				}
			}
		}

		@Override
		public String generate(int level) {
			String production = axiom;
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < level; i++) {
				for (char c : production.toCharArray()) {
					if (productions.get(c) != null) {
						sb.append(productions.get(c));
					} else {
						sb.append(c);
					}
				}

				production = sb.toString();
				sb.setLength(0);
			}

			return production;
		}

	}

	@Override
	public LSystem build() {
		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] data) {
		for (String line : data) {
			line = line.trim();

			// Skip empty line
			if (line.length() == 0)
				continue;

			String[] splitted = line.split("\\s+");

			// Parse production
			if (splitted[0].equals("production")) {
				if (splitted.length != 3 || splitted[1].length() != 1) {
					throw new IllegalArgumentException("Invalid production");
				}
				registerProduction(splitted[1].charAt(0), splitted[2]);
				continue;
			}

			// Parse command
			if (splitted[0].equals("command")) {
				if (splitted.length != 3 && splitted[1].length() != 1) {
					throw new IllegalArgumentException("Invalid command");
				}
				// Remove word 'command' from array
				String[] commandArguments = Arrays.copyOfRange(splitted, 2, splitted.length);
				// Pass only command name and arguments
				registerCommand(splitted[1].charAt(0), String.join(" ", commandArguments));
				continue;
			}

			parseField(line);
		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char commandSymbol, String command) {
		commands.put(commandSymbol, parseCommand(command));
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	/**
	 * Extracts command from a given string and returns its concrete implementation.
	 * 
	 * @param command String representation of a command
	 * @return Command implementation for a given command
	 * @throws IllegalArgumentException if a given string does not represent one of
	 *                                  existing commands or if command arguments
	 *                                  are not valid
	 */
	private Command parseCommand(String command) {
		String[] data = command.split("\\s+");

		if (data.length > 2) {
			throw new IllegalArgumentException("Invalid command.");
		}

		if (data.length == 1) {
			if (command.equals("push")) {
				return new PushCommand();
			}
			if (command.equals("pop")) {
				return new PopCommand();
			}
			throw new IllegalArgumentException("Invalid command.");
		}

		String commandName = data[0];

		// Parse color
		if (commandName.equals("color")) {
			try {
				return new ColorCommand(Color.decode("#" + data[1]));
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid command.");
			}
		}

		// Parse other commands (name + value)
		double value = 0.0;
		try {
			value = Double.parseDouble(data[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid command.");
		}

		switch (commandName) {
		case "draw":
			return new DrawCommand(value);
		case "skip":
			return new SkipCommand(value);
		case "scale":
			return new ScaleCommand(value);
		case "rotate":
			return new RotateCommand(value);
		default:
			throw new IllegalArgumentException("Invalid command");
		}
	}

	/**
	 * Extracts one of the field properties of this class.
	 * 
	 * @param line String contains name of the property and its value
	 * @throws IllegalArgumentException if a given string does not represent one of
	 *                                  existing fields or if field arguments are
	 *                                  not valid
	 */
	private void parseField(String line) {
		String[] data = line.split("\\s+");
		String field = data[0];

		// Parse origin field
		if (field.equals("origin")) {
			if (data.length != 3) {
				throw new IllegalArgumentException("Invalid field initialization.");
			}
			try {
				double x = Double.parseDouble(data[1]);
				double y = Double.parseDouble(data[2]);
				setOrigin(x, y);
				return;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid field initialization.");
			}
		}

		// Parse axiom field
		if (field.equals("axiom")) {
			if (data.length != 2) {
				throw new IllegalArgumentException("Invalid field initialization.");
			}
			setAxiom(data[1]);
			return;
		}

		double value = 0;
		try {
			if (data.length == 2) {
				value = Double.parseDouble(data[1]);
			} else {
				// Remove first argument from data
				data = Arrays.copyOfRange(data, 1, data.length);
				// Glue all data in a string
				line = String.join("", data);
				// Split data by operators and keep operators in an array
				data = line.split("((?<=[-+*/])|(?=[-+*/]))");
				value = evaluteExpression(data);
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid field initialization.");
		}

		// Parse other fields(with only 1 double value)
		switch (field) {
		case "angle":
			setAngle(value);
			break;
		case "unitLength":
			setUnitLength(value);
			break;
		case "unitLengthDegreeScaler":
			setUnitLengthDegreeScaler(value);
			break;
		default:
			throw new IllegalArgumentException("Invalid field initialization.");
		}
	}

	/**
	 * Evaluates result of the given expression.
	 * 
	 * @param data Expression members stored in a string array
	 * @return double result for the given expression
	 */
	private double evaluteExpression(String[] data) {
		double first = Double.parseDouble(data[0]);
		double second = Double.parseDouble(data[2]);
		switch (data[1]) {
		case "/":
			return first / second;
		case "+":
			return first + second;
		case "\\*":
			return first * second;
		case "-*":
			return first - second;
		default:
			return 0;
		}
	}

}
