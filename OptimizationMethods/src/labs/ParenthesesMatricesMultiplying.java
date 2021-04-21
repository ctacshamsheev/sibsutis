package labs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Как известно из высшей математики, умножение матриц ассоциативно, то есть
 * результат перемножения зависит только от порядка матриц и не зависит от
 * расстановки скобок: (А*В)*С = А*(В*С). Результат перемножения от расстановки
 * скобок не зависит, зато трудоемкость этого перемножения при разных
 * расстановках скобок может отличаться существенно. Расставить скобки при
 * перемножении матриц оптимальным образом. М1=[10×20], M2=[20×5], M3=[5×4],
 * M4=[4×30], M5=[30×6].
 */
public class ParenthesesMatricesMultiplying {
	private HashMap<Key, Integer> hashMap = new HashMap<Key, Integer>();
	private int[] begin;
	private int[] end;

	public static void main(String[] args) throws IOException {
		new ParenthesesMatricesMultiplying();
	}

	public ParenthesesMatricesMultiplying() throws IOException {
		Scanner scanner = new Scanner(new File("matrmulti.in"));
		int n = scanner.nextInt(); // количество матриц
		int[] r = new int[n + 1];

		begin = new int[n];  //открывающие скобки
		end = new int[n]; // закрывающие скобки

		for (int i = 0; i <= n; i++) {
			r[i] = scanner.nextInt();
		}

		// Алгоритм ________________________________________________
		int[][] f = new int[n][n];
		for (int t = 1; t < n; ++t) {
			for (int row = 0; row < n - t; ++row) {
				int col = row + t; // // ходим по диагонали слева направо смверха сниз, потом более правая
									// диагональ
				f[row][col] = Integer.MAX_VALUE;
				for (int l = row; l < col; ++l) {// цикл по расстановкам

					int tmp = f[row][l] + f[l + 1][col] + r[row] * r[l + 1] * r[col + 1];
					if (f[row][col] >= tmp) {
						hashMap.put(new Key(row, col), l);  // при добавлении по повторному ключу значение перезаписывается, не надо выносить за цикл
						f[row][col] = tmp;
						}
				}
				//System.out.println("[" + (row + 1) + "][" + (col + 1) + "]: min=" + f[row][col] + ",\t l=" + (ltmp + 1));
				// outPrint(r, f);
			}
		}
		
		// вывод
		mapRecursion(0, n - 1); // расстановка скобок
		outPrint(r, f); //таблица
		begin[0]--;
		end[n - 1]--; //убираем для красоты скобки от первого и до последнего
		outPrint(r);// скобки
	}

	void mapRecursion(int row, int col) {
		if (row != col) {
			begin[row]++;
			end[col]++;
			if (row - col != 1) {
				int l = hashMap.get(new Key(row, col));
				mapRecursion(row, l);
				mapRecursion(l + 1, col);

			}
		}
	}

	void outPrint(int r[], int a[][]) {
		for (int i = 1; i < r.length; i++) {
			System.out.print("[" + r[i - 1] + "x" + r[i] + "]\t");
		}
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a.length; j++) {
				System.out.print(a[i][j] + "\t");

			}
			System.out.println();
		}

		System.out.println("\nMin=" + a[0][a.length - 1]); // в правом верхнем углу матрицы, оптимальное перемножение
															// матриц
	}

	public void outPrint(int a[]) {
		for (int i = 0; i < a.length - 1; i++) {
			if (i != 0) {
				System.out.print("*");
			}
			for (int j = 0; j < begin[i]; j++) {
				System.out.print("(");
			}
			//System.out.print("M" + (i + 1) + "[" + a[i] + "x" + a[i + 1] + "]");
			 System.out.print("M" + (i + 1) );  // краткая запись
			for (int j = 0; j < end[i]; j++) {
				System.out.print(")");
			}
		}
		System.out.println();		
	}
}
