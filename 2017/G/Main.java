import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class Main {
	static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	static PrintStream out = System.out;

	public static void main(String[] args) throws IOException {
//		long ms = System.currentTimeMillis();
//		in = new BufferedReader(new InputStreamReader(new FileInputStream("in1.txt")));
//		out = new PrintStream("out_solve.txt");
//		out = new PrintStream("out_solveSimple.txt");
		int t = 10;
//		int t = 1;  // input_generate
		while (t-- > 0){
			solve();
//			solveSimple();
		}
//		System.out.println(System.currentTimeMillis()-ms);
	}
	
	static void solveSimple() throws IOException {
		int n = Integer.parseInt(in.readLine());
		char[] str = new char[n];
		in.read(str);
		in.readLine();
		int m = Integer.parseInt(in.readLine());
		
		for (int i = 0; i < m; i++) {
			int k = Integer.parseInt(in.readLine());
			if (k == 0){
				out.println(isRight(str) ? "YES" : "NO");
			} else {
				k--;
				str[k] = str[k] == '(' ? ')' : '(';
			}
		}
	}
	static boolean isRight(char[] str){
		int counter = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') ++counter;
			else {
				if (--counter < 0) return false;
			}
		}
		return counter == 0;
	}
	
	static void solve() throws IOException {
		int n = Integer.parseInt(in.readLine());
		char[] str = new char[n];
		in.read(str);
		in.readLine();
		int m = Integer.parseInt(in.readLine());
//		out.println("m = " + m);
		int counter = offset(str);
		int last0 = -1;
		
		for (int i = 0; i < m; i++) {
			int k = Integer.parseInt(in.readLine());
			if (k == 0){
				if (counter == 0 && firstErrorClose == -1){
					if (i-1 != last0)
						counter = offset(str);
					out.println(counter == 0 && firstErrorClose == -1 ? "YES" : "NO");
				} else {
					out.println("NO");
				}
				last0 = i;
			} else {
				k--;
				if (str[k] == '(') {
					str[k] = ')';
					counter-=2;
				} else { // ')'
					str[k] = '(';
					counter+=2;
					if (firstErrorClose != -1 && k <= firstErrorClose){
						firstErrorClose = -1;
					}
				}
			}
		}
	}
	
	static int firstErrorClose = -1;
	static int offset(char[] str){
		int fec = -1;
		int counter = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '('){
				counter++;
			} else if (--counter == -1 && firstErrorClose == -1){
				fec = i;
			}
		}
		firstErrorClose = fec;
//		System.out.println("offset: " + counter + " " + firstErrorClose);
		return counter;
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
