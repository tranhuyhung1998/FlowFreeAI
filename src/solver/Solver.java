package solver;

import java.util.*;
import java.io.*;

public class Solver {
	
	PriorityQueue<Node> open = new PriorityQueue<>(new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o1.f().compareTo(o2.f());
		};
	});
	HashMap<State, Node.Tracer> closed = new HashMap<>();
	
	
	
	public Solver() {
		Scanner inp = null;
		try {
			inp = new Scanner(new File("Puzzle.txt"));
		} catch (FileNotFoundException e) {
		}
		String one = inp.nextLine();
		int N = one.length();

		String[] puzzle = new String[N];
		puzzle[0] = one;
		for (int i=1; i<N; i++)
			puzzle[i] = inp.nextLine();
		
		Map.initMap(puzzle);
	}
	
	public void Solution() {
		Node initial = new Node();
		initial.initNode();
		open.add(initial);
		
		while (!open.isEmpty()) {
			
			Node P = open.poll();
			closed.put(P.state, P.T);
			
			P.state.printState();
			
			if (P.isGoal()) {
				makeSolution(P.state);
				return;
			}
			
			ArrayList<Node> nodeList = P.makeAllMoves();
			if (nodeList == null)
				continue;
			for (Node Q: nodeList) {
				if (closed.containsKey(Q.state) /*|| open.contains(Q)*/)
					continue;
				
				open.add(Q);
			}
		}

	}
	
	private void makeSolution(State S) {
		FileWriter res = null;
		try {
			res = new FileWriter("Solution.txt");
		} catch (IOException e) {}
		
		State temp = new State(S);
		
		while (!temp.isInitial()) {
			Node.Tracer T = closed.get(temp);
			Point lastPoint = temp.cur.get(T.flow);
			int pos = lastPoint.getPos();
			
			try {
				res.write(new String(lastPoint.x + " " + lastPoint.y + " " + ((T.dir +2) % 4) + System.lineSeparator()));
			} catch (IOException e) {}
			temp.map = temp.map.substring(0, pos) + Map.dot + temp.map.substring(pos + 1);
			
			lastPoint.move((T.dir +2) % 4);
		}
		
		try {
			res.close();
		} catch (IOException e) {
		}
	}
}
