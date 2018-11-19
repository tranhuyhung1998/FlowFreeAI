package solver;

import java.util.*;

public class Map {	
	public static byte[] map;	
	public static int N, numFlow;
	public static Point begin[], end[];
	
	public static HashMap<Character, Byte> flowId = new HashMap<>();
	public static ArrayList<Character> flowColor = new ArrayList<>();
	
	public static void initMap(String[] puzzle) {
		map = decode(puzzle);
		begin = new Point[numFlow];
		end = new Point[numFlow];
		
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				int f = map[i * N + j];
				if (f == -1) continue;
				
				if (begin[f] == null)
					begin[f] = new Point(i, j);
				else {
					end[f] = new Point(i, j);
					map[i * N + j] = -1;
				}
				
			}
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
