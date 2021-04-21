package codeforces;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Задача В. Зайчик https://codeforces.com/gym/100135
 */
public class Lepus {

	public static void main(String[] args) throws IOException {
		new Lepus();
	}

	Lepus() throws IOException {
		// INPUT_----------------------------------------------
		Scanner scanner = new Scanner(new File("lepus.in"));
		int n = scanner.nextInt();
		String str = scanner.next();

		int[] k = new int[n + 5];
		scanner.close();
		for (int i = 0; i < n; i++) {
			if (str.charAt(i) == '.') {
				k[i] = 0;
			}
			if (str.charAt(i) == '\"') {
				k[i] = 1;
			}
			if (str.charAt(i) == 'w') {
				k[i] = -1000000;
			}
		}
		// Алгоритм--------------------------------------------
		for (int x = 1; x < n; x++) {
			if (x >= 3) {
				if (x >= 5) {
					k[x] += Math.max(Math.max(k[x - 1], k[x - 3]), k[x - 5]);
				} else {
					k[x] += Math.max(k[x - 1], k[x - 3]);
				}
			} else {
				k[x] += k[x - 1];
			}
		}

		if (k[n - 1] < 0) {
			k[n - 1] = -1;
		}

		// OUTPUT----------------------------------------------
		FileWriter writer = new FileWriter("lepus.out");
		writer.write(Integer.toString(k[n - 1]));
		writer.close();

	}

}
