import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

public class G {
//    static Scanner scanner = new Scanner(System.in);
    static BufferedReader reader;
    static TreeMap<Integer, IntKeeper> nodes = new TreeMap<>();
    public static void main(String[] args) throws Exception {
//        scanner = new Scanner(new File("resources/g/in2.txt"));

        reader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(reader.readLine());
        while (n-- != 0) {
            String[] parts = reader.readLine().split(" ");
            int s = Integer.parseInt(parts[0]);
            int x = Integer.parseInt(parts[1]);
            if (s == 1)
                System.out.println(addToSeries(x));
            else
                removeFromSeries(x);
        }
//        int n = scanner.nextInt();
//        while (n-- != 0) {
//            int s = scanner.nextInt();
//            int x = scanner.nextInt();
//            if (s == 1)
//                System.out.println(addToSeries(x));
//            else
//                removeFromSeries(x);
//        }
//        Random r = new Random();
//        long t = System.currentTimeMillis();
//        for (int i = 1; i <= 200_000; i+=1) {
//            int idx = Math.abs(i);
//            addToSeries(idx);
//            nodes.put(-5, new IntKeeper(10));
//        }
//        for (int i = 1; i <= 100_000; i+=2) {
//            int idx = Math.abs(i);
//            removeFromSeries(idx);
//        }
//        System.out.println();
//        System.out.println(System.currentTimeMillis()-t);
//        addToSeries(42);
//        addToSeries(42);
//        addToSeries(42);
//        addToSeries(43);
//        removeFromSeries(43);
//        for (Map.Entry<Integer, IntKeeper> node : nodes.entrySet()) {
//            System.out.println(node.getKey() + " : " + node.getValue().value);
//        }
    }

    static int addToSeries(int idx){
        Map.Entry<Integer, IntKeeper> prev = nodes.floorEntry(idx);
        Map.Entry<Integer, IntKeeper> next;
        IntKeeper prevTip = prev == null ? null : prev.getValue();
        if (prev == null) {
            next = nodes.ceilingEntry(idx);
        }
        else {
            next = nodes.ceilingEntry(prevTip.value+1);
        }

        if (prev != null && prevTip.value+1 >= idx) {
            int result = ++prevTip.value;               // move "prev tail" to the right
            if (next != null && next.getKey()-1 == prevTip.value) {
                prevTip.value = next.getValue().value;  // combine prev and next
                nodes.remove(next.getKey());
            }
            return result;
        } else {
            IntKeeper head;
            if (next != null && next.getKey() - 1 == idx) {
                nodes.remove(next.getKey());    // move "next head" to the left
                head = next.getValue();
            } else {
                head = new IntKeeper(idx);     // create new one
            }
            nodes.put(idx, head);
            return idx;
        }
    }

    private static void removeFromSeries(int idx) {
        Map.Entry<Integer, IntKeeper> prev = nodes.floorEntry(idx);
        if (prev == null) throw new IndexOutOfBoundsException(""+idx);
        IntKeeper prevTip = prev.getValue();

        if (prevTip.value == prev.getKey()) {
            nodes.remove(idx);      // remove if it is single
        } else if (prev.getKey() == idx) {
            nodes.remove(idx);      // move "prev head" to the right
            nodes.put(idx+1, prevTip);
        } else if (prevTip.value == idx) {
            prevTip.value--;        // move "prev tail" to the left
        } else {    // if idx is in the middle
            // then divide into 2 serieses
            if (prevTip.value < idx) throw new IndexOutOfBoundsException(""+idx);
            nodes.put(idx+1, new IntKeeper(prevTip.value));
            prevTip.value = idx-1;
        }
    }
}

class IntKeeper {
    public int value;
    public IntKeeper(int value) { this.value = value; }
    @Override
    public String toString() { return "IntKeeper{" + value + '}'; }
}
