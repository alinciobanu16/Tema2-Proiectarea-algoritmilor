import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class P3 {
	static class Task {
		public static final String INPUT_FILE = "p3.in";
		public static final String OUTPUT_FILE = "p3.out";
		public static final int NMAX = 100005;
		public static final int INF = (int) 1e9;

		int n;
		int m;
		double energy;

		public class Edge {
			public int node;
			public int cost;

			Edge(int _node, int _cost) {
				node = _node;
				cost = _cost;
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Edge>[] adj = new ArrayList[NMAX];

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				energy = sc.nextInt();

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y, w;
					x = sc.nextInt();
					y = sc.nextInt();
					w = sc.nextInt();
					adj[x].add(new Edge(y, w));
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput() {
			ArrayList<Integer> path = new ArrayList<>();
			double result = 0;
			result = getResult(path);

			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE)));
				pw.printf("%f\n", result);
				for (int i : path) {
					pw.printf("%d ", i);
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private double getResult(ArrayList<Integer> path) {
			ArrayList<Double> d = new ArrayList<>();
			for (int i = 0; i <= n; i++) {
				d.add(0.0);
			}

			int[] parent = new int[n + 1];

			bellman(1, d, parent);

			int v = n;
			while (v != -1) {
				/*refac drumul*/
				path.add(0, v);
				v = parent[v];
			}

			return d.get(n);
		}

		private void bellman(int source, ArrayList<Double> d, int[] parent) {
			for (int i = 1; i <= n; i++) {
				d.set(i, 0.0);
			}

			parent[source] = -1;

			d.set(source, energy);
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] exist = new boolean[n + 1];
			q.add(source);
			exist[source] = true;

			while (!q.isEmpty()) {
				int u = q.poll();
				exist[u] = false;
				for (Edge e : adj[u]) {
					int v = e.node, cost = e.cost;
					/*relaxez muchiile a.i sa-mi maximizeze energia*/
					if (d.get(u) * (1 - (double) cost / 100) > d.get(v)) {
						d.set(v, d.get(u) * (1 - (double) cost / 100));
						parent[v] = u;
						if (!exist[v]) {
							q.add(v);
							exist[v] = true;
						}
					}
				}
			}
		}

		public void solve() {
			readInput();
			writeOutput();
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
