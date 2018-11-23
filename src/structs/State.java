package structs;

import java.util.*;
import branch_bound.*;
import solver.Map;

public class State {
	public byte[] map;
	public byte[] cur;
	
	public int free, last = 0, finished = 0;
	
	public State() {
		map = new byte[Map.N * Map.N];
		cur = new byte[Map.numFlow];
	}
	
	public State(State S) {
		map = new byte[Map.N * Map.N];
		cur = new byte[Map.numFlow];
		copy(S);
	}
	
	public void copy(State S) {
		System.arraycopy(S.map, 0, map, 0, S.map.length);
		System.arraycopy(S.cur, 0, cur, 0, S.cur.length);
		free = S.free;
		last = S.last;
		finished = S.finished;
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
		return (finished == ((1 << Map.numFlow) - 1)) && (free == 0);
	}
	
	public boolean tryMove(int flow, int dir) {
		Point P = new Point(cur[flow], dir);
		
		if (!P.isValid(Map.N)) 
			return false;	
		if (map[P.getPos()] != -1 && !P.equals(Map.end[flow]))
			return false;
		
		for (int d=0; d<4; d++) {
			Point Q = new Point(P, d);
			if (!Q.isValid(Map.N) || map[Q.getPos()] == -1)
				continue;
			if (map[Q.getPos()] == flow && Q.toByte() != cur[flow] && !Q.equals(Map.end[flow]))
				return false;
		}
		return true;
	}
	
	public State makeMove(int flow, int dir) {
		State next = new State(this);
		
		Point newPos = new Point(cur[flow], dir);
		
		if (newPos.equals(Map.end[flow]))
			next.finished |= (1 << flow);

		next.cur[flow] = newPos.toByte();
		
		next.map[newPos.getPos()] = (byte)flow;
		
		next.free--;
		next.last = flow;
		
		if (next.isUnsolvable())
			return null;
		
		return next;
	}
	
	private int h_Manhattan() {
		int h = 0;
		for (int c=0; c<Map.numFlow; c++)
			h += Point.getManDist(new Point(cur[c]), Map.end[c]);
		return h;
	}
	
	private int h_Wall() {
		int h = 0;
		for (int c=0; c<Map.numFlow; c++)
			h += new Point(cur[c]).getWallMinDist(Map.N);
		return h;
	}
	
	public int h() {
		return free + h_Manhattan();
	}
	
	public void printState() {
		System.out.println();
		for (int i=0; i<Map.N; i++) {
			for (int j=0; j<Map.N; j++) {
				int b = map[i * Map.N + j];
				if (b == -1)
					System.out.print(".");
				else if (b == -2)
					System.out.print("?");
				else if (Map.begin[b].equals(new Point(i, j)) || Map.end[b].equals(new Point(i, j)))
					System.out.print(Map.flowColor.get(b));
				else
					System.out.print((char)(Map.flowColor.get(b) + 32));
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public boolean isUnsolvable() {
		if (DeadEnd.exist(this))
			return true;
		if (Stranded.check(this, -1) != 0)
			return true;
		if (ChokePoint.exist(this))
			return true;
		return false;
	}
}
