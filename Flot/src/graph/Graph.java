package graph;

import java.util.Iterator;
import java.util.LinkedList;

public class Graph {
	private int N;
	public LinkedList<Integer> adj[];
	private LinkedList<Edge> edges;
	
	public Graph(int N){
		this.N = N;
		adj = new LinkedList[N];
		edges = new LinkedList<Edge>();
		
		for (int i=0; i < N; i++){
			adj[i] = new LinkedList<Integer>();
			
		}
	}


	public Graph(double[][] matrix){
		N = matrix.length;
		adj = new LinkedList[N];
		edges = new LinkedList<Edge>();
		
		for (int i=0; i < N; i++){
			adj[i] = new LinkedList<Integer>();
		}

		for (Integer i=0 ; i < N ; i++){
			for (Integer j=0 ; j < N ; j++){
				if(i!=j){
					adj[i].add(j);
					edges.add(new Edge(i,j, matrix[i][j]));
				}
			}	
		}
	}

	public void addEdge(Integer i, Integer j, double cost){
		for(Edge e : edges){
			if(e.source.equals(i) && e.target.equals(j))
				return;
		}
		
		for(Integer v : adj[i]){
			if(v.equals(j)){
				return;
			}
		}
		
		Edge a = new Edge(i, j, cost);
		edges.add(a);
		adj[i].add(j);
	}

	public void delEdge(Integer i, Integer j){
		for(Edge e : edges){
			if(e.source.equals(i) && e.target.equals(j))
				edges.remove(e);
		}
		for(Integer v : adj[i]){
			if(v.equals(j)){
				adj[i].remove(v);
				return;
			}
		}

	}

	public LinkedList<Edge> getEdges() {
		return edges;
	}

	public int size(){
		return N;
	}
	
	public void resetEdges(){
		edges.clear();
	}


	// A recursive function that uses visited[] and parent to detect
	// cycle in subgraph reachable from vertex v.
	Boolean isCyclicUtil(Integer v, Boolean visited[], Integer parent)
	{
		// Mark the current node as visited
		visited[v] = true;
		Integer i;

		// Recur for all the vertices adjacent to this vertex
		Iterator<Integer> it = adj[v].iterator();
		while (it.hasNext())
		{
			i = it.next();

			// If an adjacent is not visited, then recur for that
			// adjacent
			if (!visited[i])
			{
				if (isCyclicUtil(i, visited, v))
					return true;
			}

			// If an adjacent is visited and not parent of current
			// vertex, then there is a cycle.
			else if (!i.equals(parent))
				return true;
		}
		return false;
	}

	// Returns true if the graph contains a cycle, else false.
	public Boolean isCyclic()
	{
		// Mark all the vertices as not visited and not part of
		// recursion stack
		Boolean visited[] = new Boolean[N];
		for (int i = 0; i < N; i++)
			visited[i] = false;

		// Call the recursive helper function to detect cycle in
		// different DFS trees
		for (Integer u = 0; u < N; u++)
			if (!visited[u]) // Don't recur for u if already visited
				if (isCyclicUtil(u, visited, null))
					return true;
		return false;
	}
} 
