package main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Main {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static PrintStream out = System.out;

	public static void main(String[] args) throws Exception {
		in = new BufferedReader(new InputStreamReader(new FileInputStream("in.txt")));
		int t = Integer.parseUnsignedInt(in.readLine());
//		t = 1;
		while (t > 0){
			String s = in.readLine().trim();
			if (s.length() == 0) continue;
			solve(s);
			--t;
		}
	}
	
	static void solve(String num) throws IOException{
//		String v = "5324364645367337356875485674573567331.3346734563457456845678367356783568567356747454363";
		BigDecimal d = new BigDecimal(num).setScale(prn, rnd);
		
		BigDecimal cube = cbrt(d).setScale(10, rnd);
		out.println(toResult(cube.toPlainString()));
		if (cube.pow(3).compareTo(d) > 0 || cube.add(new BigDecimal("0.0000000001")).pow(3).compareTo(d) <= 0){
			throw new RuntimeException("Wrong");
		}
	}

	static final int prn = 10+1;
	static final BigDecimal two = BigDecimal.valueOf(2);
	static final int rnd = BigDecimal.ROUND_FLOOR;
	
	static BigDecimal cbrt(BigDecimal v) {
		int ii = 0;
		int step = 0;
		BigDecimal s = BigDecimal.ZERO;
		BigDecimal e = v;
		while (true){
			BigDecimal m = s.add(e).divide(two, prn, BigDecimal.ROUND_HALF_UP);
			BigDecimal cube = m.pow(3).setScale(prn, rnd);
			int cmp = cube.compareTo(v);
			if (cmp < 0){
				if (++step >= 10){
					if (m.equals(s)){
//						System.out.println(m);
	//					System.out.println(ii);
						return s;
					}
					step = 0;
				}
				s = m;
			} else if (cmp > 0){
				if (++step >= 10) {
					if (m.equals(e)){
	//					System.out.println(ii);
						return s;
					}
					step = 0;
				}
				e = m;
			}
			else{
//				System.out.println("eq: " + s);
//				System.out.println(ii);
				return m;
			}
			
//			if (++ii >= 530){
//				throw new RuntimeException("stop " + ii);
//			}
		}
	}
	
	static String toResult(String cube){
		int s = 0;
		for (int i = 0; i < cube.length(); i++) {
			char c = cube.charAt(i);
			if (c == '.') continue;
			s += c-'0';
		}
		return s%10 + " " + cube;
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
