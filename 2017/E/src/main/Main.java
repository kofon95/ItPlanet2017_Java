package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		in = new Scanner(new File("in.txt"));
		int t = Integer.valueOf(in.nextLine());
		while (t-- > 0) {
			solve();
		}
	}

	static void solve() {
		int n = in.nextInt();
		int k = in.nextInt();
//		int n = 6, k = 3;
		List<Integer> nums = new ArrayList<>(n);

//		nums.add(8+9);
//		nums.add(5+9);
//		nums.add(9);
//		nums.add(8);
//		nums.add(5); // -9
//		nums.add(1);
		for (int i = 0; i < n; i++) {
			nums.add(in.nextInt());
		}
		Collections.sort(nums);
		if (k == 1){
			for (int i = n-1; i >= 0; i--) {
				int num = nums.get(i);
				if (num%9 == 0){
					out.println(num);
					return;
				}
			}
			out.println("-1");
			return;
		}
		
		HashSet<Integer>[][] table = init(n);
//		table[0] = new Set[9];
		int p = nums.size();
		int pop = nums.get(--p); // pop
		table[0][pop % 9].add(1);
//		System.out.println("[" + pop % 9 + "]");

		ArrayList<String> results = new ArrayList<>();
		int i = 1;
		for (; i < table.length; i++) {
			HashSet<Integer>[] prev = table[i-1];
			HashSet<Integer>[] curr = table[i] = copy(table[i-1]);

			int x = nums.get(--p);
//			System.out.println(x);
			for (int j = 0; j < prev.length; j++) {
				int cell = (x+j) % 9;
				for (int v : prev[j]) {
					if (v+1 == k) {
						if (cell == 0) {
//							System.out.println("WIN!!!! " + p);
//							curr[(x+j) % 9].add(v+1);
							results.add(makeResult(k, nums, table, p, i));
						}
						continue; // no sense to add
					}
//					System.out.println("[" + cell + "] = " + (v+1));
					curr[cell].add(v+1);
				}
			}
			curr[x%9].add(1);
//			System.out.println("[" + x%9 + "]");
		}
		if (results.isEmpty()) {
			out.println("-1");
			return;
		}
//		System.out.println("results:");
//		for (String str : results) {
//			System.out.println(str);
//		}
		printMax(results);
	}
	
	static void printMax(List<String> results){
		isValid(results.get(0));
		String max = results.get(0);
		for (int i = 1; i < results.size(); i++) {
			String res = results.get(i);
			isValid(res);
			if (firstIsBigger(res, max)){
				max = res;
			}
		}
		out.println(max);
	}
	
	static boolean isValid(String s){
		int a = 0;
		for (int i = 0; i < s.length(); i++) {
			a += s.charAt(i) - '0';
		}
		if (a % 9 != 0) throw new RuntimeException("Wrong");
		return true;
	}

	private static boolean firstIsBigger(String first, String second) {
		if (first.length() != second.length()) {
			return first.length() > second.length();
		}
		return first.compareTo(second) > 0;
	}

	private static String makeResult(int k, List<Integer> nums, HashSet<Integer>[][] table, int p, int i) {
		int num = nums.get(p);
		ArrayList<Integer> res = new ArrayList<>(k);
		res.add(num);
		p++; i--;
		int kk = k-1;
		int inx = 9;
		for (; i > 0; p++, i--) {
			int tinx = (inx+9-(num%9)) % 9;
//			System.out.println(inx);
			if (table[i-1][tinx].contains(kk)){
//				System.out.println("continue: " + num);
				continue;
			}
			inx = tinx;
			if (--kk < 0) break;
			num = nums.get(p);
//			System.out.println(num);
			res.add(num);
		}
		if (res.size() != k){
			res.add(nums.get(nums.size()-1));
		}
		if (res.size() != k) throw new RuntimeException();
//		out.println("------\n");
		StringBuffer sb = new StringBuffer();
		for (int j = k-1; j >= 0; j--) {
			sb.append(res.get(j));
		}
		int max = res.get(0);
		for (int j = 1; j < res.size(); j++) {
			if (max > res.get(0)) {
				throw new RuntimeException("wrong sort");
			}
			max = res.get(0);
		}
		return sb.toString();
	}
	
	static HashSet<Integer>[] copy(HashSet[] src){
		HashSet<Integer>[] res = new HashSet[src.length];
		for (int i = 0; i < src.length; i++) {
			res[i] = (HashSet<Integer>) src[i].clone();
		}
		return res;
	}
	static HashSet<Integer>[][] init(int n){
		HashSet<Integer>[][] res = new HashSet[n][9];
		for (int i = 0; i < res[0].length; i++) {
			res[0][i] = new HashSet<>();
		}
		return res;
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
