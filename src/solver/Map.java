package solver;

import java.util.HashMap;

public class Map {
	public static final char dot = Character.MAX_VALUE;
	
	public static String map;
	
	public static int N, numFlow;
	public static Point begin[], end[];
	
	public static void initMap(String[] puzzle) {
		char[] code = decode(puzzle);

		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				int id = code[i * N + j];
				if (id == dot || numFlow >= id) continue;
				numFlow = id;
			}
		numFlow++;
		
		begin = new Point[numFlow];
		end = new Point[numFlow];
		
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				int f = code[i * N + j];
				if (f == dot) continue;
				
				if (begin[f] == null)
					begin[f] = new Point(i, j);
				else {
					end[f] = new Point(i, j);
					code[i * N + j] = dot;
				}
				
			}
		map = new String(code);
	}
	
	private static char[] decode(String[] puzzle) {
		HashMap<Character, Integer> flowId = new HashMap<>();
		int flow = 0;
		N = puzzle[0].length();
		char[] code = new char[puzzle.length * puzzle.length];
		
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++) {
				char c = puzzle[i].charAt(j);
				if (c == '.')
					code[i * N + j] = dot;
				else if (flowId.containsKey(c))
					code[i * N + j] = (char)flowId.get(c).intValue();
				else {
					flowId.put(c, flow);
					code[i * N + j] = (char)flow;
					flow++;
				}
			}
		
		return code;
	}
}
