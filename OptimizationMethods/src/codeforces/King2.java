package codeforces;

import java.io.*;
import java.util.*;

/**
 * 
 * Задача D. Стоимость маршрута https://codeforces.com/gym/100135
 */
public class King2 {

	public static void main(String[] args) throws IOException {
		new King2();
	}

	public King2() throws IOException {
		// INPUT_----------------------------------------------
		Scanner scanner = new Scanner(new File("king2.in"));
		int m[][] = new int[8][8];
		int n = 8;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				m[n - i - 1][j] = scanner.nextInt(); // меняю с левого верхнего в правый нижний
			}
		}
		scanner.close();

		outPrint(m);

		// Алгоритм ------------------------------------
		// считаем движение по боковым сторонам
		int a[][] = new int[8][8];
		for (int i = 1; i < n; i++) {
			a[i][0] = a[i - 1][0] + m[i][0];
			a[0][i] = a[0][i - 1] + m[0][i];
		}
		outPrint(a);
		// считаем движение от элемента [1,1], зная что не выйдем за массив,
		for (int i = 1; i < n; i++) {
			for (int j = 1; j < n; j++) {
				a[i][j] = Math.min(Math.min(a[i - 1][j], a[i - 1][j - 1]), a[i][j - 1]) + m[i][j];
			}
		}
		outPrint(a);

		// OUTPUT----------------------------------------------
		FileWriter writer = new FileWriter("king2.out");
		writer.write(Long.toString(a[n - 1][n - 1]));
		writer.close();
	}

	void outPrint(int a[][]) {
		// вывод ------------------------------------
		int n = 8;
		System.out.println("__________");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
