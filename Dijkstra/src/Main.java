import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] sizes = {1_000, 10_000, 50_000,100_000, 1_000_000, 1_500_000};
        String fileName = "execution_times.csv";
        int runs = 10;

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("GraphSize,AverageExecutionTime\n");

            for (int n : sizes) {
                long totalExecutionTime = 0;

                for (int i = 0; i < runs; i++) {
                    List<List<DijkstraAlgorithm.Edge>> graph = DijkstraAlgorithm.generateRandomGraph(n, n*2);

                    long startTime = System.nanoTime();
                    DijkstraAlgorithm.dijkstra(graph, 0);
                    long endTime = System.nanoTime();

                    long executionTime = (endTime - startTime) / 1_000;
                    totalExecutionTime += executionTime;
                }
                long averageExecutionTime = totalExecutionTime / runs;
                writer.write(n + "," + averageExecutionTime + " µs\n");
                System.out.println("Graph size: " + n + " | Average execution time: " + averageExecutionTime + " µs");
                System.out.println("remaining heap size (bytes): " + (Runtime.getRuntime().maxMemory()-Runtime.getRuntime().freeMemory()));

            }

            System.out.println("Execution times saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
