package codeforces;

import java.io.*;
import java.util.*;

/**
 * Задача I. Рюкзак https://codeforces.com/gym/100135
 */

public class Knapsack {

	static StreamTokenizer in;
	static PrintWriter out;

	static int nextInt() throws IOException {
		in.nextToken();
		return (int) in.nval;
	}

	public static void main(String[] args) throws IOException {
		new Knapsack();
	}

	int max = 0;
	int S;
	boolean flag = false;

	public Knapsack() throws IOException {
		in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

		// INPUT_----------------------------------------------

		S = nextInt();
		int n = nextInt();

		ArrayList<Integer> m = new ArrayList<Integer>(n);
		int ss = 0;
		for (int i = 0; i < n; i++) {
			int j = nextInt();
			ss += j;
			m.add(j);
		}
		if (ss < S) {
			System.out.print(ss);
		} else {
			Collections.sort(m);
			// Алгоритм ------------------------------------
			funk(S, m, 0);
			// OUTPUT---------------------------------------
			System.out.print(max);
		}
	}

	void funk(int i, ArrayList<Integer> lm, int j) {
		if (flag) {
			return;
		} else if (i == 0) {
			max = j;
			flag = true;
			return;
		} else {
			while ((lm.isEmpty() != true) && (i >= lm.get(0))) {
				int sum = lm.remove(0);
				funk((i - sum), new ArrayList<Integer>(lm), (j + sum));
			}
			if (j > max) {
				max = j;
			}
		}
	}
}