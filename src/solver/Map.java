package solver;

import java.util.*;

import solver.branch_bound.*;
import solver.structs.Point;

public class Map {	
	public static byte[] map;	
	public static int N, numFlow;
	public static Point begin[], end[];
	
	public static HashMap<Character, Byte> flowId = new HashMap<>();
	public static ArrayList<Character> flowColor = new ArrayList<>();
	
	public static ForcedMove forcedMove;
	public static DeadEnd deadEnd;
	public static Stranded stranded;
	public static ChokePoint chokePoint;
	
	public static void initMap(String[] puzzle) {
		flowId.clear();
		flowColor.clear();
		map = decode(puzzle);
		begin = new Point[numFlow];
		end = new Point[numFlow];
		
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				byte f = map[i * N + j];
				if (f == -1)
					map[i * N + j] = -1;				
				else if (begin[f] == null)
					begin[f] = new Point(i, j);
				else {
					end[f] = new Point(i, j);
					map[i * N + j] = -2;
					
					if (Param.out_in && begin[f].getWallMinDist(N) > end[f].getWallMinDist(N)) {
						Point P = begin[f];
						begin[f] = end[f];
						end[f] = P;
						
						map[begin[f].getPos()] = f;
						map[end[f].getPos()] = -2;
					}
				}
				
			}
		
		forcedMove = new ForcedMove();
		deadEnd = new DeadEnd();
		stranded = new Stranded();
		chokePoint = new ChokePoint();
	}
	
	private static byte[] decode(String[] puzzle) {	
		numFlow = 0;
		N = puzzle[0].length();
		byte[] code = new byte[N * N];
		
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				char c = puzzle[i].charAt(j);
				if (c == '.')
					code[i * N + j] = -1;
				else if (flowId.containsKey(c))
					code[i * N + j] = flowId.get(c);
				else {					
					byte id = (byte)numFlow;
					flowId.put(c, id);
					code[i * N + j] = id;
					flowColor.add(c);
					numFlow++;
					
				}
			}
		
		return code;
	}
}
