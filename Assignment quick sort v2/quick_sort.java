import java.io.*;
import java.util.*;

public class quick_sort {
    static List<Pair> elements = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        String inputFile = "dataset_5000000.csv";
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;

        // Load input
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 2) continue;
            try {
                int num = Integer.parseInt(parts[0]);
                elements.add(new Pair(num, parts[1]));
            } catch (NumberFormatException e) {
                System.out.println("Invalid line skipped: " + line);
            }
        }
        reader.close();

        // Timing only the sorting (exclude file I/O)
        long startTime = System.nanoTime();
        quickSort(0, elements.size() - 1);
        long endTime = System.nanoTime();

        // Write sorted result
        String outputFile = "quick_sort_" + elements.size() + ".csv";
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        for (Pair p : elements) {
            writer.write(p.number + "," + p.text);
            writer.newLine();
        }
        writer.close();

        // Print time
        double elapsedSeconds = (endTime - startTime) / 1e9;
        System.out.printf("Sorting time: %.3f seconds%n", elapsedSeconds);
    }

    static void quickSort(int low, int high) {
        if (low < high) {
            int pi = partition(low, high);
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

    static class Pair {
        int number;
        String text;
        Pair(int n, String t) {
            number = n;
            text = t;
        }
    }
}
