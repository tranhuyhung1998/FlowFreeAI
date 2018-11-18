package solver;

import java.util.ArrayList;

public class Node {
	public static class Tracer {
		public int flow, dir;
		
		public Tracer(int flow, int dir) {
			this.flow = flow;
			this.dir = dir;
		}
	}
	
	public State state = new State();
	private int g, h;
	public Tracer T;
	
	public void initNode() {
		state.map = Map.map;
		for (int c=0; c<Map.numFlow; c++)
			state.cur.add(Map.begin[c]);
		
		g = 0;
		h = Map.N * Map.N - Map.numFlow;
		
		for (int c=0; c<Map.numFlow; c++) {
			h += Point.getManDist(Map.begin[c], Map.end[c]);
		}
		
		T = new Tracer(-1, -1);
	}
	
	public Node makeMove(int flow, int dir, int cost) {
		Node next = new Node();
		
		Point newPos = new Point(state.cur.get(flow));
		newPos.move(dir);
		for (int c=0; c<Map.numFlow; c++)
			if (c == flow)
				next.state.cur.add(newPos);
			else
				next.state.cur.add(state.cur.get(c));
		
		int pos = newPos.getPos();
		
		next.state.map = state.map.substring(0, pos) + (char)flow + state.map.substring(pos + 1);
		
		next.g = g + cost;		
		
		next.h = h - Point.getManDist(state.cur.get(flow), Map.end[flow]) 
				+ Point.getManDist(next.state.cur.get(flow), Map.end[flow]) - 1;
		
		//next.h = h - 1;
		
		next.T = new Tracer(flow, dir);
		
		return next;
	}
	
	
	
	public ArrayList<Node> makeAllMoves() {
		int activeFlow = -1, maxMoves = 0;
		
		for (int c=0; c<Map.numFlow; c++) {
			if (state.cur.get(c).equals(Map.end[c])) continue;
			
			int moveCount = 0;
			for (int d=0; d<4; d++)
				if (state.tryMove(c, d))
					moveCount++;
			
			if (moveCount > maxMoves) {
				maxMoves = moveCount;
				activeFlow = c;
			}
		}
		
		if (maxMoves == 0)
			return null;
		
		ArrayList<Node> allMoves = new ArrayList<Node>();
		for (int d=0; d<4; d++)
			if (state.tryMove(activeFlow, d))
				allMoves.add(makeMove(activeFlow, d, (maxMoves==1 ? 0 : 1)));
		
		return allMoves;
	}
	
	public Integer f() {
		return g + h;
	}
	
	public boolean isGoal() {
		return h == 0;
		//return h == 0 && state.isGoal();
	}
}
