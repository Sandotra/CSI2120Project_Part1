/**
 * 
 * Name:                Suryadev Andotra
 * Student Number:          300006733
 * 
 * 
 * 
 * */

 
public class Point3D {

	double x, y, z;

	public Point3D(double x, double y, double z) {

		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public String toString() {
		String print = getX() + "	" + getY() + "	" + getZ();
		return print;
	}
}