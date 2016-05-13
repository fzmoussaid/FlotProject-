package vrp;
import java.util.List;
import tsp.InsertHeuristicTSP;

public class MainVRP {
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
		System.out.println(">>>>WARNING: INSTANCES MUST BE IN data/VRPinstances<<<<");
		System.out.println("Syntax : TestVRP -{h1,h2,e,l} instanceName/ALL");
		if(args.length < 2){
			System.out.println("Argument Error\nSyntax : TestVRP -{h1,h2,e,l} instanceName timeLimit");
			return;
		}
		
		if(args.length == 3){
			HeuristicVRP.TIME_LIMIT = Long.parseLong(args[2]);	
		}
		
		List<Double> listRes; // list of results
		TestVRP tt;
		if(args[1].equals("ALL"))
			tt = new TestVRP("data/VRPinstances");
		else{
			tt = new TestVRP();
			tt.loadFile("data/VRPinstances/"+args[1]);
		}		

		switch(stringtoCode(args[0])){		
		case 'h' : // heuristic
			listRes = tt.testHeuristic(new ClarkeWright(), new InsertHeuristicTSP());
			System.out.println("Heuristic Clarke and Wright Relative Error: " + TestVRP.avgVal(listRes)*100 + "% on average");
			System.out.println("See result file for more details");
			break;
		case 'H' : // heuristic
			System.out.println("Heuristic GiantTour Not supported yet");
			//istRes = tt.testHeuristic(new GiantTour(), new InsertHeuristicTSP());
			//System.out.println("Heuristic Giant Tour: " + TestVRP.avgVal(listRes) + " on average");
			break;			
		default :  // error
			System.out.println("Argument Error\nSyntax : TestTSP -{h1,h2,e,l} instanceName timeLimit");
		}
	}




}
