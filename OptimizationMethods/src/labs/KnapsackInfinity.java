
package labs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Задача грабителя (о рюкзаке). Имеет склад, на котором присутствует
 * ассортимент товаров ( каждого товара неограниченный запас). У каждого товара
 * своя стоимость Ci и масса mi. Выбрать набор товаров так, чтобы его суммарных
 * вес не превышал заданную грузоподъемность М притом, что суммарная стоимость
 * этого набора товаров была бы максимальной.
 */
public class KnapsackInfinity {

	private class Knapsack {
		public int weight;
		public int cost;

		public Knapsack(int weight, int cost) {
			super();
			this.weight = weight;
			this.cost = cost;
		}

		public String toString() {
			return "[w=" + weight + ", c=" + cost + "]";
		}

		public boolean isS(int s) {
			return (s - this.weight >= 0) ? true : false;
		}
	}

	public static void main(String[] args) throws IOException {
		new KnapsackInfinity();
	}

	public KnapsackInfinity() throws IOException {

		Scanner scanner = new Scanner(new File("knaps.in"));
		int S = scanner.nextInt();
		int n = scanner.nextInt();

		ArrayList<Knapsack> arrayList = new ArrayList<Knapsack>();
		for (int i = 0; i < n; i++) {
			arrayList.add(new Knapsack(scanner.nextInt(), scanner.nextInt()));
		}
		System.out.println(arrayList);

		// Алгоритм
		int[] m = new int[S + 1];
		for (int i = 0; i <= S; i++) {
			for (Knapsack iter : arrayList) {
				if (iter.isS(i) == true) {
					m[i] = Math.max(m[i], m[i - iter.weight] + iter.cost);
				}
			}
		}

		// вывод
		System.out.println(m[S] + "\t");

		// поиск пути
		int i = S;
		int k[] = new int[n + 1];
		while (i > 0) {
			for (Knapsack iter : arrayList) {
				if (iter.isS(i) == true) {
					if (m[i] == m[i - iter.weight] + iter.cost) {
						// System.out.println( iter.cost + "\t");
						i -= iter.weight;
						k[arrayList.indexOf(iter)]++;
					}
				}
			}
		}
		// System.out.println(Arrays.toString(k));
		outPrint(arrayList, k);
		outPrint(m);
	}

	public void outPrint(int m[]) {
		if (m.length < 30) {
			for (int i = 1; i < m.length; i++) {
				System.out.print(i + "\t");
			}
			System.out.println("");
			for (int i = 1; i < m.length; i++) {
				System.out.print(m[i] + "\t");
			}
		}
		// System.out.println("\n_________________________" );
	}

	public void outPrint(ArrayList<Knapsack> arrayList, int k[]) {

		int i = 0;
		for (Knapsack iter : arrayList) {
			System.out.println(iter + "\t" + k[i]);
			i++;
		}
		System.out.println();

	}
}
