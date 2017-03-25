package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {
	
	static final int COUNT = 2000000;
	static Random r = new Random();

	public static void main(String[] args) {
		test2();
	}

	private static void test2() {
		int[] dn = {6, 10, 14, 15, 21, 22, 26, 33, 34, 35, 38, 39, 46, 51, 55, 57, 58, 62, 65, 69, 74, 77, 82, 85, 86, 87, 91, 93, 94, 95, 106, 111, 115, 118, 119, 122, 123, 129, 133, 134, 141, 142, 143, 145, 146, 155, 158, 159, 161, 166, 177, 178, 183, 185, 187, 194, 201, 202, 203, 205, 206, 209, 213, 214, 215, 217, 218, 219, 221, 226, 235, 237, 247};
		long before = System.currentTimeMillis();
		Set<Integer> set = new HashSet<>();
		for (int i = 0; i < dn.length; i++) {
			set.add(dn[i]);
		}
		
		for (int i = 0; i < 1000000; i++) {
			set.contains(i%248);
		}
		
		System.out.println("done: " + (System.currentTimeMillis() - before));
	}

	private static void test1() {
		Map<Integer, Integer> map = new HashMap<>();
		long t = System.currentTimeMillis();
		addition(map);
		System.out.println("addition: " + (System.currentTimeMillis() - t));
		t = System.currentTimeMillis();
		extraction(map);
		System.out.println("extraction: " + (System.currentTimeMillis() - t));
	}
	
	public static void extraction(Map<Integer, Integer> map) {
		for (int i = 0; i < COUNT; i++) {
			map.get(buffer[i]);
		}
	}

	static int[] buffer = new int[COUNT];
	public static void addition(Map<Integer, Integer> map){
		for (int i = 0; i < COUNT; i++) {
			int v = r.nextInt(Integer.MAX_VALUE);
			map.put(v, i);
			buffer[i] = v;
		}
	}
}
