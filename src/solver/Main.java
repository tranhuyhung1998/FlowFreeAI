package solver;

import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
	public static void main(String args[]) throws Exception {
		File puzzles = new File("puzzles");
		FileWriter output = new FileWriter("test/Solution.txt");
		
		//for (File test: puzzles.listFiles()) {
		File test = new File("puzzles/extreme_8x8_01.txt");
			System.out.println(test.getName());
			long time = System.currentTimeMillis();
			
			AStar sol = new AStar(test);
			AtomicInteger nodeCount = new AtomicInteger();
			String status = sol.solve(nodeCount);
			
			time = System.currentTimeMillis() - time;
			
			output.write(test.getName() + " " + 
						status + " " + 
						nodeCount + " " + 
						(1.0 * time / 1000) + "s" + 
						System.lineSeparator());
			System.out.println(status);
		//}
		
		output.close();
	}
}
