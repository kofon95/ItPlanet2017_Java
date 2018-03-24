import java.util.Scanner;

public class D {
    static Scanner scanner = new Scanner(System.in);

    // ~= http://e-maxx.ru/algo/bracket_sequences
    public static void main(String[] args) {
        int t = scanner.nextInt();
        while (t-- != 0)
            solve();
    }

    static boolean sieve[] = new boolean[100];
    static int dp[][] = new int[100][100];
    private static void solve() {
        for (int i = 0; i < sieve.length; i++) {
            sieve[i] = false;
            dp[0][i] = 0;
        }
        int n = 2*scanner.nextInt();
        int k = scanner.nextInt();
        for (int i = 0; i < k; i++) {
            sieve[scanner.nextInt()] = true;
        }

        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            dp[i][0] = sieve[i] ? 0 : dp[i - 1][1];
            for (int j = 1; j <= i; j++) {
                dp[i][j] = dp[i - 1][j - 1] + (sieve[i] ? 0 : dp[i - 1][j + 1]);
            }
        }
        System.out.println(dp[n][0]);
    }
}
