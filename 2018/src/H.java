import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class H {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
//        scanner = new Scanner(new File("resources/h/in.txt"));
        solve();
    }

    private static void solve() {
        sieve(100_000);
        int t = scanner.nextInt();
        while (t-- != 0) {
            int n = scanner.nextInt();
            long result = 1;
            for (Integer prime0 : primes) {
                int prime = prime0;
                if (prime > n) break;

                int v = 0;
                while (prime <= n) {
                    v += n / prime;
                    prime *= prime0;
                }
                result *= v + 1;
                result %= 1_000_000_007;
            }
            System.out.println(result);
        }
    }

    static List<Integer> primes;
    private static void sieve(long n) {
        boolean[] c = new boolean[(int) n + 1];
        primes = new ArrayList<>(10000);
        for (long i = 2; i < n; i++) {
            if (c[(int) i]) continue;
            for (long j = i * i; j < n; j += i) {
                c[(int) j] = true;
            }
            primes.add((int) i);
        }
    }
}
