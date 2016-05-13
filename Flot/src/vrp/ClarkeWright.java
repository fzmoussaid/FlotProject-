package vrp;
import java.util.ArrayList;
import java.util.List;
import graph.Graph;
import tsp.HeuristicTSP;

public class ClarkeWright extends HeuristicVRP{
	
	/*graph*/
	private Graph graph;
	
	/*cost*/
	private double[][] cost;
	
	/* demand table */
	private int demands[] = null;

	/* Capacity of a vehicle */
	private int capacity = -1;

	private ArrayList<Trip> L;  

	private void init(HeuristicTSP h,VRPinstance vrp){
		graph = new Graph(vrp.getMatrix());
		cost = vrp.getMatrix();
		demands = vrp.getDemands();
		capacity = vrp.getCapacity();
		L = new ArrayList<Trip>();
		for (int i = 1; i < graph.size(); i++)
			L.add(new Trip(i, demands[i]));
	}

	public double computeSolution(HeuristicTSP h,VRPinstance vrp, List<Trip> result){
		init(h,vrp);
		double saving, bestSaving;
		Trip bestChoise1 = null, bestChoise2 = null;
		boolean repeat;
		long start = System.currentTimeMillis();
		do{
			bestSaving = 0;
			repeat = false;
			for(Trip t1: L){
				for(Trip t2: L){
					if (areFusable(t1,t2)){
						repeat = true;
						saving = computeSaving(t1,t2);
						if(saving > bestSaving){
							bestSaving = saving;
							bestChoise1 = t1;
							bestChoise2 = t2;
						}
					}
				}
			}
			if(repeat){
				fuse(bestChoise1, bestChoise2);
			}
			if(System.currentTimeMillis() - start > HeuristicVRP.TIME_LIMIT)
				return vrp.getOptimal();
		}while(repeat);
		double r=0;
		for(Trip t: L){
			result.add(t);
			r+=t.getCost(cost);
		}
		return r;
	}

	private boolean areFusable(Trip t1, Trip t2){

		for(Integer i: t1){
			if (t2.contains(i))
				return false;
		}

		if(t1.getDemand() + t2.getDemand() <= capacity)
			return true;
		else
			return false;		
	}

	private double computeSaving(Trip t1, Trip t2){
		Integer from = t1.getLast();
		Integer to = t2.getFirst();
		return cost[from][0]+cost[0][to]-cost[from][to];
	}

	private void fuse(Trip t1, Trip t2){
		for(Integer i : t2){
			t1.addToTrip(i, demands[i]);
		}
		L.remove(t2);
	}

}