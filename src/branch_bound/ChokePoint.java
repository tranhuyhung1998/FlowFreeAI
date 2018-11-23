package branch_bound;

import solver.*;
import structs.Point;
import structs.State;

public class ChokePoint {
	private static State tempS;
	
	public static void init() {
		tempS = new State();
	}
	
	public static boolean exist(State S) {
		int lastFlow = S.last;		
		Point lastPoint = new Point(S.cur[lastFlow]);
		
		for (int dir=0; dir<4; dir++) {
			Point filler = new Point(lastPoint, dir);
			if (!filler.isValid(Map.N) || S.map[filler.getPos()] != -1)
				continue;
			
			tempS.copy(S);
			int bottleneckSize = 0;
			while (filler.isValid(Map.N) && tempS.map[filler.getPos()] == -1) {
				tempS.map[filler.getPos()] = (byte)lastFlow;
				bottleneckSize++;
				filler.move(dir);
			}
			filler.move((dir + 2) >> 2);
			tempS.cur[lastFlow] = filler.toByte();
			
			if (Stranded.check(tempS, lastFlow) > bottleneckSize)
				return true;
				
		}
		
		return false;
	}
}
