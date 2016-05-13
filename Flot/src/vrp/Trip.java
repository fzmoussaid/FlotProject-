package vrp;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Trip extends LinkedList<Integer> {
	private static final long serialVersionUID = 1L;
	private int demand;
	
	Trip(){
		super();
	}
	
	Trip(List<Integer> l, int d){
		super(l);
		demand = d;
	}
	
	Trip(Integer i, int d){
		super();
		add(i);
		demand = d;
	}
	
	int getDemand(){
		return demand;
	}
	
	void addToTrip(Integer i, int d){
		add(i);
		demand+=d;
	}
	
	double getCost(double matrix[][]){
		double cost = 0;
		Iterator<Integer> itr = this.iterator();
		Integer current = itr.next();
		Integer next = current;
		while(itr.hasNext()){
			next = itr.next();
			cost+=matrix[current][next];
			current = next;
		}
		
		cost+= matrix[0][this.getFirst()];
		cost+= matrix[this.getLast()][0];
		return cost;
	}
}
