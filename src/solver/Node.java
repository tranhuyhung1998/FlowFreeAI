package solver;

import java.util.ArrayList;

import branch_bound.ForcedMove;

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
		int activeFlow = -1, minWall = Map.N;

		for (int c=0; c<Map.numFlow; c++) {
			if ((state.finished & (1 << c)) != 0) continue;
			
			int moveCode = ForcedMove.findMoves(state, c);
			int moveCount = moveCode >> 2;
			if (moveCount == 1) {
				Node N = makeMove(c, moveCode & 3, 0);
				if (N != null)
					allMoves.add(N);
				return allMoves;
			}
			
			int dist = new Point(state.cur[c]).getWallMinDist(Map.N);
			if (dist < minWall) {
				minWall = dist;
				activeFlow = c;
			}
		}	
		
		if (activeFlow == -1)
			return allMoves;
		
		for (int d=0; d<4; d++)
			if (state.tryMove(activeFlow, d)) {
				Node N = makeMove(activeFlow, d, 1);
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
