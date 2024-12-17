import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

public class GraphPlotter {

    public static void main(String[] args) {
        String fileName = "execution_times.csv";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            String[] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                String graphSize = nextLine[0];
                String executionTime = nextLine[1];

                executionTime = executionTime.replace(" µs", "");
                dataset.addValue(Long.parseLong(executionTime), "Execution Time", graphSize);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Execution Time vs Graph Size",
                "Graph Size",
                "Average Execution Time (µs)",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(chartPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
