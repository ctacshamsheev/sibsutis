package pkg;


// Экспериментальная модель
public class Experiment {
	private double lambda;
	private double mu;
	private int N;
	private int T;
	private double h;
	private int K;
	private int i;

	public Experiment(double lambda, double mu, int n, int t, double h, int k, int i) {
		super();
		this.lambda = lambda;
		this.mu = mu;
		N = n;
		T = t;
		this.h = h;
		K = k;
		this.i = i;
	}

	//метод моделирования рождения гибели
	public Data start() {
		Data data = new Data(T);
		double listM2[] = new double[T];
		double listM[] = new double[T];
		double listD[] = new double[T];

		for (int k = 0; k < K; k++) {
			int listN[] = new int[T];
			listN[0] = N-i;

			for (int i = 1; i < T; i++) {
				double t1 = i * h;
				double t2 = t1 + h;
				listN[i] = listN[i - 1];
				if (listN[i] > 0) {
					double R = Math.exp(-lambda * listN[i] * h);
					double r = Math.random();
					if (r > R) {
						listN[i]--;
					}
				}
				if (listN[i] < N) {
					double R = Math.exp(-mu * (N - listN[i]) * h);
					double r = Math.random();
					if (r > R) {
						listN[i]++;
					}
				}
			}
			for (int i = 0; i < T; i++) {
				listM[i] += listN[i];
				listM2[i] += (listN[i] * listN[i]);
			}
		}
		data.h = 0;
		for (int i = 0; i < T; i++) {
				listM[i] /= K;
				listD[i] = listM2[i] / K - (listM[i] * listM[i]);
				data.x[i] = i * h;
				data.y1[i]=(N - listM[i]);
				data.y2[i]=(N - listM[i])+Math.sqrt( listD[i]);
				data.h = Math.max(data.h , data.y2[i]);
		}
			data.w = T*h;
			return data;
	}
}
