package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dijkstra{

	private final List<Edge> edges;
	private Set<Integer> settledNodes;
	private Set<Integer> unSettledNodes;
	private Map<Integer, Integer> predecessors;
	private Map<Integer, Double> distance;

	public Dijkstra(Graph graph) {
		this.edges = new ArrayList<Edge>(graph.getEdges());
		System.out.println(this.edges);
	}

	public void execute(Integer source) {
		settledNodes = new HashSet<Integer>();
		unSettledNodes = new HashSet<Integer>();
		distance = new HashMap<Integer, Double>();
		predecessors = new HashMap<Integer, Integer>();
		distance.put(source, (double) 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Integer node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Integer node) {
		List<Integer> adjacentNodes = getNeighbors(node);
		for (Integer target : adjacentNodes) {
			if (getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
				distance.put(target, getShortestDistance(node) + getDistance(node, target));
				predecessors.put(target, node);
				unSettledNodes.add(target);
			}
		}

	}

	private double getDistance(Integer node, Integer target) {
		for (Edge edge : edges) {
			if (edge.getSource() == node && edge.getTarget() == target) {
				return edge.getCost();
			}
		}
		throw new RuntimeException("Should not happen");
	}

	private List<Integer> getNeighbors(Integer node) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (Edge edge : edges) {
			if (edge.getSource() == node && !isSettled(edge.getTarget())) {
				neighbors.add(edge.getTarget());
			}
		}
		return neighbors;
	}

	private Integer getMinimum(Set<Integer> vertexes) {
		Integer minimum = null;
		for (Integer vertex : vertexes) {
			if (minimum == null) {
				minimum = vertex;
			} else {
				if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
					minimum = vertex;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Integer vertex) {
		return settledNodes.contains(vertex);
	}

	private Double getShortestDistance(Integer destination) {
		Double d = distance.get(destination);
		if (d == null) {
			return Double.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 * Source is not included in returned path, target included
	 */
	public LinkedList<Integer> getPath(Integer target) {
		LinkedList<Integer> path = new LinkedList<Integer>();
		Integer step = target;
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}

}