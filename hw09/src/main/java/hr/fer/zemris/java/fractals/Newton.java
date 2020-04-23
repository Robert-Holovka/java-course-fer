package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Visualizes fractals derived from Newton-Raphson iterations.
 * 
 * @author Robert Holovka
 * @version 1.1
 */
public class Newton {

	/**
	 * Convergence threshold.
	 */
	private static final double CONVERGENCE_THRESHOLD = 0.001;
	/**
	 * Threshold for establishing whether two roots are equal.
	 */
	private static final double ROOT_THRESHOLD = 0.002;
	/**
	 * Maximum number of iterations.
	 */
	private static final int MAX_ITERATION = 100;

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		Set<Complex> roots = new HashSet<>();
		Scanner sc = new Scanner(System.in);

		loadRoots(roots, sc);
		if (roots.size() < 2) {
			System.out.println("Not enough roots. Provided: " + roots.size());
			return;
		}

		System.out.println("Image of fractal will appear shortly. Thank you.");
		ComplexRootedPolynomial rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE,
				roots.toArray(new Complex[roots.size()]));
		FractalViewer.show(new FractalProducer(rootedPolynomial));
		sc.close();
	}

	/**
	 * Retrieves inputs from the user and tries to parse it as roots of complex
	 * polynomial.
	 * 
	 * @param roots List of Complex roots
	 * @param sc    Scanner Object responsible for fetching user input
	 */
	private static void loadRoots(Set<Complex> roots, Scanner sc) {
		while (true) {
			System.out.printf("Root %d> ", roots.size() + 1);
			String line = sc.nextLine();
			if (line.equals("done")) {
				break;
			}

			try {
				roots.add(Complex.parse(line));
			} catch (IllegalArgumentException e) {
				System.out.println("Given input: '" + line + "' is not a valid complex number, try again.");
			}
		}
	}

	/**
	 * Class designed for producing fractals derived from Newton-Raphson iteration.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class FractalProducer implements IFractalProducer {
		/**
		 * Polynomial in a normal form.
		 */
		private ComplexPolynomial factored;
		/**
		 * Polynomial in a rooted form.
		 */
		private ComplexRootedPolynomial rooted;
		/**
		 * Thread pool.
		 */
		private ExecutorService pool;
		/**
		 * Number of data fractions.
		 */
		private int numberOfFractions = 8 * Runtime.getRuntime().availableProcessors();

		/**
		 * Constructs new instance of this class defined by a given polynomial.
		 * 
		 * @param polynomial
		 */
		public FractalProducer(ComplexRootedPolynomial polynomial) {
			rooted = polynomial;
			factored = polynomial.toComplexPolynom();
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
					new DaemonicThreadFactory());
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax,
				int width, int height, long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {

			short[] data = new short[width * height];
			class Producer implements Runnable {
				int from;
				int to;

				public Producer(int from, int to) {
					this.from = from;
					this.to = to;
				}

				@Override
				public void run() {
					doJob(reMin, reMax, imMin, imMax, width, height, cancel, data, from, to);
				}
			}

			List<Future<?>> results = new ArrayList<>();
			int jobFraction = height / numberOfFractions;
			for (int i = 0; i < numberOfFractions; i++) {
				int from = i * jobFraction;
				int to = i == (numberOfFractions - 1) ? height : (from + jobFraction);
				results.add(pool.submit(new Producer(from, to)));
			}

			for (Future<?> f : results) {
				while (true) {
					try {
						f.get();
						break;
					} catch (InterruptedException | ExecutionException e) {
					}
				}
			}

			observer.acceptResult(data, (short) (factored.order() + 1), requestNo);
		}

		/**
		 * Calculates closest roots for points defined by a variables {@code from} and
		 * {@code to}.
		 * 
		 * @param reMin  lower limit for real numbers
		 * @param reMax  upper limit for real numbers
		 * @param imMin  lower limit for imaginary numbers
		 * @param imMax  upper limit for imaginary numbers
		 * @param width  width of the screen
		 * @param height height of the screen
		 * @param cancel Signal for killing this method
		 * @param data   Indexes of closest root for each iteration
		 * @param from   lower limit for y
		 * @param to     upper limit for y
		 */
		private void doJob(double reMin, double reMax, double imMin, double imMax, int width, int height,
				AtomicBoolean cancel, short[] data, int from, int to) {
			int offset = from * width;
			for (int y = from; y < to; y++) {
				// Cancellation can happen while iterating!!!
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					double module = 0;
					int iteration = 0;

					double real = (double) x / width * (reMax - reMin);
					double imaginary = ((double) (height - y) / height) * (imMax - imMin);
					real += reMin;
					imaginary += imMin;
					Complex zn = new Complex(real, imaginary);

					do {
						ComplexPolynomial derived = factored.derive();

						Complex numerator = factored.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex fraction = numerator.divide(denominator);

						Complex znold = zn;
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iteration++;
					} while (module > CONVERGENCE_THRESHOLD && iteration < MAX_ITERATION);

					int index = rooted.indexOfClosestRootFor(zn, ROOT_THRESHOLD);
					data[offset++] = (short) (index + 1);
				}
			}
		}

	}

	/**
	 * Generator for daemon threads.
	 * 
	 * @author Robert Holovka
	 * @version 1.0
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {
		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			t.setDaemon(true);
			return t;
		}
	}
}
