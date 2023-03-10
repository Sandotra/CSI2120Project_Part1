/**
 * 
 * Name:                Suryadev Andotra
 * Student Number:          300006733
 * 
 * 
 * 
 * */

import java.io.File;
import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class PointCloud {

	public static Point3D[] ptCloud;

	public static int size;

	public PointCloud(String filename) throws Exception {

		sizeofPointCloud(filename);

		ptCloud = new Point3D[size];

		populatePointCloud(filename);

	}

	public PointCloud() {

		size = 100000;
		ptCloud = new Point3D[size];

		for (int i = 0; i < size; i++) {
			ptCloud[i] = null;
		}
	}

	public PointCloud(int size) {

		this.size = size;
		ptCloud = new Point3D[size];

		for (int i = 0; i < size; i++) {
			ptCloud[i] = null;
		}
	}

	//Adds a point into the Point3D array initialized if the array[index] is null (free space)
	public void addPoint(Point3D pt) {

		boolean allow = true;
		for (int i = 0; i < size; i++) {
			while (allow) {
				if (ptCloud[i] == null) {
					ptCloud[i] = new Point3D(pt.getX(), pt.getY(), pt.getZ());
					allow = false;
				}
			}
		}
	}

	//Returns a random point from the stored Point3D array
	public Point3D getPoint() {

		int random = (int)Math.floor(Math.random() * size);
		return ptCloud[random];		
	}

	//Save method to write a pointcloud into an xyz file
	public void save(String filename) throws Exception {

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));

		writer.write("x	y	z");
		writer.newLine();
		for (int i = 0; i < ptCloud.length; i++) {
			writer.write(ptCloud[i].toString());
			writer.newLine();
		}
		writer.flush();
	}

	//Iterator method
	public Iterator<Point3D> iterator() {

		Iterator<Point3D> it = new Iterator<Point3D>() {

			private int index = 0;

			public boolean hasNext() {
				return index < size && ptCloud[index] !=null;
			}

			public Point3D next() {
				return ptCloud[index++];
			}

			public void remove() {
				ptCloud[index] = null;
				index++;
			}
		};
		return it;
	}

	//Intiailizes the size of the array needed for the given file
	public void sizeofPointCloud(String filename) throws Exception {

		Scanner myscanner = new Scanner(new File(filename));
		while (myscanner.hasNext()) {

			String str = myscanner.nextLine();
			if (!str.isEmpty()) {
				size++;
			}

		}
		size = size - 1;

	}

	//Populates the Point3D array with the given file
	public void populatePointCloud(String filename) throws Exception {

		int index = 0;
		Scanner myscanner = new Scanner(new File(filename));
		while (myscanner.hasNext()) {

			String str = myscanner.nextLine();
			if (!str.isEmpty() && str.charAt(0) != 'x') {
				String[] coords = str.split("	");
				ptCloud[index] = new Point3D(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
				index++;
			}
		}
	}
}