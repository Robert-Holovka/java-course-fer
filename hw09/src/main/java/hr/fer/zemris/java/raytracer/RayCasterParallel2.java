package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerAnimator;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Concrete implementation of a ray caster algorithm.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class RayCasterParallel2 {

	private static final int MIN_ROWS_THRESHOLD = 8;
	/**
	 * Maximum allowed value for reed, green and blue color.
	 */
	private static final int MAX_RGB = 255;
	/**
	 * Maximum allowed difference between two distances if they are considered as
	 * equal.
	 */
	private static final double DISTANCE_THRESHOLD = 0.1;

	/**
	 * Entry point of the program.
	 * 
	 * @param args Arguments from the command line
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(
				getIRayTracerProducer(), getIRayTracerAnimator(), 30, 30);
	}

	/**
	 * Returns ray tracer animator.
	 * 
	 * @return IRayTracerAnimator
	 */
	private static IRayTracerAnimator getIRayTracerAnimator() {
		return new IRayTracerAnimator() {
			long time;

			@Override
			public void update(long deltaTime) {
				time += deltaTime;
			}

			@Override
			public Point3D getViewUp() { // fixed in time
				return new Point3D(0, 0, 10);
			}

			@Override
			public Point3D getView() { // fixed in time
				return new Point3D(-2, 0, -0.5);
			}

			@Override
			public long getTargetTimeFrameDuration() {
				return 150; // redraw scene each 150 milliseconds
			}

			@Override
			public Point3D getEye() { // changes in time
				double t = (double) time / 10000 * 2 * Math.PI;
				double t2 = (double) time / 5000 * 2 * Math.PI;
				double x = 50 * Math.cos(t);
				double y = 50 * Math.sin(t);
				double z = 30 * Math.sin(t2);
				return new Point3D(x, y, z);
			}
		};
	}

	/**
	 * Returns ray tracer producer.
	 * 
	 * @return IRayTracerProducer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp,
					double horizontal, double vertical, int width, int height,
					long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");

				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = viewUp.sub(zAxis.scalarMultiply(zAxis.scalarProduct(viewUp))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();
				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene2();

				@SuppressWarnings("serial")
				class Job extends RecursiveAction {
					int from;
					int to;

					public Job(int from, int to) {
						this.from = from;
						this.to = to;
					}

					@Override
					protected void compute() {
						if (from - to < MIN_ROWS_THRESHOLD) {
							computeDirect();
							return;
						}

						invokeAll(new Job(from, (from - to) / 2), new Job(from + (from - to) / 2, to));
					}

					private void computeDirect() {
						processPixels(scene, screenCorner, eye, xAxis, yAxis, width, height, horizontal, vertical,
								red, green, blue, from, to);
					}

				}

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new Job(0, height));

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * For each pixel on the screen casts ray through it and calculates its color.
	 * 
	 * @param scene        Reference to the scene
	 * @param screenCorner Reference to the screen corner
	 * @param eye          Position of a viewer
	 * @param xAxis        x axis
	 * @param yAxis        y axis
	 * @param width        width of the screen
	 * @param height       height of the screen
	 * @param horizontal   horizontal offset
	 * @param vertical     vertical offset
	 * @param red          Array for intensity of a red color for each pixel
	 * @param green        Array for intensity of a green color for each pixel
	 * @param blue         Array for intensity of a blue color for each pixel
	 * @param from         lower limit for y
	 * @param to           upper limit for y
	 */
	private static void processPixels(Scene scene, Point3D screenCorner, Point3D eye, Point3D xAxis, Point3D yAxis,
			int width, int height, double horizontal, double vertical, short[] red, short[] green,
			short[] blue, int from, int to) {

		short[] rgb = new short[3];
		int offset = from * width;
		for (int y = from; y < to; y++) {
			for (int x = 0; x < width; x++) {
				Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / width))
						.sub(yAxis.scalarMultiply(y * vertical / height));
				Ray ray = Ray.fromPoints(eye, screenPoint);
				tracer(scene, ray, rgb);

				red[offset] = rgb[0] > MAX_RGB ? MAX_RGB : rgb[0];
				green[offset] = rgb[1] > MAX_RGB ? MAX_RGB : rgb[1];
				blue[offset] = rgb[2] > MAX_RGB ? MAX_RGB : rgb[2];
				offset++;
			}
		}
	}

	/**
	 * Calculates interference of a given ray and scene. If there is no
	 * intersections sets rgb array to black color, otherwise calls appropriate
	 * method for establishing true color of pixel that this ray cuts.
	 * 
	 * @param scene Reference to the scene
	 * @param ray
	 * @param rgb   Red(0), green(1), blue(2) color array
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		// Black
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;

		RayIntersection intersection = findClosestIntersection(scene, ray);

		if (intersection == null) {
			return;
		}

		determineColorFor(scene, intersection, ray, rgb);
	}

	/**
	 * Calculates color for a given intersection. Calculation is based on a Phong
	 * lighting model.
	 * 
	 * @param scene
	 * @param intersection Point for which color will be calculated
	 * @param view         Vector of a "viewer" gaze
	 * @param rgb          Red(0), green(1), blue(2) color array
	 */
	private static void determineColorFor(Scene scene, RayIntersection intersection, Ray view, short[] rgb) {
		// Ambient component, constant in whole scene
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		for (LightSource ls : scene.getLights()) {

			RayIntersection closestToLight = findClosestIntersection(scene,
					Ray.fromPoints(ls.getPoint(), intersection.getPoint()));

			if (closestToLight == null || closestToLight.isOuter())
				continue;

			Point3D p1 = ls.getPoint();
			Point3D p2 = intersection.getPoint();
			double realDistance = Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2)
					+ Math.pow(p1.z - p2.z, 2));
			if (closestToLight.getDistance() + DISTANCE_THRESHOLD < realDistance)
				continue;

			Point3D viewVector = ls.getPoint().sub(intersection.getPoint()).normalize();
			Point3D normal = intersection.getNormal().normalize();

			// Diffuse component
			double ln = viewVector.scalarProduct(normal);
			ln = (ln < 0) ? 0.0 : ln;
			rgb[0] += ls.getR() * intersection.getKdr() * ln;
			rgb[1] += ls.getG() * intersection.getKdg() * ln;
			rgb[2] += ls.getB() * intersection.getKdb() * ln;

			// Reflective component
			// r=d−2(d⋅n)n
			Point3D r = viewVector.sub(normal.scalarMultiply(2 * viewVector.scalarProduct(normal)));
			Point3D v = view.start.sub(normal).normalize();
			double rv = Math.pow((r.scalarProduct(v)), intersection.getKrn());
			rv = (rv < 0) ? 0.0 : rv;
			rgb[0] += ls.getR() * intersection.getKrr() * rv;
			rgb[1] += ls.getG() * intersection.getKrg() * rv;
			rgb[2] += ls.getB() * intersection.getKrb() * rv;

		}

	}

	/**
	 * Finds closest intersection between a given ray and scene.
	 * 
	 * @param scene
	 * @param ray
	 * @return null if there is no intersection, otherwise instance of
	 *         RayIntersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;
		for (GraphicalObject object : scene.getObjects()) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);

			if (intersection != null && closestIntersection == null) {
				closestIntersection = intersection;
			} else if (intersection != null && intersection.getDistance() < closestIntersection.getDistance()) {
				closestIntersection = intersection;
			}
		}
		return closestIntersection;
	}
}
