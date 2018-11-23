package structs;

import java.util.ArrayList;

import branch_bound.ForcedMove;
import solver.Map;

public class Node {
	
	public State state;
	private int g, h;
	public Node parent;
	
	public void initNode() {
		state = new State();
		System.arraycopy(Map.map, 0, state.map, 0, Map.map.length);
		for (int c=0; c<Map.numFlow; c++)
			state.cur[c] = Map.begin[c].toByte();
		
		g = 0;
		state.free = Map.N * Map.N - Map.numFlow;
		
		for (int c=0; c<Map.numFlow; c++) {
			h += Point.getManDist(Map.begin[c], Map.end[c]);
		}
		parent = null;
	}
	
	public Node makeMove(int flow, int dir, int cost) {
		Node next = new Node();
		
		next.state = state.makeMove(flow, dir);
		if (next.state == null)
			return null;
		
		next.g = g + cost;		
		next.h = next.state.h();	
		next.parent = this;
		
		return next;
	}
	
	
	
	public ArrayList<Node> makeAllMoves() {
		ArrayList<Node> allMoves = new ArrayList<Node>();
		int activeFlow = -1, minMoves = 5;

		Node N = this;
		int forced = ForcedMove.findForcedMove(state);
		if (forced != -1) {
			//while (forced != -1) {
				N = N.makeMove(forced >> 2, forced & 3, 0);
				if (N == null)
					return allMoves;
				//forced = ForcedMove.findForcedMove(N.state);
				//N.state.printState();
			//}
			allMoves.add(N);
			return allMoves;
		}
		
		for (int c=0; c<Map.numFlow; c++) {
			int count = ForcedMove.moveCounts[c];
			if (count == -1)
				continue;
			if (count < minMoves) {
				minMoves = count;
				activeFlow = c;
			}
		}
		
		if (activeFlow == -1)
			return allMoves;
		
		for (int d=0; d<4; d++)
			if (state.tryMove(activeFlow, d)) {
				N = makeMove(activeFlow, d, minMoves==1?0:1);
				if (N != null)
					allMoves.add(N);
			}
				
		
		return allMoves;
	}
	
	public Integer f() {
		return g + h;
	}
	
	public boolean isGoal() {
		return state.isGoal();
	}
}
