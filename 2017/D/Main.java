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
		// in = new Scanner(new java.io.File("in0.txt"));
		int t = in.nextInt();
		while (t-- > 0)
			solve();
	}
	
	static void solve() {
		int n = in.nextInt()*2, k = in.nextInt();
		int[] s = new int[k];
		int m = 0;
		int max = (1 << n)-1;
		for (int i = 0; i < k; i++) {
			s[i] = in.nextInt()-1;
			m |= 1 << s[i];
		}
		
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
	
	static int plusOne(int[] s, int m){
		m++;
		for (int i = 0; i < s.length; i++) {
			m |= 1 << s[i];
		}
		return m;
	}
	static boolean isValid(int m, int max){
		int o = 0, z = 0;
		for (int i = 1; i <= max; i<<=1) {
			if ((m & i) == i)
				o++;
			else
				if (++z > o) return false;
		}
		return o == z;
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
