import java.util.*;

public class ifBipartite {

    public static boolean isBipartite(int[][] graph) {
        int[] color = new int[graph.length];
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < graph.length; i++) {
            if (color[i] == 0) {
                color[i] = 1;
                queue.offer(i);

                while (!queue.isEmpty()) {
                    int u = queue.poll();

                    for (int v = 0; v < graph.length; v++) {
                        if (graph[u][v] == 1 && color[v] == 0) {
                            color[v] = -color[u];
                            queue.offer(v);
                        } else if (graph[u][v] == 1 && color[u] == color[v]) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of vertices: ");
        int numVertices = scanner.nextInt();

        int[][] graph = new int[numVertices][numVertices];

        System.out.println("Enter the adjacency matrix:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                graph[i][j] = scanner.nextInt();
            }
        }

        scanner.close();

        if (isBipartite(graph)) {
            System.out.println("The graph is Bipartite.");
        } else {
            System.out.println("The graph is not Bipartite.");
        }
    }
}

//no. of vertices: 5
// {0, 1, 0, 1, 0},
// {1, 0, 1, 0, 1},
// {0, 1, 0, 1, 0},
// {1, 0, 1, 0, 1},
// {0, 1, 0, 1, 0}