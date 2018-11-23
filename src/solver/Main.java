package solver;

import java.io.FileWriter;

public class Main {
	public static void main(String args[]) throws Exception {
		AStar sol = new AStar("Puzzle.txt");
		AStar.makeSolution(sol.Solution(), new FileWriter("Solution.txt"));
	}
}
