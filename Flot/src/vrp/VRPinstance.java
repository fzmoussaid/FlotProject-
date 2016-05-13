package vrp;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;


/**
 * VRPinstance
 */

public class VRPinstance {


	/* distance matrix */
	private static double matrix[][] = null;

	/* demand table */
	private static int demands[] = null;

	/* Capacity of a vehicle */
	private static int capacity = -1;

	// private String name=null;
	private static int size;

	private static int trucks;

	private static int optimal;

	private static String name;

	public VRPinstance(FileReader f) {
		Scanner scan = new Scanner(f);
		try {
			name = scan.nextLine().split(":")[1]; // name
			String[] part = scan.nextLine().split(":"); // comment
			String[] part2 = part[2].split(",");
			trucks = Integer.parseInt(part2[0].substring(1, part2[0].length()));

			part[3] = part[3].substring(1, part[3].length()-1);
			optimal = Integer.parseInt(part[3]);
			scan.nextLine(); // type
			scan.next(); // dimension
			size = scan.nextInt();
			scan.nextLine(); // passage a la ligne
			scan.nextLine(); // edge type
			scan.next(); // capacity:
			capacity = scan.nextInt();
			scan.nextLine(); // node coord section

			matrix = new double[size][];
			for (int i = 0; i < size; i++) {
				matrix[i] = new double[size];
			}

			double x[] = new double[size];
			double y[] = new double[size];

			scan.next();

			for (int i = 0; i < size; i++) {
				scan.nextInt();
				x[i] = scan.nextInt();
				y[i] = scan.nextInt();
			}

			scan.nextLine(); // demand section
			scan.nextLine(); // demand section

			demands = new int[size];
			for (int i = 0; i < size; i++) {
				scan.nextInt(); // line number
				demands[i] = scan.nextInt();
			}



			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					if (i == j) {
						matrix[i][j] = Double.MAX_VALUE;
					} else {
						matrix[i][j] = Math.sqrt((x[i] - x[j]) * (x[i] - x[j])
								+ (y[i] - y[j]) * (y[i] - y[j]));
						matrix[i][j] = Math.round(matrix[i][j]);
					}
				}
			}
		}catch(Exception e){
			System.out.println("File: "+name+" Corrupted");
			return;
		}
		scan.close();

	}

	public VRPinstance(File f) throws java.io.FileNotFoundException {
		this(new FileReader(f));
	}

	public VRPinstance(String filename) throws java.io.FileNotFoundException {
		this(new FileReader(filename));
	}




	/**
	 * Creates a VRP instance "by hand"
	 * Data are supposed to be consistent (size, etc.).
	 * @param matrix The distance matrix (size * size)
	 * @param demands The demand for each customer.
	 * @param capacity The capacity of the vehicles.
	 * @param size The size of the instance (number of customers)
	 */
	/*public VRPinstance(double[][] matrix, int[] demands, int capacity, int size) {
		super();
		this.matrix = matrix;
		this.demands = demands;
		this.capacity = capacity;
		this.size = size;
	}
	 */
	public VRPinstance(){

	}
	/**
	 * returns the matrix read
	 * 
	 * @return the matrix
	 */
	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * returns the number $n$ of customers (including the depot)
	 * 
	 * @return N
	 */
	public int getN() {
		return size;
	}

	public int getCapacity() {
		return capacity;
	}

	public int[] getDemands() {
		return demands;
	}

	public int getDemand(int customer) {
		return demands[customer];
	}

	public int getTrucks() {
		return trucks;
	}

	public int getOptimal() {
		return optimal;
	}

	public String getName() {
		return name;
	}


	public static void parse(String path) {

		VRPinstance instance = null;
		try {
			instance = new VRPinstance(path);
		} catch (java.io.FileNotFoundException e) {
			System.out.println("File not found");
		}

		System.out.println("Matrice de distances");
		matrix = instance.getMatrix();	
		for (int i = 1; i < instance.getN(); i++) {
			System.out.print(matrix[i][0] + " ");
		}
		System.out.println();

		demands = instance.getDemands();
		System.out.println("Demandes");
		for(int d : demands) System.out.print(d + " ");
		System.out.println();
		capacity = instance.getCapacity();
		System.out.println("Capacite : " + instance.getCapacity());

	}

}