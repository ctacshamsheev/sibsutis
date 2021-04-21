package codeforces;

import java.io.*;
import java.util.*;

/**
 * Задача Е. Спуск с горы https://codeforces.com/gym/100135
 */
public class Slalom {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		new Slalom();
	}

	Slalom() throws IOException {
		// INPUT_----------------------------------------------
		Scanner scanner = new Scanner(new File("slalom.in"));
		int n = scanner.nextInt();
		int m[][] = new int[n][n];

		for (int i = 1; i <= n; i++) {
			for (int j = 0; j < i; j++) {
				m[i - 1][j] = scanner.nextInt();
			}
//			for (int j=i; j<n; j++) {
//				m[i-1][j]=-100000;
//			}
		}
		scanner.close();
		outPrint(n, m);
		int a[][] = new int[n][n];

		// Алгоритм ------------------------------------

		a[0][0] = m[0][0]; // проходим диагональ и край
		for (int i = 1; i < n; i++) {
			a[i][0] = a[i - 1][0] + m[i][0];
			a[i][i] = a[i - 1][i - 1] + m[i][i];
		}

		outPrint(n, a);

		for (int i = 2; i < n; i++) {// cчитаем из клеток в которые можем попасть
			for (int j = 1; j < n; j++) {
				a[i][j] = Math.max(a[i - 1][j - 1], a[i - 1][j]) + m[i][j];
			}
		}
		outPrint(n, a);

		int max = a[n - 1][0]; // ищем максимальную в нижней строке
		for (int i = 1; i < n; i++) {
			max = Math.max(max, a[n - 1][i]);
		}

		outPrint(n, a);
		System.out.print(max);

		// OUTPUT----------------------------------------------
		FileWriter writer = new FileWriter("slalom.out");
		writer.write(Integer.toString(max));
		writer.close();
	}

	void outPrint(int n, int a[][]) {
		// вывод ------------------------------------
		System.out.println("__________");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j <= i; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}

	}
}
