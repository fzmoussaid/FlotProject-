package vrp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import tsp.HeuristicTSP;

public class TestVRP {
	
	private List<File> fileList = new ArrayList<File>();
	private PrintWriter writer;

	
	public TestVRP(){

		try {
			writer = new PrintWriter("VRPresults.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(String.format("%-16s%-16s%-16s%-16s%-16s%-16s","instanceName","Time","Truks","OptiTrucks","Distance", "OptiDistance"));
	}
	
	public TestVRP(String directory) {
		try {
			writer = new PrintWriter("VRPresults.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadFileList(directory);
		writer.println(String.format("%-16s%-16s%-16s%-16s%-16s%-16s","instanceName","Time","Truks","OptiTrucks","Distance", "OptiDistance"));
	}

	
	/**
	 * Load the files of the directory into the list of files
	 * @param directoryName le nom du répertoire
	 */
	public void loadFileList(String directoryName) {
		File directory = new File(directoryName);
		loadFileList(directory);
	}

	private void loadFileList(File directory) {
		if (directory.isDirectory()) {
			File[] files = directory.listFiles();
			for (File f : files) {
				loadFile(f);
			}
		}
	}

	/**
	 * Load file f into the list of instances.
	 * @param f the file to load
	 */
	void loadFile(File f) {
		if(f.getName().endsWith(".vrp")){fileList.add(f);}
		else System.out.println("File " + f.getName() + " not loaded (extension is not .vrp)");
	}

	/**
	 * Load the file of name fileName into the list of instances
	 * @param fileName the name of the file to load
	 */
	public void loadFile(String fileName){
		loadFile(new File(fileName));
	}
	
	
	/**
	 * 
	 * @return a list containing the names of the files loaded
	 */
	public List<String> getFileNames(){
		List<String> fileNames = new ArrayList<String>();
		for(File f : fileList){
			fileNames.add(f.getName());
		}
		return fileNames;
	}


    
    /**
     * Test the heuristic procedure.
     * The method will be run on each instance previously loaded.
     * The value found for each instance is put in the list in the same order the instances were entered.
     */
	public List<Double> testHeuristic(HeuristicVRP h, HeuristicTSP tsp) {
		List<Double> listValues = new ArrayList<Double>();
		long start;
		long finish;
		System.out.print("Working");
		for (File f : fileList) {
			List<Trip> soluce = new ArrayList<Trip>();
			VRPinstance data = null;
			try {
				data = new VRPinstance(f);
			} catch (java.io.FileNotFoundException e) {
				System.out.println("File not found... Strange.");
			}
			System.out.print(".");
			start = System.currentTimeMillis();
			double val = h.computeSolution(tsp, data, soluce);
			finish = System.currentTimeMillis() - start;
			//Relative Error
			listValues.add(Math.abs(val-data.getOptimal())/data.getOptimal());
			writer.println(String.format("%-16s%-16s%-16s%-16s%-16s%-16s",data.getName(),finish,soluce.size(),data.getTrucks(),val,data.getOptimal()));
		}
		System.out.println("Done");
		writer.close();
		return listValues;
	}

    
	/**
	 * Returns the average value of the list
	 */
	public static double avgVal(List<Double> results) {
		double avg = 0.0;
		for (double ele : results) {
			avg += ele;
		}
		return avg / (double) results.size();
	}


}
