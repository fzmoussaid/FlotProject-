package tsp;
import java.util.List;

public class MainTSP {

	private static char stringtoCode(String s){
		if(s.equals("-h1") || s.equals("--heuristic1")) return 'h';
		if(s.equals("-h2") || s.equals("--heuristic2")) return 'H';
		if(s.equals("-e")  || s.equals("--exact")) return 'e';
		if(s.equals("-l1") || s.equals("--lowerbound")) return 'l';
		return '0';
	}
	
	
	/** run the test 
	 * 
	 * Syntax : TestTSP -{h1,h2,e,l} instanceName timeLimit
	 * 
	 * h1 : closest neighbor
	 * h2 : arc insertion heuristic
	 * e  : branch and bound
	 * l  : lower bound
	 * 
	 * Parameter timeLimit is only used when the first parameter is e
	 * 
	 * */
	public static void main(String args[]) {
		System.out.println(">>>>WARNING: INSTANCES MUST BE IN data/TSPinstances<<<<");
		System.out.println("Syntax : TestTSP -{h1,h2,e,l} instanceName/ALL");
		if(args.length < 2){
			System.out.println("Argument Error\nSyntax : TestVRP -{h1,h2,e,l} instanceName/ALL timeLimit");
			return;
		}
		
		if(args.length == 3){
			HeuristicTSP.TIME_LIMIT = Long.parseLong(args[2]);	
		}
		
		List<Double> listRes; // list of results
		TestTSP tt;
		if(args[1].equals("ALL"))
			tt = new TestTSP("data/TSPinstances");
		else{
			tt = new TestTSP();
			tt.loadFile("data/TSPinstances/"+args[1]);
		}		
		
		switch(stringtoCode(args[0])){		
		case 'h' : // heuristic
			listRes = tt.testHeuristic(new InsertHeuristicTSP());
			System.out.println("Heuristic insertion : " + TestTSP.avgVal(listRes)+ " on average");
			System.out.println("See result file for more details");
			break;
		case 'H' : // heuristic
			listRes = tt.testHeuristic(new DecreasingArcHeuristicTSP());
			System.out.println("Heuristic decreasing arcs : " + TestTSP.avgVal(listRes)+ " on average");
			System.out.println("See result file for more details");
			break;			
		default :  // error
			System.out.println("Argument Error\nSyntax : TestTSP -{h1,h2,e,l} instanceName/ALL timeLimit");
		}
	}

	
	
	
}
