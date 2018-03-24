import java.util.Scanner;

public class I {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int t = scanner.nextInt();
        while (t-- != 0){
            int n = scanner.nextInt();
            int odds = 0;
            while (n-- != 0){
                if (scanner.nextInt() % 2 == 1)
                    odds++;
            }
            System.out.println(odds % 2 == 0 ? "POSSIBLE" : "IMPOSSIBLE");
        }
    }
}
