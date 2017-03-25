package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import com.sun.xml.internal.ws.util.StringUtils;

public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;

	public static void main(String[] args) {
//		int t = Integer.valueOf(in.nextLine());
//		while (t-- > 0) {
			solve();
//		}
	}

	static void solve() {
//		int n = in.nextInt();
//		int k = in.nextInt();
		int n = 6, k = 3;
		List<Integer> a = new ArrayList<>();
		List<Integer> pops = new ArrayList<>(16);

		a.add(5+9);
		a.add(4);
		a.add(9);
		a.add(1);
		a.add(8);
		a.add(8+9);
//		for (int i = 0; i < n; i++) {
//			int ai = in.nextInt();
//			a[ai%9].add(ai);
//		}
		Collections.sort(a);
		
		int[][] table = new int[n][];
		table[0] = new int[9];
		int pop = a.remove(a.size()-1); // pop
		table[0][pop % 9] = 1;
		pops.add(pop);

		for (int i = 1; i < table.length; i++) {
			table[i] = table[i-1].clone();

			int t = a.remove(a.size()-1);
			pops.add(t);
			for (int j = 1; j < table[i-1].length; j++) {
				if (table[i-1][j] == 0) continue;
				if (table[i][(j + t) % 9] == 0){
					table[i][(j + t) % 9] += table[i-1][j]+1;
				}
				else
					table[i][(j + t) % 9] = Math.max(table[i][(j + t) % 9], Math.min(table[i-1][j]+1, k));
			}
			table[i][t%9]++;
			if (table[i][0] == k){
				out.println("stop: " + "t=" + t);
				printResult(table, pops);
				return;
			}
		}
		out.println("end");
		printResult(table, pops);
	}

	static void sortA(List<Integer>[] a){
		for (int i = 0; i < 9; i++) {
			Collections.sort(a[i]);
		}
	}
	static String join(String sep, Collection<?> c){
		StringBuffer sb = new StringBuffer();
		for (Object i : c) {
			sb.append(i).append(sep);
		}
		return sb.substring(0, sb.length() == 0 ? 0 : sb.length() - sep.length());
	}
	static void printTable(int[][] table){
		String pad = "   ";
		for (int i = 0; i < table[0].length; i++) {
			String v = i+" ";
			out.print(v + pad.substring(v.length()));
		}
		out.println();
		for (int i = 0; i < table.length; i++) {
			if (table[i] == null) break;
			for (int j = 0; j < table[i].length; j++) {
				String v = table[i][j] + " ";
				out.print(v + pad.substring(v.length()));
			}
			out.println();
		}
		out.println("-------\n");
	}
	static void printResult(int[][] table, List<Integer> pops){
		printTable(table);
		out.println(join(", ", pops));
		int t = pops.get(pops.size()-1);
		out.println(t);
		for (int i = pops.size()-2; i >= 0; i--) {
			int t2 = pops.get(i)%9;
			if (t != (9-t2)%9) continue;
			out.print(t2 + " ");
			t = (9-t2)%9;
		}
	}
}
