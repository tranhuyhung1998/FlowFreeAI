package solver;

public class Main {
	public static void main(String args[]) throws Exception {
		AStar sol = new AStar("Puzzle.txt");
		AStar.makeSolution(sol.Solution());
	}
}
