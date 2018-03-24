package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
	static MyReader in = new MyReader(System.in);
	static PrintStream out = System.out;
	static int[] bufferExtrm = new int[100000];

	public static void main(String[] args) throws IOException {
		in = new MyReader(new File("in0.txt"));
//		out = new PrintStream("out1.txt");
//		long ms = System.currentTimeMillis();
		int t = in.nextInt();
		while (t-- > 0){
			solve();
		}
//		System.out.println("total: " + (System.currentTimeMillis() - ms));
	}
	

// https://ru.wikipedia.org/wiki/%D0%90%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC_%D0%93%D1%80%D1%8D%D1%85%D0%B5%D0%BC%D0%B0
	private static void solve() {
//		long ms = System.currentTimeMillis();
		int n = in.nextInt();
		int[][] xy = new int[2][n];
		int[] ptr = new int[n];
//		if (n == 1){
//			out.println("0.00");
//			out.println(1);
//			return;
//		}
		int ex=0, ey=0;
		xy[0][0] = in.nextInt();
		xy[1][0] = in.nextInt();
		ex = xy[0][0];
		ey = xy[1][0];
		bufferExtrm[0] = 0;
		int blen = 1;
		for (int i = 1; i < n; i++) {
			int x = in.nextInt();
			int y = in.nextInt();
			xy[0][i] = x;
			xy[1][i] = y;
			ptr[i] = i;
			if (ey > y || (ey == y && ex > x)){
				ex = x;
				ey = y;
				bufferExtrm[0] = i;
				blen = 1;
			} else if (ey == y && ex == x) {
				bufferExtrm[blen++] = i;
			}
		}
		for (int i = 0; i < blen; i++) {
			int t = ptr[bufferExtrm[i]];
			ptr[bufferExtrm[i]] = ptr[i];
			ptr[i] = t;
		}
		if (blen == n){
			// only one unique point
			out.println("0.00");
			out.println(1);
			out.println();
			return;
		}
//		System.out.println("ms: " + (System.currentTimeMillis() - ms));
		///////////////////////

		PointComparable cmp = makeComparator(ex, ey);
		quickSort(xy, ptr, blen, n-1, cmp);
		
//		System.out.println("sorted: " + join(" ", ptr));
		

//		for (int i = 0; i < n; i++) {
//			out.println(ptr[i] + ") " + xy[0][ptr[i]] + " " + xy[1][ptr[i]]);
//		}
//		out.println("---------");
		

		List<Integer> set = new ArrayList<>();
		set.add(0);
		set.add(blen);
//		System.out.println("set: " + join(" ", set));
		
		for (int i = blen+1; i < ptr.length; i++) {
			int prev = set.get(set.size()-1);
			if (xy[0][ptr[i]] == xy[0][ptr[prev]] && xy[1][ptr[i]] == xy[1][ptr[prev]]){
				if (ptr[i] < ptr[prev]){
					// quick sort isn't stable
					int t = ptr[i];
					ptr[i] = ptr[prev];
					ptr[prev] = t;
				}
//				System.out.println("set: " + join(" ", set));
				continue;  // skip the same point
			}
			
//			System.out.println("i=" + i + " | " + xy[0][ptr[i]] + "," + xy[1][ptr[i]] + " | " + xy[0][ptr[prev]] + "," + xy[1][ptr[prev]]);
			// points on one line
			if (rotate(xy[0][ptr[i]], xy[1][ptr[i]], xy[0][ptr[prev]], xy[1][ptr[prev]], xy[0][bufferExtrm[0]], xy[1][bufferExtrm[0]]) == 0)
				continue;

//			int c = cmp.compare(xy[0][ptr[i]], xy[1][ptr[i]], xy[0][ptr[prev]], xy[1][ptr[prev]]);
//			if (c <= 0) throw new RuntimeException("incorrect sorting: rotate = " + c);

//			System.out.print("[");
//			System.out.print(String.format("(%s %s) (%s %s) (%s %s)", xy[0][ptr[i]], xy[1][ptr[i]],
//					xy[0][ptr[set.get(set.size()-1)]], xy[1][ptr[set.get(set.size()-1)]],
//					xy[0][ptr[set.get(set.size()-2)]], xy[1][ptr[set.get(set.size()-2)]]));
//			System.out.println("]");
//			int r1 = rotate(xy, ptr[i], ptr[set.get(set.size()-1)], ptr[set.get(set.size()-2)]);
//			int r2 = rotate(xy[0][ptr[i]], xy[1][ptr[i]],
//					xy[0][ptr[set.get(set.size()-1)]], xy[1][ptr[set.get(set.size()-1)]],
//					xy[0][ptr[set.get(set.size()-2)]], xy[1][ptr[set.get(set.size()-2)]]);
//			if (r1 != r2) throw new RuntimeException("incorrect rotate");
			while (rotate(xy, ptr[i], ptr[set.get(set.size()-1)], ptr[set.get(set.size()-2)]) >= 0){
				set.remove(set.size()-1);
//				System.out.print("ok: [");
//				System.out.print(String.format("(%s %s) (%s %s) (%s %s)", xy[0][ptr[i]], xy[1][ptr[i]],
//						xy[0][ptr[set.get(set.size()-1)]], xy[1][ptr[set.get(set.size()-1)]],
//						xy[0][ptr[set.get(set.size()-2)]], xy[1][ptr[set.get(set.size()-2)]]));
//				System.out.println("]");
			}
//			System.out.println("add: " + xy[0][ptr[i]] + " " + xy[1][ptr[i]]);
			set.add(i);
//			System.out.println();
		}
		
//		System.out.println("---------");
		int prevx = xy[0][ptr[set.get(0)]];
		int prevy = xy[1][ptr[set.get(0)]];
		double area;
		area = getLength(xy[0][ptr[set.get(set.size()-1)]], xy[1][ptr[set.get(set.size()-1)]], prevx, prevy);
//		out.println(area);
//		out.println(prevx + " " + prevx);
//		out.println(xy[0][ptr[set.get(set.size()-1)]] + " " + xy[1][ptr[set.get(set.size()-1)]]);
		for (int i = 1; i < set.size(); i++) {
			area += getLength(xy[0][ptr[set.get(i)]], xy[1][ptr[set.get(i)]], prevx, prevy);
			prevx = xy[0][ptr[set.get(i)]];
			prevy = xy[1][ptr[set.get(i)]];
		}
		
		out.println(String.format(Locale.US, "%.2f", area));
		out.print(ptr[set.get(0)]+1);
		for (int i = 1; i < set.size(); i++) {
			int s = set.get(i);
			out.print(" " + (ptr[s]+1));
//			out.println(s + ") " + (ptr[s]+1) + "| " + xy[0][ptr[s]] + " " + xy[1][ptr[s]]);
		}
		out.println();
		out.println();

		// testing
//		for (int s : set) {
//			out.println(xy[0][ptr[s]] + " " + xy[1][ptr[s]]);
//		}
		
		
//		checkRotate(xy, ptr, set);
	}
	
	static void checkRotate(int[][] xy, int[] ptr, List<Integer> sorted){
		int x = xy[0][ptr[sorted.get(0)]];
		int y = xy[1][ptr[sorted.get(0)]];
		int a = sorted.get(0);
		int b = sorted.get(1);
		for (int i = 2; i < sorted.size(); i++) {
			int c = sorted.get(i);
			int pa = ptr[a], pb = ptr[b], pc = ptr[c];
			int r = rotate(xy, pa, pb, pc);
			if (r <= 0) {
				String s = String.format("%d wrong: (%d,%d),(%d,%d),(%d,%d)", i, xy[0][pa], xy[1][pa], xy[0][pb], xy[1][pb], xy[0][pc], xy[1][pc]);
				System.out.println("Error " + r + ": " + x + " " + y);
				System.out.println(s);
			}
			a = b;
			b = c;
		}
	}
	
	
	
	static void quickSort(int[][] xy, int[] ptr, int s0, int e0, PointComparable cmp){
		if (s0 >= e0) return;
		int s = s0, e = e0;
		int[] x = xy[0], y = xy[1];
		int pi = (e-s)/2+s;
		int px = x[ptr[pi]];
		int py = y[ptr[pi]];
//		out.println("one:  s, e = " + s + " " + e + " | pivot = " + px + " " + py);
//		for (int i = s; i <= e; i++) {
//			out.print(xy[0][ptr[i]] + " " + xy[1][ptr[i]] + ", ");
//		}
//		out.println();
		
		while (s < e) {
			while (cmp.compare(x[ptr[s]], y[ptr[s]], px, py) < 0) s++;
			while (cmp.compare(x[ptr[e]], y[ptr[e]], px, py) > 0) e--;
			
			if (s <= e){
				int t = ptr[s];
				ptr[s] = ptr[e];
				ptr[e] = t;
				s++;
				e--;
			}
		}
//		out.println("two:  s, e = " + s + " " + e + " | pivot = " + px + " " + py);
//		for (int i = s0; i <= e0; i++) {
//			out.print(xy[0][ptr[i]] + " " + xy[1][ptr[i]] + ", ");
//		}
//		out.println("\n");
//		if (true)return;

		quickSort(xy, ptr, s0, e, cmp);
		quickSort(xy, ptr, s, e0, cmp);
	}
	
	
	static PointComparable makeComparator(final int x, final int y){
		return (x1,y1,x2,y2) -> {
			int res = rotate(x2, y2, x1, y1, x, y);
			if (res != 0)
				return res;
//			return Integer.compare(Math.abs(x2-x)+Math.abs(y2-y), Math.abs(x1-x)+Math.abs(y1-y));
			return Double.compare(getLength(x, y, x2, y2), getLength(x, y, x1, y1));
		};
	}
	
	static double getLength(int x1, int y1, int x2, int y2){
		int dx = x1-x2;
		int dy = y1-y2;
		return Math.sqrt(dx*dx + dy*dy);
	}

	@FunctionalInterface
	interface PointComparable{
		int compare(int x1, int y1, int x2, int y2);
	}
	
	// -right, +left
	static int rotate(int[][] xy, int a, int b, int c){
		int ax = xy[0][b]-xy[0][a];
		int ay = xy[1][b]-xy[1][a];
		int bx = xy[0][c]-xy[0][b];
		int by = xy[1][c]-xy[1][b];
		return ax*by - bx*ay;
	}

	static int rotate(int x1, int y1, int x2, int y2, int x3, int y3){
		return (x2-x1)*(y3-y2) - (x3-x2)*(y2-y1);
	}

	static String join(String sep, boolean[] a){
		return join(sep, a, 0, a.length);
	}
	static String join(String sep, boolean[] a, int fromIndex, int toIndex){
		List<Boolean> list = new ArrayList<>();
		for (int i = fromIndex; i < a.length; i++) {
			list.add(a[i]);
		}
		return join(sep, list);
	}
	static String join(String sep, int[] a, int fromIndex){
		return join(sep, a, fromIndex, a.length);
	}
	static String join(String sep, int[] a){
		return join(sep, a, 0, a.length);
	}
	static String join(String sep, int[] a, int fromIndex, int toIndex){
		List<Integer> list = new ArrayList<>();
		for (int i = fromIndex; i < toIndex; i++) {
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
	
	static class MyReader extends BufferedReader {
		
		public static final int[] ignore = new int[]{'\r', '\n', ' '};

		public MyReader(Reader in) {
			super(in);
		}
		public MyReader(InputStream in){
			super(new InputStreamReader(in));
		}
		public MyReader(String file) throws FileNotFoundException{
			super(new InputStreamReader(new FileInputStream(file)));
		}
		public MyReader(File file) throws FileNotFoundException{
			super(new InputStreamReader(new FileInputStream(file)));
		}
		public int nextInt() {
			try {
				int s;
				do {
					s = read();
				} while (indexOf(ignore, s) >= 0);

				StringBuffer sb = new StringBuffer(10);
				while (true){
					if (s >= '0' && s <= '9') sb.append(s-'0');
					else if (s == '-') sb.append('-');
					else break;
					s = read();
				}
				return Integer.parseInt(sb.toString(), 10);
			} catch (IOException e) {
				throw new RuntimeException("cannot read");
			}
		}
		
		public static int indexOf(int[] a, int v){
			for (int i = 0; i < a.length; i++) {
				if (a[i] == v) return i;
			}
			return -1;
		}
	}
}
