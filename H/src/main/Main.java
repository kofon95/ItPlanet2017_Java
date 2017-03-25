package main;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;
	static final int PRIME_COUNT = 78498; // in one million

	public static void main(String[] args) {
		long ms = System.currentTimeMillis();
		int[] dn = {6, 10, 14, 15, 21, 22, 26, 33, 34, 35, 38, 39, 46, 51, 55, 57, 58, 62, 65, 69, 74, 77, 82, 85, 86, 87, 91, 93, 94, 95, 106, 111, 115, 118, 119, 122, 123, 129, 133, 134, 141, 142, 143, 145, 146, 155, 158, 159, 161, 166, 177, 178, 183, 185, 187, 194, 201, 202, 203, 205, 206, 209, 213, 214, 215, 217, 218, 219, 221, 226, 235, 237, 247};
		Set<Integer> dnset = new HashSet<>(dn.length);
		for (int i = 0; i < dn.length; i++) {
			dnset.add(dn[i]);
		}
		
		int[] sieve = generateSieve(1000000);
		
		int each9 = 0;
		Map<Integer, Integer> divs = new HashMap<>();
		for (int i = 2; i <= 1000000; i++) {
			int a = i;
			int d = 0, di = 1;
			divs.clear();
			while (a != 1 && sieve[a] != 0){
				if (d == sieve[a]){
					divs.put(d, ++di);
				} else {
					d = sieve[a];
					divs.put(d, di=1);
				}
				a /= sieve[a];
			}
			if (a == d){
				divs.put(a, ++di);
			} else {
				divs.put(a, 1);
			}
			int m = 1;
			for (int degree : divs.values()) {
				m *= degree+1;
			}
			if (dnset.contains(m)){
				if (++each9 == 9){
					out.println(i);
					each9 = 0;
				}
			}
		}
		
//		System.out.println("done: " + (System.currentTimeMillis() - ms) + " ms");
	}
	
	// first divisor or number is prime
	static int[] generateSieve(int n){
		int[] sieve = new int[++n];
		sieve[0] = 1;
		sieve[1] = 1;
		int sq = (int)Math.ceil(Math.sqrt(n));
		for (int i = 2; i < sq; i++) {
			if (sieve[i] != 0) continue;
			for (int j = i*i; j < n; j+=i) {
				if (sieve[j] == 0)
					sieve[j] = i;
			}
		}
		return sieve;
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
}
