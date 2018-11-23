package branch_bound;

import java.util.ArrayList;

import solver.*;
import structs.Point;
import structs.State;

public class ForcedMove {
	public static int[] moveCounts;
	
	public static void init() {
		moveCounts = new int[Map.numFlow];
	}
	
	private static boolean isForced(State S, int flow, int direct) {
		Point P = new Point(S.cur[flow], direct);
		if (P.equals(Map.end[flow]))
			return true;
		
		int countFree = 0;
		int countOtherEnds = 0;
		
		for (int dir=0; dir<4; dir++) {
			Point Q = new Point(P, dir);
			
			if (!Q.isValid(Map.N) || Q.toByte() == S.cur[flow] || Q.equals(Map.end[flow]))
				continue;
			int pos = Q.getPos();
			if (S.map[pos] == -1)
				countFree++;
			else if (S.map[pos] == -2)
				countOtherEnds++;
			else if (((S.finished & (1 << S.map[pos])) == 0) && Q.toByte() == S.cur[S.map[pos]])
				countOtherEnds++;
		}
		
		return (countFree == 1) && (countOtherEnds == 0);
	}
	
	public static int findForcedDir(State S, int flow) {
		int moveCount = 0;
		
		for (int dir=0; dir<4; dir++)
			if (S.tryMove(flow, dir)) {
				moveCount++;
				if (isForced(S, flow, dir)) {
					//System.out.println("Forced: " + Map.flowColor.get(flow) + " " + dir);
					//S.printState();
					return dir;
				}
			}
		moveCounts[flow] = moveCount;
		return -1;
	}
	
	public static int findForcedMove(State S) {
		for (int c=0; c<Map.numFlow; c++) {
			if ((S.finished & (1 << c)) != 0) {
				moveCounts[c] = -1;
				continue;
			}
			int dir = findForcedDir(S, c);
			if (dir != -1)
				return (c << 2) | dir;
		}
		
		return -1;
	}
	
}
