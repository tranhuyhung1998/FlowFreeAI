package solver;

import java.util.*;

public class State {
	public byte[] map = new byte[Map.N * Map.N];
	public byte[] cur = new byte[Map.numFlow];
	
	public State() {
	}
	
	public State(State S) {
		System.arraycopy(S.map, 0, map, 0, S.map.length);
		System.arraycopy(S.cur, 0, cur, 0, S.cur.length);
	}
	
	@Override
	public boolean equals(Object obj) {
		State O = (State)obj;
		return Arrays.equals(map, O.map) && Arrays.equals(cur, O.cur);
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(map) + Arrays.hashCode(cur);
	}
	
	public boolean isInitial() {
		return Arrays.equals(map, Map.map);
	}
	
	public boolean isGoal() {
		for (int c=0; c<cur.length; c++)
			if (!(cur[c] == Map.end[c].toByte()))
				return false;
		return true;
	}
	
	public boolean tryMove(int flow, int dir) {
		Point pos = new Point(cur[flow]);
		
		pos.move(dir);
		if (!pos.isValid(Map.N)) 
			return false;

		if (map[pos.getPos()] == -1)
			return true;
		
		
		return false;
	}
	
	public void printState() {
		System.out.println();
		for (int i=0; i<Map.N; i++) {
			for (int j=0; j<Map.N; j++) {
				int f = map[i * Map.N + j];
				if (f == -1)
					System.out.print('.');
				else
					System.out.print(Map.flowColor.get(map[i * Map.N + j]));
			}
			System.out.println();
		}
		System.out.println();
	}
}
