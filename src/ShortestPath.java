import java.util.*;

class ShortestPath {

    private static final int NO_PARENT = -1;


    public static int INFINITY = 99999999;

    // Function that implements Dijkstra's single source shortest path algorithm
    // for a graph represented using adjacency matrix representation
    public static List<Integer> dijkstra(int[][] graph, int startVertex, int endVertex) {
        int nVertices = graph[0].length;

        // shortestDistances[i] will hold the shortest distance from src to i
        int[] shortestDistances = new int[nVertices];

        // added[i] will true if vertex i is included
        // / in shortest path tree or shortest distance from src to i is finalized
        boolean[] added = new boolean[nVertices];

        // Initialize all distances as INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
        // Distance of source vertex from itself is always 0
        shortestDistances[startVertex] = 0;
        // Parent array to store shortest path tree
        int[] parents = new int[nVertices];
        // The starting vertex does not have a parent
        parents[startVertex] = NO_PARENT;

        // Find shortest path for all vertices
        for (int i = 1; i < nVertices; i++) {

            // Pick the minimum distance vertex from the set of vertices not yet processed.
            // nearestVertex is always equal to startNode in first iteration.
            int nearestVertex = -1, shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
            // Mark the picked vertex as processed
            added[nearestVertex] = true;

            // this is where the updated dist value of the adjacent vertices of the picked vertex are.
            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                int edgeDistance = graph[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
            if (added[endVertex]) break;
        }
        List<Integer> path = new ArrayList<>();
        path.add(shortestDistances[endVertex]);
        tracePath(endVertex, parents, path);
        return path;
    }


    // This is where I trace shortest path from source to destination using the parents array
    private static void tracePath(int currentVertex, int[] parents, List<Integer> path) {
        if (currentVertex == NO_PARENT)
            return;
        tracePath(parents[currentVertex], parents, path);
        path.add(currentVertex);
    }
    // Used floydWarshal method here.
    public static int[][] floydWarshal(int[][] graph) {
        int[][] cost = new int [graph.length][graph.length];
        for (int r = 0; r < graph.length; r++)
            for (int c = 0; c < graph.length; c++)
                cost[r][c] = graph[r][c] != 0 ? graph[r][c] : INFINITY;
        for(int k = 0; k < graph.length; k++)
            for(int i = 0; i < graph.length; i++)
                for(int j = 0; j < graph.length; j++)
                    if(cost[i][k] + cost[k][j] < cost[i][j])
                        cost[i][j] = cost[i][k] + cost[k][j];
        return cost;
    }
}