import java.util.*;

public class FordFulkersonMaxFlow {

    public static int fordFulkersonMaxFlow(int[][] graph, int source, int sink) {
        int numVertices = graph.length;
        int[][] residualGraph = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                residualGraph[i][j] = graph[i][j];
            }
        }

        int[] parent = new int[numVertices];
        int maxFlow = 0;

        while (bfs(residualGraph, source, sink, parent)) {
            int pathFlow = Integer.MAX_VALUE;

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                pathFlow = Math.min(pathFlow, residualGraph[u][v]);
            }

            for (int v = sink; v != source; v = parent[v]) {
                int u = parent[v];
                residualGraph[u][v] -= pathFlow;
                residualGraph[v][u] += pathFlow;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static boolean bfs(int[][] graph, int source, int sink, int[] parent) {
        int numVertices = graph.length;
        boolean[] visited = new boolean[numVertices];
        Queue<Integer> queue = new LinkedList<>();

        queue.offer(source);
        visited[source] = true;

        while (!queue.isEmpty()) {
            int u = queue.poll();

            for (int v = 0; v < numVertices; v++) {
                if (!visited[v] && graph[u][v] > 0) {
                    queue.offer(v);
                    visited[v] = true;
                    parent[v] = u;
                }
            }
        }

        return visited[sink];
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

        // scanner.close();

        if (isBipartite(graph)) {
            System.out.println("The graph is Bipartite.");
            System.out.print("Enter source and sink (e.g. 0 5 --if 6 vertices--): ");
            int source = scanner.nextInt();
            int sink = scanner.nextInt();
            int maxFlow = fordFulkersonMaxFlow(graph, source, sink);
            System.out.println("Maximum Flow: " + maxFlow);
        } else {
            System.out.println("The graph is not Bipartite. Cannot compute maximum flow.");
        }

        scanner.close();
    }

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
}


//SAMPLE INPUT/OUTPUT

// Enter the number of vertices: 5
// Enter the adjacency matrix:
// 0 1 0 1 0
// 1 0 1 0 1
// 0 1 0 1 0
// 1 0 1 0 1
// 0 1 0 1 0
// The graph is Bipartite.
// Enter source and sink (e.g., 0 5): 0 4
// Maximum Flow: 2


// Enter the number of vertices: 4
// Enter the adjacency matrix:
// 0 1 0 1
// 1 0 1 0
// 0 1 0 1
// 1 0 1 0
// The graph is Bipartite.
// Enter source and sink (e.g. 0 5 --if 6 vertices--): 0 3
// Maximum Flow: 2


// Enter the number of vertices: 4
// Enter the adjacency matrix:
// 1 1 1 1
// 3 4 1 0
// 1 1 1 1
// 0 0 0 0
// The graph is not Bipartite. Cannot compute maximum flow.
