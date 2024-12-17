import java.util.*;

class FibonacciHeap {
    private Node minNode;
    private int size;

    class Node {
        int key;
        int value;
        Node prev;
        Node next;
        Node child;
        int degree;
        boolean mark;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
            this.prev = this;
            this.next = this;
        }
    }

    public FibonacciHeap() {
        minNode = null;
        size = 0;
    }

    public Node insert(int key) {
        Node node = new Node(key, size++);
        if (minNode == null) {
            minNode = node;
        } else {
            insertNode(minNode, node);
            if (node.key < minNode.key) {
                minNode = node;
            }
        }
        size++;
        return node;
    }

    public void decreaseKey(Node node, int key) {
        if (key > node.key) return;
        node.key = key;
        Node parent = node.prev;
        if (parent != null && key < parent.key) {
            cut(node);
        }
        if (node.key < minNode.key) {
            minNode = node;
        }
    }

    private void cut(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        insertNode(minNode, node);
        node.mark = false;
    }

    private void insertNode(Node minNode, Node node) {
        node.prev = minNode;
        node.next = minNode.next;
        minNode.next.prev = node;
        minNode.next = node;
    }

    public Node extractMin() {
        if (minNode == null) return null;

        Node extractedNode = minNode;
        if (minNode.next == minNode) {
            minNode = null;
        } else {
            minNode.next.prev = minNode.prev;
            minNode.prev.next = minNode.next;
            minNode = minNode.next;
        }
        size--;
        return extractedNode;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}

public class DijkstraWithFibonacciHeap {

    static class Edge {
        int target, weight;

        Edge(int target, int weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    public static void dijkstra(List<List<Edge>> graph, int source) {
        int n = graph.size();
        FibonacciHeap heap = new FibonacciHeap();
        FibonacciHeap.Node[] nodes = new FibonacciHeap.Node[n];

        for (int i = 0; i < n; i++) {
            nodes[i] = heap.insert(Integer.MAX_VALUE);
        }

        heap.decreaseKey(nodes[source], 0);

        while (!heap.isEmpty()) {
            FibonacciHeap.Node currentNode = heap.extractMin();
            if (currentNode == null) break;

            int current = currentNode.value;
            int currentDistance = currentNode.key;

            for (Edge neighbor : graph.get(current)) {
                int newDist = currentDistance + neighbor.weight;
                if (newDist < currentDistance) {
                    heap.decreaseKey(nodes[neighbor.target], newDist);
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