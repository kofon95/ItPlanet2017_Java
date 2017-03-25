package main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		in = new Scanner(new java.io.File("in0.txt"));
		int t = in.nextInt();
		while (t-- > 0)
			solveDijkstraHeap();
	}
	
	static void solveDijkstraHeap() {
		int n = in.nextInt();
		in.nextLine();
		int[][] edges = new int[n+1][];
		int[][] weights = new int[n+1][];
		Map<String, Integer> citiesMap = new HashMap<>();

		int[] marks = new int[n+1];
		boolean[] extracted = new boolean[n+1];
		for (int i = 1; i <= n; i++) {
			citiesMap.put(in.nextLine(), i);
			int p = Integer.valueOf(in.nextLine());
			edges[i] = new int[p];
			weights[i] = new int[p];
			for (int j = 0; j < p; j++){
				edges[i][j] = in.nextInt();
				weights[i][j] = in.nextInt();
			}
			if (p > 0) in.nextLine();
		}
		int r = in.nextInt();
		in.nextLine();
		while (r-- > 0){
			String[] names = in.nextLine().split(" ");
			int v=citiesMap.get(names[0]), stop=citiesMap.get(names[1]);
			
			for (int k = 1; k <= n; k++) {
				marks[k] = Integer.MAX_VALUE;
				extracted[k] = false;
			}
		
			marks[v] = 0;
			
			while (true){
				extracted[v] = true;
				for (int i = 0; i < edges[v].length; i++) {
					int u = edges[v][i];
					if (extracted[u]) continue;
					int w = weights[v][i];
					if (marks[u] > marks[v] + w){
						marks[u] = marks[v] + w;
					}
				}
				
				// find min
				v = -1;
				int min = Integer.MAX_VALUE;
				for (int i = 1; i <= n; i++) {
					if (extracted[i]) continue;
					if (min > marks[i]){
						min = marks[i];
						v = i;
					}
				}
				if (v == stop) {
//					out.println("stop: " + v + " w=" + min);
					out.println(min);
					break;
				}
				if (v == -1){
					out.println("IMPOSSIBLE");
					break;
				}
	//			out.println(v + " w=" + min);
			}
		}
	}

	static void solveDijkstra() {
		int n = in.nextInt();
		in.nextLine();
		int[][] edges = new int[n+1][];
		int[][] weights = new int[n+1][];
//		String[] cities = new String[n+1];
		Map<String, Integer> citiesMap = new HashMap<>();
		
		
//		edges[1] = new int[2];
//		weights[1] = new int[2];
//		edges[1][0] = 2;
//		weights[1][0] = 1;
//		edges[1][1] = (3);
//		weights[1][1] = (3);
//
//		edges[2] = new int[3];
//		weights[2] = new int[3];
//		edges[2][0] = 1;
//		weights[2][0] = 1;
//		edges[2][1] = 3;
//		weights[2][1] = 1;
//		edges[2][2] = 4;
//		weights[2][2] = 4;
//
//		edges[3] = new int[3];
//		weights[3] = new int[3];
//		edges[3][0] = 1;
//		weights[3][0] = 3;
//		edges[3][1] = 2;
//		weights[3][1] = 1;
//		edges[3][2] = 4;
//		weights[3][2] = 1;
//
//		edges[4] = new int[2];
//		weights[4] = new int[2];
//		edges[4][0] = 2;
//		weights[4][0] = 4;
//		edges[4][1] = 3;
//		weights[4][1] = 1;
		
		
		int[] marks = new int[n+1];
		boolean[] extracted = new boolean[n+1];
		for (int i = 1; i <= n; i++) {
//			out.println("[" + in.nextLine() + "]"); // endl
//			cities[i] = in.nextLine();
			String city = in.nextLine();
			citiesMap.put(city, i);
			int p = Integer.valueOf(in.nextLine());
//			out.println(city + " " + p);
			edges[i] = new int[p];
			weights[i] = new int[p];
//			out.println("[" + in.nextLine() + "]");
			for (int j = 0; j < p; j++){
				edges[i][j] = in.nextInt();
				weights[i][j] = in.nextInt();
			}
			if (p > 0) in.nextLine();
		}
		int r = in.nextInt();
//		out.println("r=" + r);
		in.nextLine();
		while (r-- > 0){
			String[] names = in.nextLine().split(" ");
			int v=citiesMap.get(names[0]), stop=citiesMap.get(names[1]);
//			for (int c = 1; c <= n; c++) {
//				if (cities[c] == null){
//					out.println("NULL");
//				}
//				if (cities[c].equals(names[0])){
//					v = c;
//				} else if (cities[c].equals(names[1])){
//					stop = c;
//				}
//			}
			
			// Dijkstra
			
			for (int k = 1; k <= n; k++) {
				marks[k] = Integer.MAX_VALUE;
				extracted[k] = false;
			}
		
			marks[v] = 0;
			
			while (v != -1){
				extracted[v] = true;
				for (int i = 0; i < edges[v].length; i++) {
					int u = edges[v][i];
					if (extracted[u]) continue;
					int w = weights[v][i];
					if (marks[u] > marks[v] + w){
						marks[u] = marks[v] + w;
					}
				}
				
				// find min
				v = -1;
				int min = Integer.MAX_VALUE;
				for (int i = 1; i < extracted.length; i++) {
					if (extracted[i]) continue;
					if (min > marks[i]){
						min = marks[i];
						v = i;
					}
				}
				if (v == stop) {
//					out.println("stop: " + v + " w=" + min);
					out.println(min);
					break;
				}
				if (v == -1){
					out.println("IMPOSSIBLE");
				}
	//			out.println(v + " w=" + min);
			}
		}
	}
	

	
	static String join(String sep, int[] a, int fromIndex){
		return join(sep, a, fromIndex, a.length);
	}
	static String join(String sep, int[] a){
		return join(sep, a, 0, a.length);
	}
	static String join(String sep, int[] a, int fromIndex, int toIndex){
		List<Integer> list = new ArrayList<>();
		for (int i = fromIndex; i < a.length; i++) {
			list.add(a[i]);
		}
		return join(sep, list);
	}
	static String join(String sep, Collection<?> c){
		StringBuffer sb = new StringBuffer();
		for (Object i : c) {
			sb.append(i).append(sep);
		}
		return sb.substring(0, sb.length() == 0 ? 0 : sb.length() - sep.length());
	}
}
