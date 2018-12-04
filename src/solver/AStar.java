// NHAP XUAT DU LIEU VA XU LY A*

package solver;

import java.util.*;

import solver.structs.*;

import java.io.*;

public class AStar {
	
	// Tap "open" trong ly thuyet
	private PriorityQueue<Node> open = new PriorityQueue<>(new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o1.f().compareTo(o2.f());
		};
	});

	// Tap "closed" trong ly thuyet
	public HashSet<State> closed = new HashSet<>(); 
	
	// Nhap du lieu tu file
	public AStar(File test) throws Exception {
		Scanner inp = new Scanner(test);
		
		String one = inp.nextLine();
		int N = one.length();

		String[] puzzle = new String[N];
		puzzle[0] = one;
		for (int i=1; i<N; i++)
			puzzle[i] = inp.nextLine();
		
		Map.initMap(puzzle);
		inp.close();
	}
	
	
	// Giai thuat A*
	public String solve() {
		int nodeCount = 0;
		Node initial = new Node();
		initial.initNode();
		open.add(initial);
		
		while (!open.isEmpty()) {
			
			Node P = open.poll();
			closed.add(P.state);
			
			//P.state.printState();
			++nodeCount;
			
			if (nodeCount == Param.maxNode)
				return "LimitExceed";
			
			if (P.isGoal())
				//P.state.printState();
				return "Solved";
			
			ArrayList<Node> nodeList = P.makeAllMoves();

			for (Node Q: nodeList) {
				if (closed.contains(Q.state) || open.contains(Q))
					continue;
				
				open.add(Q);
			}
		}

		return "NoSolution";
	}
	
	// Xuat ket qua ra file
	public static void makeSolution(Node P, FileWriter res) throws Exception {
		
		if (P == null)
			res.write("No Solution!");
		else {
			res.write("Solution:" + System.lineSeparator());
			while (P.parent != null) {
				Node parent = P.parent;
				int last = P.state.last;
				
				Point cur = new Point(P.state.cur[last]);
				Point past = new Point(parent.state.cur[last]);
				
				res.write(new String(cur.x + " " + cur.y + " " + cur.howToMove(past) + System.lineSeparator()));
				
				P = parent;
			}
		}
		
		res.close();
	}
}
