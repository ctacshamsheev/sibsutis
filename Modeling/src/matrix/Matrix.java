package matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

/**
 * Моделирование. Родионов. 2 семестр. Получить случайную двухстохастическую
 * матрицу цепи Маркова. Построить график перехода. Сравнить результаты для двух
 * разных матриц. Размер матрицы N=10. Число переходов n = 1 000 000 значений.
 **/

public class Matrix {

	static final int N = 3;
	static double a[][] = new double[N + 1][N + 1]; // вершины
	static double eps = 0.000000000000001;

	static NumberFormat nf = NumberFormat.getInstance();

	static int vec_per[] = new int[N + 1];
	static int step = 5;

	public static void print() { // вывод по слоям

		for (int j = 0; j < N; j++) {
			// a[N][j]=0;
			// a[j][N]=0;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// a[N][j]+=a[i][j];
				// a[i][N]+=a[i][j];
				System.out.print(nf.format(a[i][j]) + "\t");
			}
			System.out.println(":\t" + nf.format(a[i][N]));
		}
		System.out.println("-------------------------------------");
		for (int j = 0; j < N; j++) {
			System.out.print(nf.format(a[N][j]) + "\t");
		}
		System.out.println();
		System.out.println();
	}

	public static void init() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// a[i][j] = -Math.log((N-j+1)/N-Math.random()/N);
				a[i][j] = Math.random();// * (1.0 - a[i][N]);
				a[i][N] += a[i][j];
				a[N][j] += a[i][j];
			}
			// a[i][N - 1] = 1.0 - a[i][N];
			// a[i][N] += a[i][N - 1];
			// a[N][N - 1] += a[i][N - 1];
		}

	}

	public static void normJ() {
		for (int i = 0; i < N; i++) {
			a[i][N] = 0;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a[j][i] /= (a[N][i]);
				a[j][N] += a[j][i];

			}
			(a[N][i]) = 1.0;
		}
	}

	public static void normI() {
		for (int i = 0; i < N; i++) {
			(a[N][i]) = 0;
		}
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				a[i][j] /= (a[i][N]);
				a[N][j] += a[i][j];
			}
			a[i][N] = 1.0;
		}
	}

	public static boolean isNorm() {
		for (int i = 0; i < N; i++) {
			if ((Math.abs(a[i][N] - 1.0) > eps) || (Math.abs(a[N][i] - 1.0) > eps)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		nf.setMaximumFractionDigits(4);

		init();
		print();
		normI();
		print();
		int s = 0;
		while (isNorm() && s < 1000) {
			s++;
			normJ();
			normI();
		}
		System.out.println("s=" + s);
		print();

		// TO DO

		try (FileWriter writer = new FileWriter("output.txt", false)) // создаем файл "output.txt" и открываем на
																		// запись
		{

			int k = (int) (Math.random() * N);
			System.out.println(k + "-первый шаг\t");
			vec_per[k]++;

			for (int i = 0; i < step; i++) {
				double t = Math.random();
				double sum = 0;
				s = 0;
				while (t > sum) {
					sum += a[k][s];
					s++;
				}
				System.out.println("cл.ч.=" + nf.format(t) + "\t интервал:" + (s));
				k = s - 1;
				writer.write(Integer.toString(i));
				writer.append('\t');
				writer.write(Integer.toString(s));
				writer.append('\n');
				System.out.println(i + "\t" + (s));
				vec_per[k]++;
			}
			writer.write("______________________________________________________________");
			for (int i = 0; i < N; i++) {
				System.out.println(i + "\t" + vec_per[i]);
			}

			writer.flush();

		} catch (IOException ex) {

			// System.out.println(ex.getMessage());
		}

		System.out.println("OK");

	}

}
