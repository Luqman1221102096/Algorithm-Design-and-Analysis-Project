import java.io.*;
import java.util.*;

public class quick_sort_step {
    static List<Pair> elements = new ArrayList<>();
    static BufferedWriter stepWriter;
    
    // Change these to set your input range
    static int start = 1;  // e.g., 1
    static int end = 7;    // e.g., 7

    public static void main(String[] args) throws Exception {
        String inputFile = "dataset_sample_1000.csv";
        String outputFile = "quick_sort_step_" + start + "_" + end + ".txt";

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        stepWriter = new BufferedWriter(new FileWriter(outputFile));

        int index = 1;
        String line;
        while ((line = reader.readLine()) != null) {
            if (index >= start && index <= end) {
                String[] parts = line.split(",");
                elements.add(new Pair(Integer.parseInt(parts[0]), parts[1]));
            }
            index++;
        }
        reader.close();

        logStep(elements, -1); // Initial unsorted state
        quickSort(0, elements.size() - 1);
        logStep(elements, -2); // Final sorted state

        stepWriter.close();
        System.out.println("Steps written to: " + outputFile);
    }

    static void quickSort(int low, int high) throws IOException {
        if (low < high) {
            int pi = partition(low, high);
            logStep(elements, pi); // Log after each partition
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    static int partition(int low, int high) {
        int pivot = elements.get(high).number;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (elements.get(j).number < pivot) {
                i++;
                Collections.swap(elements, i, j);
            }
        }
        Collections.swap(elements, i + 1, high);
        return i + 1;
    }

    static void logStep(List<Pair> list, int pi) throws IOException {
        if (pi >= 0) {
            stepWriter.write("pi=" + pi + " ");
        } else if (pi == -2) {
            stepWriter.write("final ");
        }

        stepWriter.write("[");
        for (int i = 0; i < list.size(); i++) {
            Pair p = list.get(i);
            stepWriter.write(p.number + "/" + p.text);
            if (i < list.size() - 1) stepWriter.write(", ");
        }
        stepWriter.write("]\n");
    }

    static class Pair {
        int number;
        String text;
        Pair(int n, String t) {
            number = n;
            text = t;
        }
    }
}
