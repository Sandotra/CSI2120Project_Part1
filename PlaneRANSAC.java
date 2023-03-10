/**
 * 
 * Name:                Suryadev Andotra
 * Student Number:          300006733
 * 
 * 
 * 
 * */



import java.util.Random;
import java.util.*;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

// Uses RANSAC algorithm to process data points from xyz files in order to find the best plane which fits the most amount of data points
// Best plane data points are added into a Point3D array and saved into a new xyz file. 
public class PlaneRANSAC {
    private static PointCloud pointCloud;
    private static double eps;
    private static int numberOfIterations;
    private static Plane3D bestPlane;
    private int bestPlaneInliersCount;

    public PlaneRANSAC(PointCloud pointCloud) {
        this.pointCloud = pointCloud;
    }

    //set Eps value
    public void setEps(double eps) {
    	this.eps = eps;
    }

    //get Eps value
    public double getEps() {
    	return eps;
    }

    //runs the RANSAC algorithm and consequently runs the save function to save the dataset into the given filename
    public void run(int numberOfIterations, String filename) throws Exception {
        // Random random = new Random();
        int iteration = 0;

        while (iteration < numberOfIterations) {
            Point3D[] sample = new Point3D[3];
            for (int i = 0; i < 3; i++) {
                sample[i] = pointCloud.getPoint();
            }

            Plane3D plane = new Plane3D(sample[0], sample[1], sample[2]);
            int inliersCount = 0;

            for (Point3D point : pointCloud.ptCloud) {
                if (point == null) {
                    continue;
                }
                if (plane.getDistance(point) <= eps) {
                    inliersCount++;
                }
            }

            if (inliersCount > bestPlaneInliersCount) {
                bestPlane = plane;
                bestPlaneInliersCount = inliersCount;
            }

            iteration++;
        }

        save(filename);
        // return bestPlane;
    }

    //Used to initialize numberOfIterations needed for a given confidence level and % of points on plane
    public int getNumberOfIterations(double confidence, double percentageOfPointsOnPlane) {

    	int numberOfPoints = (int) (percentageOfPointsOnPlane * pointCloud.size);
		double numerator = Math.log10(1 - confidence);
		double denominator = Math.log10(1 - Math.pow(percentageOfPointsOnPlane, 3));
		numberOfIterations = (int) Math.ceil(numerator/denominator);
		return numberOfIterations;
    }

    //saves the dataset into provided filename using BufferedWriter
    public void save(String filename) throws Exception {

    	Point3D[] dominantCloud = new Point3D[pointCloud.size];

    	for (int i = 0; i < pointCloud.size; i++) {

    		Point3D point = new Point3D(pointCloud.ptCloud[i].getX(), pointCloud.ptCloud[i].getY(), pointCloud.ptCloud[i].getZ());
    		if (bestPlane.getDistance(point) < eps) {
    			dominantCloud[i] = point;
    		}
    	}

    	BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false));

    	writer.write("x	y	z");
    	writer.newLine();
    	for(int i = 0; i < dominantCloud.length; i++) {
    		if (dominantCloud[i] != null) {
    			writer.write(dominantCloud[i].toString());
    			writer.newLine();
    		}
    		
    	}
    	writer.flush();
	}


    //Main method to take user input for which file to process
    //along with taking the Eps value and which filename to save the corresponding dataset to.
    public static void main(String[] args) throws Exception {

    	System.out.print("Please enter the name of the file to process: ");

		Scanner scanner = new Scanner(System.in);

		String strFilename = scanner.nextLine();
    	PointCloud ptCloud = new PointCloud(strFilename);
    	PlaneRANSAC ransac = new PlaneRANSAC(ptCloud);

    	System.out.print("Enter eps: ");
    	strFilename = scanner.nextLine();
    	ransac.setEps(Double.parseDouble(strFilename));
    	ransac.getNumberOfIterations(.99, .30);

    	System.out.print("Please enter the name of the file to save to: ");
    	strFilename = scanner.nextLine();
    	ransac.run(numberOfIterations, strFilename);

    }
}