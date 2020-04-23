package hr.fer.zemris.java.raytracer.model;

/**
 * Model for a sphere.
 * 
 * @author Robert Holovka
 * @version 1.0
 */
public class Sphere extends GraphicalObject {

	/**
	 * Maximum allowed difference between two decimal numbers if they are considered
	 * as equal.
	 */
	private static double MAX_DIFFERENCE = 0.0000001;

	/**
	 * Center point of the sphere.
	 */
	Point3D center;
	/**
	 * Radius of the sphere.
	 */
	double radius;
	/**
	 * Diffuse coefficient of a red color.
	 */
	double kdr;
	/**
	 * Diffuse coefficient of a green color.
	 */
	double kdg;
	/**
	 * Diffuse coefficient of a blue color.
	 */
	double kdb;
	/**
	 * Reflective coefficient of a red color.
	 */
	double krr;
	/**
	 * Reflective coefficient of a green color.
	 */
	double krg;
	/**
	 * Reflective coefficient of a blue color.
	 */
	double krb;
	/**
	 * Shininess factor of a material.
	 */
	double krn;

	/**
	 * Constructs new sphere.
	 * 
	 * @param center Center point of the sphere
	 * @param radius Radius of the sphere
	 * @param kdr    Diffuse coefficient of a red color
	 * @param kdg    Diffuse coefficient of a green color
	 * @param kdb    Diffuse coefficient of a blue color
	 * @param krr    Reflective coefficient of a red color
	 * @param krg    Reflective coefficient of a green color
	 * @param krb    Reflective coefficient of a blue color
	 * @param krn    Shininess factor
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg,
			double kdb, double krr, double krg, double krb, double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * Finds intersection between a given ray and this sphere. If there is no
	 * intersection returns {@code null}.
	 * 
	 * @param ray
	 * @return RayIntersection
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D t = ray.start.sub(center);

		double a = ray.direction.scalarProduct(ray.direction);
		double b = 2 * (ray.direction.scalarProduct(t));
		double c = (t.scalarProduct(t)) - (radius * radius);

		double d = b * b - 4 * a * c;

		// There is no intersection
		if (d < 0)
			return null;

		d = Math.sqrt(d);
		double distance1 = (-b + d) / 2 * a;
		double distance2 = (-b - d) / 2 * a;

		// Check if there is only 1 intersection
		boolean outer = (distance1 - distance2) < MAX_DIFFERENCE;

		double closer = (distance1 < distance2) ? distance1 : distance2;
		Point3D intersectionPoint = ray.start.add(ray.direction.scalarMultiply(closer));

		return new RayIntersection(intersectionPoint, closer, outer) {
			@Override
			public Point3D getNormal() {
				return center.sub(intersectionPoint).normalize().negate();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}
}