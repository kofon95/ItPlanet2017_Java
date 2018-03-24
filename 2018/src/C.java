import java.io.File;
import java.util.Scanner;

public class C {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
//        scanner = new Scanner(new File("resources/c/in.txt"));
        solve();
    }

    private static void solve() {
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        int K = scanner.nextInt();

        int[][] d = new int[N + 1][M + 1];
        int[][] t = new int[N][M];

        int bm = 0;
        for (int i = 0; i < M; i++) {
            String line = scanner.next();
            for (int j = 0; j < N; j++) {
                t[j][i] = 1 << (line.charAt(j) - 'A');
                bm |= t[j][i];
            }
        }

        if (Integer.bitCount(bm) < K) {
            System.out.println(0);
            return;
        }

        int ans = 0;

        for (int y = 0; y < M; y++) {
            for (int x = 0; x < N; x++) {

                for (int h = 1; h <= M - y; h++) {
                    for (int w = 1; w <= N - x; w++) {
                        if (h == 1 && w == 1) {
                            d[1][1] = t[x][y];
                        }
                        d[w][h] = d[w - 1][h] | d[w][h - 1] | t[x + w - 1][y + h - 1];
                        int uc = Integer.bitCount(d[w][h]);
                        if (uc == K) {
                            ans++;
                        } else if (uc > K) {
                            break;
                        }
                    }
                }
            }
        }

        System.out.println(ans);
    }
}

/*
4 3 3
MPQ
MPB
MBM
QQA
*/
