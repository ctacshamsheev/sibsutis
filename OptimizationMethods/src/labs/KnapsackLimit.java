package labs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Задача грабителя (о рюкзаке). Имеет склад, на котором присутствует
 * ассортимент товаров ( каждого товара ОГРАНИЧЕННЫЙ запас). У каждого товара
 * своя стоимость Ci и масса mi и количество ai. Выбрать набор товаров так,
 * чтобы его суммарных вес не превышал заданную грузоподъемность М притом, что
 * суммарная стоимость этого набора товаров была бы максимальной.
 * 
 */
public class KnapsackLimit {

	private class Knapsack {
		public int weight;
		public int cost;
		public int amount;

		public Knapsack(int weight, int cost, int amount) {
			super();
			this.weight = weight;
			this.cost = cost;
			this.amount = amount;
		}

		public String toString() {
			return "[w=" + weight + "\tc=" + cost + "\ta=" + amount + "]";
		}

		public boolean isS(int s) {
			return (s - this.weight >= 0) ? true : false;
		}

		public boolean isAmount(int s) {
			return (this.amount - s >= 0) ? true : false;
		}
	}

	public static void main(String[] args) throws IOException {
		new KnapsackLimit();
	}

	public KnapsackLimit() throws IOException {

		Scanner scanner = new Scanner(new File("knaps2.in"));
		int S = scanner.nextInt();
		int n = scanner.nextInt();

		ArrayList<Knapsack> arrayList = new ArrayList<Knapsack>();
		for (int i = 0; i < n; i++) {
			arrayList.add(new Knapsack(scanner.nextInt(), scanner.nextInt(), scanner.nextInt()));
		}

		// Алгоритм __________________________________________________________________

		int[][] m = new int[S + 1][n + 1];
		for (int i = 1; i <= S; i++) {
			Knapsack tKnapsack = null;
			for (Knapsack iter : arrayList) {
				if (iter.isS(i) == true) { // проверка, не уйдем ли за предел массива

					int tcost = m[i - iter.weight][0] + iter.cost; // стоимость с учетом предыдущий + текущей
					int tamount = m[i - iter.weight][arrayList.indexOf(iter) + 1] + 1; // количество, предыдущее + 1
																						// (текущее)

					if ((tcost > m[i][0]) && (iter.isAmount(tamount))) { // если рюкзак меньше текущего полученно
						m[i][0] = tcost;
						tKnapsack = iter; // запоминаем кого добавили
					}
				}
			}
			if (tKnapsack != null) { // если доавляли
				for (int j = 1; (j <= n); j++) { // переписываем значения предыдущего веса
					m[i][j] = m[i - tKnapsack.weight][j];
				}
				m[i][arrayList.indexOf(tKnapsack) + 1]++; // и добавляем вес текущего
			} else {
				for (int j = 1; (j <= n); j++) { // иначе переносим вес прошлого шага
					m[i][j] = m[i - 1][j];
				}
			}
		}
		outPrint(arrayList, m);
	}

	public void outPrint(ArrayList<Knapsack> arrayList, int m[][]) {
		// вывод
		System.out.println(m[m.length - 1][0]);
		for (int i = 1; i < m[0].length; i++) {
			System.out.println(m[m.length - 1][i] + "\t" + arrayList.get(i - 1));
		}

		int begin = 1;
		if (m.length > 18) {
			begin = m.length - 18;
		}
		System.out.println("\n\n\n_________________________");
		for (int i = begin; i < m.length; i++) {
			System.out.print(i + "\t");
		}
		System.out.println("\n_________________________");
		for (int j = 0; j < m[0].length; j++) {

			for (int i = begin; i < m.length; i++) {
				System.out.print(m[i][j] + "\t");
			}
			System.out.println();
		}

	}

	/*
	 * 
	 * 103001 3 3 8 20000 5 14 4600 8 23 10000
	 */
}
