package solver;

import java.util.*;

import structs.Node;
import structs.Point;
import structs.State;

import java.io.*;

public class AStar {
	
	PriorityQueue<Node> open = new PriorityQueue<>(new Comparator<Node>() {
		public int compare(Node o1, Node o2) {
			return o1.f().compareTo(o2.f());
		};
	});

	HashSet<State> closed = new HashSet<>(); 
	
	public AStar(String fileName) throws Exception {
		Scanner inp = new Scanner(new File(fileName));
		
		String one = inp.nextLine();
		int N = one.length();

		String[] puzzle = new String[N];
		puzzle[0] = one;
		for (int i=1; i<N; i++)
			puzzle[i] = inp.nextLine();
		
		Map.initMap(puzzle);
		inp.close();
	}
	
	public Node Solution() {
		int nodeCount = 0;
		Node initial = new Node();
		initial.initNode();
		open.add(initial);
		
		while (!open.isEmpty()) {
			
			Node P = open.poll();
			//closed.add(P.state);
			
			//P.state.printState();
			System.out.println(++nodeCount);
			
			if (P.isGoal()) {
				P.state.printState();
				return P;
			}
			
			ArrayList<Node> nodeList = P.makeAllMoves();

			for (Node Q: nodeList) {
				//if (closed.contains(Q.state) /*|| open.contains(Q)*/)
					//continue;
				
				open.add(Q);
			}
		}

		return null;
	}
	
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
