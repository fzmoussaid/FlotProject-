package tsp;
import java.util.List;

/**
   Interface defining the behavior of a TSP heuristic

 */

public abstract class HeuristicTSP{
	
	public static long TIME_LIMIT = 2000;

	/** apply the heuristic to the TSP problem given as a matrix
	 * 
	 * @param matrix : TSP data
	 * @param solution an empty list that will be filled with the solution by the method 
	 * @return the value of the solution found
	 */
    public abstract double computeSolution(double[][] matrix, List<Integer> solution);

}