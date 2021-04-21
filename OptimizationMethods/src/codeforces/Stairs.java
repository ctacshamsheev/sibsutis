package codeforces;

import java.io.*;
import java.util.*;

/**
 * Задача А. Лестница https://codeforces.com/gym/100135
 */
public class Stairs {

	public static void main(String[] args) throws IOException {
		new Stairs();
	}

	Stairs() throws IOException {
		// INPUT_----------------------------------------------
		Scanner scanner = new Scanner(new File("ladder.in"));
		int n = scanner.nextInt();
		int[] l = new int[n + 1];
		int[] k = new int[n + 1];
		for (int i = 0; i < n; i++) {
			k[i + 1] = scanner.nextInt();
		}
		scanner.close();

		// Алгоритм--------------------------------------------
		l[1] = k[1];
		for (int x = 2; x <= n; x++) {
			l[x] = Math.max(l[x - 1], l[x - 2]) + k[x];
		}

		// построение маршрута ---------------------------------
		for (int i = 1; i <= n; i++) {
			System.out.print((i) + "\t");
		}
		System.out.println();
		for (int i = 1; i <= n; i++) {
			System.out.print(k[i] + "\t");
		}
		System.out.println();
		for (int i = 1; i <= n; i++) {
			System.out.print(l[i] + "\t");
		}
		System.out.println();

		int i = n;
		boolean[] a = new boolean[n + 1];
		a[n] = true;
		while (i > 1) {
			int t = l[i - 1] >= l[i - 2] ? 1 : 2;
			i -= t;
			a[i] = true;
		}

		for (i = 1; i <= n; i++) {
			char c = a[i] == true ? '|' : '_';
			System.out.print(c + "\t");
		}

		// OUTPUT----------------------------------------------
		FileWriter writer = new FileWriter("ladder.out");
		writer.write(Integer.toString(l[n]));
		writer.close();

	}
//	int funk(int x) {
//	if (x<n) {			
//		return 0;//Math.max(funk(x+1), funk(x+2))+l[x];
//	}
//	return l[n];
//}
}
