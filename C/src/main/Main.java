package main;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	static PrintStream out = System.out;

	public static void main(String[] args) {
		int t = Integer.valueOf(in.nextLine());
		while (t-- > 0) {
			solve();
		}
//		for (int i = 0; i < 1000000; i++) {
//			String a1 = nextPalindrome(i);
//			String a2 = solve(i);
//			if (!a1.equals(a2)){
//				out.println("wrong: " + i);
//				out.println(a1 + " " + a2);
//				break;
//			}
//		}
//		out.println("done");
	}

	static void /*String*/ solve(/*int p*/) {
		char[] s = in.nextLine().toCharArray();
//		char[] s = (""+p).toCharArray();
		int len = s.length;
		int firstNot9 = -1; // first from middle
		if ((len&1) == 1 && s[len/2] != '9')
			firstNot9 = len/2;
		
		int l = len / 2 - 1;
		int r = len - len / 2;
		/////////////////////////
		for (; l >= 0; l--, r++) {
			if (s[r] < s[l]) {
				firstNot9 = l;
				break;
			}

			if (s[l] < s[r]) {
				if (firstNot9 == -1){
					firstNot9 = l;
				}
				s[firstNot9]++;
				r = len-firstNot9-1;
				for (int j = firstNot9+1; j < r; j++){
					s[j] = '0';
				}
				break;
			} else if (firstNot9 == -1 && s[l] != '9'){
				firstNot9 = l;
			}
		}
//		out.println(l + " " + r + "  " + firstNot9);
		
		if (firstNot9 == -1){
			s = new char[len+1];
			s[0] = '1';
			s[len] = '1';
			Arrays.fill(s, 1, len, '0');
		} else {
			if (l == -1){
				s[firstNot9]++;
				for (r = firstNot9+1; r < len-firstNot9-1; r++){
					s[r] = '0';
				}
			}
			for (; r < len; r++) {
				s[r] = s[len-r-1];
			}
		}
		out.println(s);
//		return new String(s);
	}
	
	
	static String nextPalindrome(int p){
		while (true){
			p++;
			String ps = ""+p;
			if (isPalindrome(ps)) return ps;
		}
	}
	static boolean isPalindrome(String p){
		for (int i = 0; i < p.length()/2; i++) {
			if (p.charAt(i) != p.charAt(p.length()-i-1)) return false;
		}
		return true;
	}
}
