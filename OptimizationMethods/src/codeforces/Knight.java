package codeforces;

import java.io.*;
import java.util.*;

/**
 * Задача С. Ход конем https://codeforces.com/gym/100135
 */
public class Knight {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Knight();
	}

	public Knight() throws IOException {
		// INPUT_----------------------------------------------
		Scanner scanner = new Scanner(new File("knight.in"));
		int x = scanner.nextInt();
		int y = scanner.nextInt();
		scanner.close();

		// Алгоритм ------------------------------------
		int N = Math.min(x, y);
		int M = Math.max(x, y);

		long a[][] = new long[M + 1][M + 1];

		a[1][1] = 1;
		for (int n = 1; n < M; n++) {
			if (n * 2 - 1 <= M) {
				a[n][n * 2 - 1] = 1;
				a[n * 2 - 1][n] = 1;
			}
		}

		for (int n = 4; n <= N; n++) {
			for (int m = n; (m <= M) && (m < n * 2); m++) {
				a[n][m] = a[n - 2][m - 1] + a[n - 1][m - 2];
				a[m][n] = a[n - 2][m - 1] + a[n - 1][m - 2];
			}
		}
		outPrint(N, M + 1, a);
		// OUTPUT----------------------------------------------
		FileWriter writer = new FileWriter("knight.out");
		writer.write(Long.toString(a[N][M]));
		writer.close();
	}

	void outPrint(int N, int M, long a[][]) {
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j < M; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}
//	// Алгоритм рекурсии------------------------------------------
//	int funk(int x, int y) {
//		if ((x==n)&&(y==m)) {
//			return 1;
//		} 
//		if (((x+2>n)&&(y+1>m)) || ((x+1>n)&&(y+2>m))){			
//			return 0;
//		}		
//		return funk(x+2,y+1) + funk(x+1,y+2);
//	}
}
