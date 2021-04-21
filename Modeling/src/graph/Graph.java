package graph;

/**
 * Моделирование. Родионов. 2 семестр. Задание: Сгенерировать случайное дерево
 * из N вершин глубины DEG.
 */

public class Graph {

	static final int N = 35;
	static final int deg = 6;

	static int a[] = new int[N]; // вершины
	static int d[] = new int[N]; // глубина вершины
	static int v[] = new int[N]; // ребро за кого связана вершина

	public static int random(int a, int b) { // целочисленное случайное число от i до j
		return (int) (Math.random() * (b - a + 1) + a);
	}

	public static void sort(int i, int j) { // перемещение местами элементов массива
		int t = a[i];
		a[i] = a[j];
		a[j] = t;
		t = d[i];
		d[i] = d[j];
		d[j] = t;
		t = v[i];
		v[i] = v[j];
		v[j] = t;
	}

	static void qsort() { // сортировака для удобного вывода
		for (int i = 0; i < N; i++) {
			for (int j = i; j < N; j++) {
				if ((d[i] > d[j]) || ((d[i] == d[j]) && (v[i] > v[j]))
						|| ((d[i] == d[j]) && (v[i] == v[j]) && (a[i] > a[j]))) {
					sort(i, j);
				}
			}
		}
	}

	public static void print() { // вывод по слоям
		System.out.println(a[0] + "(-)");
		int j = 1;
		for (int i = 2; i <= deg; i++) {
			while ((j < N) && (i == d[j])) {
				System.out.print(a[j] + "(" + v[j] + ")\t");
				j++;
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

		for (int i = 0; i < N; i++) {// заполнение массива от 0 до N-1
			a[i] = i;
		}
		int k = 0; // количество вершин с максимальной глубиной
		int vt = -1; // какая вершина за какую. -1 корень

		for (int m = 1; m <= deg; m++) { // находим корень и выстраиваем за ним в цепочку случайные вершины до степени
											// deg
			int r = random(0, N - m - k);
			d[r] = m;
			v[r] = vt;
			vt = a[r];
			sort(r, N - m - k);
		}
		k++;
		sort(N - deg, N - k); // последнее число имеет максимальную степень, сдвинем вправо и передвинем
								// границу к вершинам которым нельзя закрепляться

		int lim = N - deg; // стенка распределенных вершин
		while (lim > 0) {
			int r = random(0, lim - 1); // рандомная вершина
			int l = random(lim, N - k - 1); // рандомное ребро из доступных вершин
			d[r] = d[l] + 1; // у найденного числа повышаем порядок
			v[r] = a[l]; // запоминаем ребро
			lim--; // уменьшаем границу
			sort(r, lim); // переставляем число за границу
			if (d[lim] == deg) { // если переставленное число имеет максимальный порядок, то сдвигаем за границу
									// доступных к добавлению
				sort(lim, N - k - 1);
				k++;
			}
		}

		qsort(); // упорядочим массив перед выводом
		print(); // выведем массив в виде дерева
	}
}
