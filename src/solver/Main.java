package solver;

import java.io.*;
import java.util.Scanner;

public class Main {
	public static void run(int outName) throws Exception {
		File puzzles = new File("puzzles");
		FileWriter output = new FileWriter("Solution" + outName + ".txt");
		
		int testCnt = 0;
		for (File test: puzzles.listFiles()) {
		//File test = new File("puzzles/8x8_02.txt");
			Scanner bound = new Scanner(test);
			if (bound.nextLine().length() > Param.maxTestSize)
				continue;
			bound.close();
			
			System.out.println(test.getName());
			//long time = System.currentTimeMillis();
			
			output.write(test.getName());
			for (int i=0; i<5; i++) {
				System.out.println("\tRunning h" + i + "...");
				Param.h = i;
				
				AStar sol = new AStar(test);
				sol.solve();
				
				//time = System.currentTimeMillis() - time;
				int nodeClosed = sol.closed.size();
				int nodeOpen = sol.open.size();
				output.write(" " + ((nodeClosed > Param.maxClosed || nodeOpen > Param.maxOpen) ? "LE" : nodeClosed));
			}
			output.write(System.lineSeparator());
			System.out.println("Done!");
			System.out.println();
			
		}
		
		output.close();
	}
	
	public static void main(String args[]) throws Exception {
		
		/*Param.activeColor = false;
		Param.selfTouchable = true;
		Param.maxTestSize = 9;
		run(1);*/
		
		Param.activeColor = true;
		Param.selfTouchable = true;
		Param.maxTestSize = 11;
		run(2);
		
		/*Param.activeColor = true;
		Param.selfTouchable = false;
		Param.maxTestSize = 15;
		run(3);*/
		
		/*Param.forcedMove = false;
		Param.maxNode = 5000000;
		
		Param.activeColor = false;
		Param.selfTouchable = true;
		Param.maxTestSize = 9;
		run(1001);
		
		Param.activeColor = true;
		Param.selfTouchable = true;
		Param.maxTestSize = 11;
		run(1002);
		
		Param.activeColor = true;
		Param.selfTouchable = false;
		Param.maxTestSize = 15;
		run(1003);
		
		Param.activeColor = false;
		Param.selfTouchable = false;
		Param.maxTestSize = 11;
		run(1004);*/
	}
}
