package tsp;

import graph.*;
import java.util.Collections;
import java.util.List;

/**
 * This heuristic sorts the arcs by increasing value and 
 * considers each arc in turn for insertion
 * An arc is inserted if and only if it does not create a subtour.
 * The method stops when a tour is obtained.
 */
public class DecreasingArcHeuristicTSP extends HeuristicTSP {
	private Graph graph;
	
	public double computeSolution(double[][] matrix, List<Integer> solution) {
		if(matrix==null)
			return Double.POSITIVE_INFINITY;
		graph = new Graph(matrix);
		Collections.sort(graph.getEdges());
		Graph tmp = new Graph(matrix.length);
		int journey = 0;
		double cost = 0;
		long start = System.currentTimeMillis();
		for (Edge edge : graph.getEdges()){
			tmp.addEdge(edge.source, edge.target, edge.cost);
			journey++;
			if (tmp.isCyclic() && journey < tmp.size()){
				tmp.delEdge(edge.source, edge.target);
				journey--;
			} else {
				cost+=edge.getCost();
			}
			if(System.currentTimeMillis() - start > HeuristicTSP.TIME_LIMIT)
				return Double.POSITIVE_INFINITY;
		}
		Integer current = new Integer(1);
		Integer before = new Integer(1);
		Integer after = tmp.adj[current].getFirst();
		do{
			solution.add(current);
			if (tmp.adj[current].getFirst().equals(before))
				after = tmp.adj[current].getFirst();
			else
				after = tmp.adj[current].getLast();
			before = current;
			current = after;
			if(System.currentTimeMillis() - start > HeuristicTSP.TIME_LIMIT)
				return Double.POSITIVE_INFINITY;
		}while(current!= 1);
		return cost;
	}
	
	/**
	 * generate an arc list from graph matrix
	 * @param matrix
	 * @return List<Edge>
	 */
	
}