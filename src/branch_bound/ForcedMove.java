package branch_bound;

import solver.*;

public class ForcedMove {
	private static boolean isForced(State S, int flow, int direct) {
		Point P = new Point(S.cur[flow], direct);
		if (P.equals(Map.end[flow]))
			return false;
		
		int countFree = 0;
		int countOtherEnds = 0;
		
		for (int dir=0; dir<4; dir++) {
			Point Q = new Point(P, dir);
			
			if (!Q.isValid(Map.N) || Q.toByte() == S.cur[flow])
				continue;
			int pos = Q.getPos();
			if (S.map[pos] == -1)
				countFree++;
			else if (S.map[pos] == -2) {
				if (!Q.equals(Map.end[flow]))
					countOtherEnds++;
			}
			else if (((S.finished & (1 << S.map[pos])) == 0) && Q.toByte() == S.cur[S.map[pos]])
				countOtherEnds++;
		}
		
		return (countFree == 1) && (countOtherEnds == 0);
	}
	
	public static int findMoves(State S, int flow) {
		int moveCount = 0, oneDir = 0;
		
		for (int dir=0; dir<4; dir++)
			if (S.tryMove(flow, dir)) {
				moveCount++;
				oneDir = dir;
				if (isForced(S, flow, dir)) {
					//System.out.println("Forced: " + Map.flowColor.get(flow) + " " + dir);
					//S.printState();
					return 4 | dir;
				}
			}
		return (moveCount << 2) | oneDir;
	}
}
