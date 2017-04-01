package main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
		in = new Scanner(new java.io.File("in.txt"));
		int t = in.nextInt();
		while (t-- > 0)
			solveOld();
	}
	
	static void solve(){
		int n = in.nextInt(), k = in.nextInt();
		boolean[] m = new boolean[n*2];
		int a = -1;
		for (int i = 0; i < k; i++) {
			a = in.nextInt()-1;
			m[a] = true;
		}
		
		int s = catalan(n-a);
		for (int i = m.length-1; i >= 0; i--) {
			
		}
		System.out.println(join(" ", m));
	}

	static int catalan(int n){
		float s = 1;
		for (float k = 2; k <= n; k++) {
			s *= (n+k) / k;
		}
		return Math.round(s);
	}
	
	static void solveOld() {
		int n = in.nextInt()*2, k = in.nextInt();
		long[] s = new long[k];
		long m = 0;
		long max = (1L << n)-1;
		for (int i = 0; i < k; i++) {
			s[i] = in.nextInt()-1;
			m |= 1 << s[i];
		}
//		System.out.println(m + " " + max);
		
		int ans = 0;
		while (m <= max){
//			System.out.println(Integer.toBinaryString(m) + "  " + isValid(m, max));
			if (isValid(m, max)) ans++;
			m = plusOne(s, m);
		}
		out.println(ans);
		
//		m = 1<<1 | 1<<0;
//		max = 63;
//		System.out.println(Integer.toBinaryString(m) + " " + isValid(m, max));
//		System.out.println(Integer.toBinaryString(max));
	}
	
	static long plusOne(long[] s, long m){
		m++;
		for (int i = 0; i < s.length; i++) {
			m |= 1 << s[i];
		}
		return m;
	}
	static boolean isValid(long m, long max){
		int o = 0, z = 0;
		for (long i = 1; i <= max; i<<=1) {
			if ((m & i) == i)
				o++;
			else
				if (++z > o) return false;
		}
		return o == z;
	}

	
	static String join(String sep, boolean[] a) {
		List<Boolean> list = new ArrayList<>();
		for (int i = 0; i < a.length; i++) {
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
