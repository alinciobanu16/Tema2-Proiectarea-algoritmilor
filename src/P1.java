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

public class P1 {
	static class Task {
		public static final String INPUT_FILE = "p1.in";
		public static final String OUTPUT_FILE = "p1.out";
		public static final int NMAX = 100005; // 10^5
		public static final int Inf = 0x3f3f3f3f;

		int n, m, k;
		@SuppressWarnings("unchecked")
		ArrayList<Integer>[] adj = new ArrayList[NMAX];
		ArrayList<Integer> orase = new ArrayList<>();
		ArrayList<Integer> perm = new ArrayList<>();

		private void readInput() {
			try {
				Scanner sc = new Scanner(new BufferedReader(new FileReader(INPUT_FILE)));
				n = sc.nextInt();
				m = sc.nextInt();
				k = sc.nextInt();

				for (int i = 1; i <= k; i++) {
					int oras = sc.nextInt();
					orase.add(oras);
				}

				for (int i = 1; i < n; i++) {
					int p = sc.nextInt();
					perm.add(p);
				}

				for (int i = 1; i <= n; i++) {
					adj[i] = new ArrayList<>();
				}
				for (int i = 1; i <= m; i++) {
					int x, y;
					x = sc.nextInt();
					y = sc.nextInt();
					adj[x].add(y);
					adj[y].add(x);
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

		boolean bfs(int src, int dest, ArrayList<Integer> parent, boolean[] closed) {
			if (closed[src]) {
				return false;
			}

			parent.set(src, -1);

			boolean[] visited = new boolean[n + 1];
			visited[src] = true;

			Queue<Integer> q = new LinkedList<Integer>();
			q.add(src);

			while (!q.isEmpty()) {
				int u = q.poll();
				for (int v : adj[u]) {
					if (!visited[v] && !closed[v]) {
						q.add(v);
						parent.set(v, u);
						visited[v] = true;
					}
				}
			}

			return (visited[dest] == true);
		}

		private int getResult() {
			int nr = 0;
			boolean[] closed = new boolean[n + 1];
			ArrayList<Integer> parent = new ArrayList<Integer>();
			for (int i = 0; i <= n; i++) {
				parent.add(0);
			}

			for (int source : orase) {
				if (!closed[source]) {
					while (bfs(source, 1, parent, closed)) {
						int ok = 1;
						for (int p : parent) {
							if (perm.contains(p)) {
								/*daca un nod din drum se afla in permutare*/
								ok = 0;
								int idx = perm.indexOf(p);
								if (perm.contains(source)) {
									/*daca sursa se afla inaintea nodului in permutare*/
									int idx2 = perm.indexOf(source);
									if (idx2 < idx) {
										idx = idx2;
									}
								}
								for (int j = 0; j <= idx; j++) {
									/*inchid orasul curent impreuna cu toate
									drumurile de dinainte*/
									closed[perm.get(j)] = true;
								}
								/*elimin din permutare orasele inchise*/
								perm.subList(0, idx + 1).clear();
								break;
							}
						}
						if (ok == 1) {
							break;
						}
					}
				}
			}

			for (int i = 1; i <= n; i++) {
				if (closed[i]) {
					nr++;
				}
			}

			return nr;
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