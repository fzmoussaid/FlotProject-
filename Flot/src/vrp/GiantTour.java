package vrp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import graph.Dijkstra;
import graph.Graph;
import tsp.HeuristicTSP;

public class GiantTour extends HeuristicVRP{
	/*graph*/
	private int N;

	private Graph g;

	/*cost*/
	private double[][] cost;

	/* demand table */
	private int demands[] = null;

	/* Capacity of a vehicle */
	private int capacity = -1;

	private ArrayList<Trip> L;

	private ArrayList<Integer> tspTour;

	double tspCost;

	private void init(HeuristicTSP h,VRPinstance vrp){
		N = vrp.getN();
		g = new Graph(N);
		cost = vrp.getMatrix();
		demands = vrp.getDemands();
		capacity = vrp.getCapacity();
		L = new ArrayList<Trip>();
		tspTour = new ArrayList<Integer>();
		tspCost = h.computeSolution(cost, tspTour);

	}

	public double computeSolution(HeuristicTSP h,VRPinstance vrp, List<Trip> result){
		init(h,vrp);
		// each edge is a possible trip with, the cost is the total cost of the trip
		int idx0 = tspTour.indexOf((Integer)0);
		
		//Change tspTour to start from vertex 0
		ArrayList<Integer> tmp = new ArrayList<Integer>();
		for(int k = idx0; k < N; k++){
			tmp.add(tspTour.get(k));
		}
		for(int k = 0; k < idx0; k++){
			tmp.add(tspTour.get(k));
		}
		tspTour = tmp;

		Integer current, next, last;
		for(Integer i = 1 ; i < N; i++){
			last = tspTour.get(i-1);
			current = tspTour.get(i);
			g.addEdge(last, current, 2*cost[0][current]);
			for(Integer j = i+1; j < N; j++){
				next = tspTour.get(j);
				if(onTrip(current, next))
					g.addEdge(current, next,tripCost(i,j));
			}
		}
		
		
//		
//		for(Integer i : tspTour){
//			for(Integer j : tspTour){
//				if(tspTour.indexOf(j) >= tspTour.indexOf(i) && onTrip(i,j) && i.equals(0) && i.equals(0))
//					g.addEdge(i, j, tripCost(i, j));
//			}
//		}
//		int idx;
//		for(Integer v : tspTour){
//			idx = tspTour.indexOf(v)+1;
//			if(idx > N-1)
//				break;
//			Integer w = tspTour.get(idx);
//			g.addEdge(v,w, 2*cost[0][w]);
//		}
		
		Dijkstra algo = new Dijkstra(g);
		algo.execute(0);
		LinkedList<Integer> path = algo.getPath(tspTour.get(2));
		System.out.println(tspTour);
		Iterator<Integer> itr = path.iterator();
		last = (Integer) itr.next();
		do{
			next = (Integer) itr.next();
			L.add(genTrip(last,next));
		}while(itr.hasNext());
		
		System.out.println("Trucks= "+L.size());
		return 0;
	}

	private Trip genTrip(Integer i, Integer j){
		int k = 0;		
		while(tspTour.get(k) != i)
			k++;

		Integer v;
		Trip t = new Trip();
		for (int l = k+1; l < N ; l++){
			v = tspTour.get(l);
			t.addToTrip(v, demands[v]);
			if(v == j)
				break;
		}
		return t;
	}

	private boolean onTrip(Integer i, Integer j){
		int d = 0;
		if(i.equals(j))
			return demands[i] <= capacity;
		
		int start = tspTour.indexOf(i);
		int finish = tspTour.indexOf(j);
		for( int v = start; v <= finish; v++){
			d+=demands[v];
		}
		
		if(d <= capacity)
			return true;
		else
			return false;
	}

	private double tripCost(Integer i, Integer j){
		double d = 0;
		if (i.equals(j)){
			return cost[0][i]*2;

		}
		int start = tspTour.indexOf((Integer)i);
		int finish = tspTour.indexOf((Integer)j);
		int next;
		for(int v = start; v < finish; v++){
			next = tspTour.get(v+1);
			d+=cost[v][next];
		}
		d+= cost[0][i];
		d+= cost[j][0];
		return d;
	}

}
