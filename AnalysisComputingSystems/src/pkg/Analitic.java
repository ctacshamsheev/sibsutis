package pkg;


// Аналитическая модель

public class Analitic {
	private double lambda;
	private double mu;
	private int N;
	private int T;
	private double h;
	private int i;
	
	public Analitic(double lambda, double mu, int n, int t, double h, int k, int i) {
		super();
		this.lambda = lambda;
		this.mu = mu;
		N = n;
		T = t;
		this.h = h;
		this.i = i;
	}

	public Data start() {
		Data data = new Data(T);
		for (int t=0; t<T; t++) {
			data.x[t]= t*h;
			data.y1[t] = ((lambda*N)/(lambda+mu)) +			
					((mu*i-lambda*(N-i))/(lambda+mu))* 
					Math.exp(-1.0*(lambda+mu)*data.x[t] ); 
			double d = ((N*lambda*mu)/((lambda+mu)*(lambda+mu))) +
					((lambda*lambda*(N-i)+mu*(i*mu-lambda*N))/((lambda+mu)*(lambda+mu)))*
					Math.exp(-(lambda+mu)*  data.x[t] ) -
					((lambda*lambda*(N-i)+i*mu*mu)/((lambda+mu)*(lambda+mu)))*
					Math.exp(-2.0*(lambda+mu)*  data.x[t] );	
			data.y2[t]=data.y1[t]+Math.sqrt(d);
			//data.y2[t]=data.y1[t]+d;
			data.h=Math.max(data.h, data.y2[t]);
			//System.out.println();
		}
		return data;
	}
}
