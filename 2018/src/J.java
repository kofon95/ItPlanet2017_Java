import java.io.File;
import java.util.*;

public class J {
    static Scanner scanner = new Scanner(System.in);
    static int[] x;
    static int[] y;
    static int n;
    public static void main(String[] args) throws Exception {
//        scanner = new Scanner(new File("resources/j/in_sq.txt"));
        n = scanner.nextInt();
        x = new int[n];
        y = new int[n];
        int leftBottom = 0;
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextInt();
            y[i] = scanner.nextInt();
            if (x[leftBottom] > x[i] || (x[leftBottom] == x[i] && y[leftBottom] > y[i])) {
                leftBottom = i;
            }
        }
        swap(x, 0, leftBottom);
        swap(y, 0, leftBottom);

//        long t = System.currentTimeMillis();

        int[] poly = new int[n];
        int startIndex = 0;
        int iter = 0;
        for (; startIndex+2 < n; iter++) {
            quickSort(makeComparator(x[startIndex], y[startIndex]), startIndex+1, n-1);
            int s = 2;
            poly[0] = startIndex;
            poly[1] = startIndex + 1;
//            System.out.println("si: " + startIndex);
            for (int i = startIndex+2; i < n; ) {
                int penultimate = poly[s - 2];
                int last = poly[s - 1];

                int r = rotate(x[penultimate], y[penultimate], x[last], y[last], x[i], y[i]);
                if (r == 0) {
//                    poly.set(s - 1, i);   // set the last
                    poly[s - 1] = i;   // set the last
                    i++;
                } else if (r > 0) {
//                    poly.add(i);
                    poly[s++] = i;
                    i++;
                } else {
//                    poly.remove(s - 1);  // pop and repeat this step
                    s--;
                }
            }
//            printCoords(startIndex);
//            for (Integer p : poly) {
//                System.out.println(p);
//            }
            int i = leftBottom = n - 1;
            for (int j = n - 1; i >= startIndex; i--, j--) {
                while (s != 0 && poly[s - 1] == j) {
//                    poly.remove(poly.size() - 1);
                    s--;
                    j--;
                }
                if (j < startIndex) break;
//                System.out.println(i + " <- " + j);
                x[i] = x[j];
                y[i] = y[j];
                if (x[leftBottom] > x[i] || (x[leftBottom] == x[i] && y[leftBottom] > y[i])) {
                    leftBottom = i;
                }
            }
//            System.out.println("----------- " + i);
            startIndex = i+1;
        }
//        printCoords();
        System.out.println(iter);
//        System.out.println(System.currentTimeMillis()-t + " ml");
    }

    static void printCoords(){ printCoords(0); }
    static void printCoords(int startIndex){
        for (int i = startIndex; i < n; i++) {
            System.out.println(i + ") " + x[i] + " " + y[i]);
        }
    }

    private static PointComparable makeComparator(int x0, int y0) {
        return (x1, y1, x2, y2) -> {
            int r = rotate(x0, y0, x2, y2, x1, y1);
            if (r != 0) return r;
            return Double.compare(Math.abs(x0-x1) + Math.abs(y0-y1), Math.abs(x0-x2) + Math.abs(y0-y2));
//            return Double.compare(getLength(x0, y0, x2, y2), getLength(x0, y0, x1, y1));
//            return Double.compare(getLength(x0, y0, x1, y1), getLength(x0, y0, x2, y2));
        };
    }

    private static double getLength(int x1, int y1, int x2, int y2) {
        int dx = x1-x2;
        int dy = y1-y2;
        return Math.sqrt(dx*dx + dy*dy);
    }

    static void quickSort(PointComparable cmp, int s0, int e0) {
        if (s0 >= e0) return;

        int s = s0, e = e0;
        int p = (e0-s0)/2 + s0;
        int px = x[p];
        int py = y[p];

        while (s < e) {
            while (cmp.compare(x[s], y[s], px, py) < 0) s++;
            while (cmp.compare(x[e], y[e], px, py) > 0) e--;

            if (s <= e) {
                swap(x, s, e);
                swap(y, s, e);
                s++; e--;
            }
        }
        quickSort(cmp, s0, e);
        quickSort(cmp, s, e0);
    }

    @FunctionalInterface
    interface PointComparable {
        int compare(int x1, int y1, int x2, int y2);
    }

    static int rotate(int ax, int ay, int bx, int by, int cx, int cy) {
        return (bx-ax) * (cy-by) - (by-ay)*(cx-bx);
    }
    private static void swap(int[] array, int index1, int index2) {
        int t = array[index1];
        array[index1] = array[index2];
        array[index2] = t;
    }
}
