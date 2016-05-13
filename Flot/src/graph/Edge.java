package graph;

import java.util.LinkedList;

public class Edge implements Comparable<Edge> {
	public Integer source;
	public Integer target;
	public double cost;
	
	public Edge(Integer source, Integer target, double cost) {
		this.source = new Integer(source);
		this.target = new Integer(target);
		this.cost = cost;
	}
	
	public double getCost(){
		return cost;
	}
	
	public Integer getSource() {
		return source;
	}

	public Integer getTarget() {
		return target;
	}

	public String toString() {
		return "(" + source + "<->" + target + "|" + cost + ")";
	}
	
	
	public static Edge findEdge(LinkedList<Edge> list, Integer source, Integer target){
		for(Edge a : list){
			if(a.getSource().equals(source) && a.getTarget().equals(target)){
				return a;
			}
		}
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		return other.source == source && other.target == target;
	}

	@Override
	public int compareTo(Edge a) {
		if (cost > a.getCost())
			return 1;
		else if (cost == a.getCost())
			return 0;
		else
			return -1;
	}

}