/**
 * 
 * Name:                Suryadev Andotra
 * Student Number:          300006733
 * 
 * 
 * 
 * */

import java.lang.Math;

public class Plane3D {

	double a, b, c, d;

	Point3D pt1, pt2, normal;

	//Initializes the Plane3D 
	public Plane3D(Point3D p1, Point3D p2, Point3D p3) {

		normal = calculateNormal(calculateP1P2(p1, p2), calculateP1P2(p2, p3));
		d = calculateD(normal, p1);

		a = normal.getX();
		b = normal.getY();
		c = normal.getZ();
	}

	public Plane3D(double a, double b, double c, double d) {

		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}


	//Returns the distance to the given point from this plane
	public double getDistance(Point3D pt) {

		double distance = java.lang.Math.abs((a*pt.getX() + b*pt.getY() + c*pt.getZ() - d))/Math.sqrt(a*a + b*b + c*c);
		return distance;
	}

	//Calculates the P1P2 vector for constructor
	private Point3D calculateP1P2(Point3D a, Point3D b) {

		Point3D pt = null;

		pt = new Point3D(b.getX() - a.getX(), b.getY() - a.getY(), b.getZ() - a.getZ());
		return pt;
	}

	//Calculates the normal vector for constructor
	private Point3D calculateNormal(Point3D a, Point3D b) {

		Point3D pt = null;
		double i, j, k;

		i = (a.getY()*b.getZ()) - (a.getZ()*b.getY());
		j = (a.getZ()*b.getX()) - (a.getX()*b.getZ());
		k = (a.getX()*b.getY()) - (a.getY()*b.getX());

		pt = new Point3D(i, j, k);

		return pt;
	}

	//Calculates the d value for the plane
	private double calculateD(Point3D normal, Point3D a) {

		double d = 0;

		d = normal.getX()*(-a.getX()) + normal.getY()*(-a.getY()) + normal.getZ()*(-a.getZ());

		return -d;
	}

}