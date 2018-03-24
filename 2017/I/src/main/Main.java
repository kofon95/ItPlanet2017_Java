package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Main {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static PrintStream out = System.out;

	public static void main(String[] args) throws Exception {
//		in = new BufferedReader(new InputStreamReader(new FileInputStream("in0.txt")));
		int t = Integer.parseUnsignedInt(in.readLine());
		while (t-- > 0){
			solve();
		}
	}

	static final int M = 26;   // number of alphabet's symbols
	
	static void solve() throws Exception {
		int n = Integer.parseUnsignedInt(in.readLine());
		int[][] m = new int[M][M];
		@SuppressWarnings("unchecked")
		List<Integer>[] g = new List[M];
		int[] indeg = new int[M];
		int[] outdeg = new int[M];
		for (int i = 0; i < M; i++) {
			g[i] = new ArrayList<>(1<<10);
		}
		
		for (int i = 0; i < n; i++) {
			String str = in.readLine();
			int s = str.charAt(0)-'a';
			int e = str.charAt(str.length()-1)-'a';
//			System.out.println(str + " " + e + " " + s);
			m[s][e]++;
			g[s].add(e);
			outdeg[s]++;
			indeg[e]++;
		}
		
		int start=-1, end=-1;
		int nums = 0, nume = 0;
		boolean impossible = false;
		for (int i = 0; i < M; i++) {
			if (outdeg[i]-1 == indeg[i]){
				start = i;
				nums++;
			} else if (indeg[i]-1 == outdeg[i]){
				nume++;
				end = i;
			} else if (Math.abs(indeg[i] - outdeg[i]) > 1){
				nums = 10;  // impossible
			}
			
			if (start == -1 && indeg[i] > 0){  // and outs[i] > 0
				start = i;  // Eulerian circuit
			}
		}
		
		
		if (start == -1 || nums > 1 || nume > 1){
			out.println("The door cannot be opened.");
			return;
		}
		visit = new boolean[M];
		visit[start] = true;
		dfs(g, start);
		for (int i = 0; i < visit.length; i++) {
			if (indeg[i] != 0 && !visit[i]){
				out.println("The door cannot be opened.");
				return;
			}
		}
		
		System.out.println("Ordering is possible.");
//		System.out.println(getPath(g, start));
	}
	static boolean[] visit;
	
	static void dfs(List<Integer>[] g, int v){
		for (int arrow : g[v]) {
			if (visit[arrow]) continue;
			visit[arrow] = true;
			dfs(g, arrow);
		}
	}
	
	static String getPath(List<Integer>[] g, int start){
		path = new ArrayList<>(1<<10);
		euler(g, start);
		StringBuffer sb = new StringBuffer();
		for (int it : path) {
			sb.append((char)(it+'a'));
		}
		return sb.toString();
	}
	static List<Integer> path;
	static void eulerRec(List<Integer>[] g, int v){
		while (!g[v].isEmpty()){
			int u = g[v].remove(g[v].size()-1);
			eulerRec(g, u);
		}
		path.add(v);
	}
	static void euler(List<Integer>[] g, int v){
		List<Integer> st = new ArrayList<>();
		st.add(v);
		while (!st.isEmpty()){
			int t = st.get(st.size()-1);
			if (g[t].isEmpty()){
				st.remove(st.size()-1);
				path.add(t);
			} else {
				st.add(g[t].remove(g[t].size()-1));
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
