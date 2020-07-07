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

public class P2 {
	static class Task {
		public static final String INPUT_FILE = "p2.in";
		public static final String OUTPUT_FILE = "p2.out";
		public static final int NMAX = 100005;
		public static final int INF = (int) 1e9;

		int n;
		int m;
		int source;
		int dest;

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
				source = sc.nextInt();
				dest = sc.nextInt();

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

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE)));
				pw.printf("%d", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			ArrayList<Integer> d = new ArrayList<>();
			for (int i = 0; i <= n; i++) {
				d.add(0);
			}

			bellman(source, d);

			return d.get(dest);
		}

		private void bellman(int source, ArrayList<Integer> d) {
			for (int i = 1; i <= n; i++) {
				d.set(i, INF);
			}

			d.set(source, 0);
			Queue<Integer> q = new LinkedList<Integer>();
			boolean[] exist = new boolean[n + 1];
			q.add(source);
			exist[source] = true;

			while (!q.isEmpty()) {
				int u = q.poll();
				exist[u] = false;
				for (Edge e : adj[u]) {
					int v = e.node, cost = e.cost;
					if (d.get(u) + cost < d.get(v)) {
						d.set(v, d.get(u) + cost);
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
			writeOutput(getResult());
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
