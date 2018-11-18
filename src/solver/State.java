package solver;

import java.util.*;

public class State {
	String map;
	ArrayList<Point> cur = new ArrayList<Point>();
	
	public State() {
		
	}
	
	public State(State S) {
		map = S.map;
		cur.addAll(S.cur);
	}
	
	@Override
	public boolean equals(Object obj) {
		State O = (State)obj;
		return map.equals(O.map) && cur.equals(O.cur);
	}
	
	@Override
	public int hashCode() {
		return map.hashCode() + cur.hashCode();
	}
	
	public boolean isInitial() {
		return map.equals(Map.map);
	}
	
	public boolean isGoal() {
		for (int c=0; c<Map.numFlow; c++)
			if (!cur.get(c).equals(Map.end[c]))
				return false;
		return true;
	}
	
	public boolean tryMove(int flow, int dir) {
		Point pos = new Point(cur.get(flow));
		
		pos.move(dir);
		if (!pos.isValid(Map.N)) 
			return false;

		if (map.charAt(pos.getPos()) == Map.dot)
			return true;
		
		
		return false;
	}
	
	public void printState() {
		System.out.println();
		for (int i=0; i<Map.N; i++) {
			for (int j=0; j<Map.N; j++) {
				char c = map.charAt(i * Map.N + j);
				if (c == Map.dot)
					c = '.';
				else
					c += 'a';
				System.out.print(c);
			}
			System.out.println();
		}
		System.out.println();
	}
}
