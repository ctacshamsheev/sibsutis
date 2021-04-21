package labs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Лягушка прыгает только с лева на право первый прыжок =1 длина каждого
 * последующего шага отличается от предыдущего на единицу, либо не изменяется:
 * -1, 0, +1 (1<= шаг <...) Нобходимо найти наикратчайший путь. входной файл,
 * номера камушков: (есть пропуски, там камушков нет) Лягушка стартует в 0;
 * Лягушка финиширует в последнем (16); “ест: 0 1 2 3 4 6 7 9 13 16 ответ: 0 1 3
 * 6 9 13 16 +1 +2 +3 +3 +4 +3
 */
public class Frog {

	public static void main(String[] args) throws FileNotFoundException {
		new Frog();
	}

	public Frog() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("frog.in"));
		int N = scanner.nextInt();

		ArrayList<Integer> arrayList = new ArrayList<Integer>();

		int[] list = new int[N + 1];
		for (int i = 0; i < N; i++) {
			list[i] = scanner.nextInt();
			arrayList.add(list[i]);
		}

		int[][] a = new int[N + 1][N + 1];
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N; j++) {
				a[i][j] = 100000;
			}
		}

		// јлгоритм
		a[0][0] = 0;
		a[1][1] = 1;
		for (int j = 2; j < N; j++) {
			for (int step = 1; step <= j; step++) {
				int index = arrayList.indexOf(list[j] - step); // чтоб вручную не искать номер столбца элемента с нужным
																// значением, воспользуемс¤ поиском по коллекции
				if (index > 0) {
					int min = Math.min(Math.min(a[step - 1][index], a[step][index]), (a[step + 1][index])); // ищем
																											// минимальное
					if (a[step][j] > min) { // если минимальное меньше текущего
						a[step][j] = min + 1; // добавим шаг
					}
				}
			}
		}

		// вывод
		outPrint(a, list);

		int step = 0;
		int min = 100000;
		for (int i = 1; i < N; i++) {
			min = Math.min(min, a[i][a.length - 2]); // ищем ответ в результирующем столбце
			if (min == a[i][a.length - 2]) {
				step = i;
			}
		}

		// поиск маршрута
		if ((min == 100000) || (list[0] != 0) || (list[1] != 1)) {
			System.out.println("невозможно пройти");
		} else {
			System.out.println("min =" + min);
			ArrayList<Integer> arrayTrip = new ArrayList<Integer>();
			int index = a.length - 2;
			arrayTrip.add(list[index]);
			for (int i = min - 1; i >= 0; i--) {
				index = arrayList.indexOf(list[index] - step);
				int j = step - 1;
				while (j <= step + 1) {
					if (a[j][index] == i) {
						step = j;
					}
					j++;
				}
				arrayTrip.add(list[index]);
			}

			Collections.sort(arrayTrip);
			System.out.println(arrayTrip.toString());
		}
	}

	void outPrint(int a[][], int k[]) {

		for (int j = 0; j < k.length - 1; j++) {
			System.out.print(k[j] + "\t");
		}

		System.out
				.println("\n_________________________________________________________________________________________");
		for (int i = 1; i < a.length - 2; i++) {
			for (int j = 0; j < a.length - 1; j++) {
				if (a[i][j] == 100000) {
					System.out.print("---\t");
				} else {
					System.out.print(a[i][j] + "\t");
				}
			}
			System.out.println();
		}

	}

}
