import java.util.*;

class DijkstraAlgorithm {

    private static Edge current;

    static class Edge {
        int target, weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    // Dijkstra's Algorithm implementation
    public static void dijkstra(List<List<Edge>> graph, int source) {
        int n = graph.size();
        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        pq.add(new Edge(source, 0));

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int currentNode = current.target;
            int currentDistance = current.weight;

            if (currentDistance > distances[currentNode]) continue;

            for (Edge neighbor : graph.get(currentNode)) {
                int newDist = currentDistance + neighbor.weight;
                if (newDist < distances[neighbor.target]) {
                    distances[neighbor.target] = newDist;
                    pq.add(new Edge(neighbor.target, newDist));
                }
            }
        }
    }


    public static List<List<Edge>> generateRandomGraph(int n, int edges) {
        Random rand = new Random();
        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < edges; i++) {
            int source = rand.nextInt(n);
            int target = rand.nextInt(n);
            int weight = rand.nextInt(10) + 1;

            graph.get(source).add(new Edge(target, weight));
        }

        return graph;
    }
}
