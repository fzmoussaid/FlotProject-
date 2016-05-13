package tsp;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


/**
 * 
 * Utility class to test algorithms for the TSP.
 * Instances are loaded using methods load*. 
 * The different methods can then be run on all these instances.
 *
 */

public class TestTSP {

	private List<File> fileList = new ArrayList<File>();
	private PrintWriter writer;

	public TestTSP() {
		try {
			writer = new PrintWriter("TSPresults.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.println(String.format("%-16s%-16s%-16s","instanceName","Time","Distance"));

	}

	public TestTSP(String directory){
		try {
			writer = new PrintWriter("TSPresults.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loadFileList(directory);
		writer.println(String.format("%-16s%-16s%-16s","instanceName","Time","Distance"));
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
		if(f.getName().endsWith(".tsp")){fileList.add(f);}
		else System.out.println("File " + f.getName() + " not loaded (extension is not .tsp)");
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
	public List<Double> testHeuristic(HeuristicTSP h) {
		List<Double> listValues = new ArrayList<Double>();
		System.out.print("Working");
		long start;
		long finish;
		for (File f : fileList) {
			System.out.print(".");
			List<Integer> soluce = new ArrayList<Integer>();
			TSPData data = null;
			try {
				data = new TSPData(f);
			} catch (java.io.FileNotFoundException e) {
				System.out.println("File not found... Strange.");
			}
			start = System.currentTimeMillis();
			double val = h.computeSolution(data.getMatrix(), soluce);
			listValues.add(val);
			finish = System.currentTimeMillis() - start;
			writer.println(String.format("%-16s%-16s%-16s",data.getName(),finish,val));
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