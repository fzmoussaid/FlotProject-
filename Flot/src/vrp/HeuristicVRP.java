package vrp;

import java.util.List;

import tsp.HeuristicTSP;

/**
* Interface defining the behavior of a TSP heuristic
*/

public abstract class HeuristicVRP {
	
	/** apply the heuristic to the VSP problem given to constructor
	 * 
	 * @param L an empty list that will be filled with the solution by the method 
	 * @return the value of the solution found
	 */
	public static long TIME_LIMIT = 2000; 
	public abstract double computeSolution(HeuristicTSP h,VRPinstance vrp, List<Trip> L);
}
