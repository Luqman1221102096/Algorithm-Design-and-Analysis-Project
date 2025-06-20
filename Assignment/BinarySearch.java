import java.io.*;
import java.util.*;

public class BinarySearch {
    public static void binarySearch(String filename) throws IOException {
        // Read dataset into a list
        List<String[]> data = readCSV(filename);

        // Sort the dataset by the integer key
        data.sort((a, b) -> Integer.compare(Integer.parseInt(a[0]), Integer.parseInt(b[0])));

        // Extract integers from the dataset for searching
        int[] sortedData = data.stream()
                .mapToInt(row -> Integer.parseInt(row[0]))
                .toArray();

        // Prepare targets for best, average, and worst cases
        int bestCaseTarget = sortedData[sortedData.length / 2]; // Middle element (best case)
        int averageCaseTarget = sortedData[sortedData.length / 4]; // Somewhere in the first quarter (average case)
        int worstCaseTarget = -1; // A value not in the dataset (worst case)

        // Perform n searches and measure time for each case
        int n = sortedData.length;

        long bestCaseTime = measureBinarySearch(sortedData, bestCaseTarget, n);
        long averageCaseTime = measureBinarySearch(sortedData, averageCaseTarget, n);
        long worstCaseTime = measureBinarySearch(sortedData, worstCaseTarget, n);

        // Write results to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("binary_search_" + n + ".txt"))) {
            writer.write("Best Case Running Time: " + bestCaseTime + " nanoseconds\n");
            writer.write("Average Case Running Time: " + averageCaseTime + " nanoseconds\n");
            writer.write("Worst Case Running Time: " + worstCaseTime + " nanoseconds\n");
        }
    }

    private static long measureBinarySearch(int[] sortedData, int target, int repetitions) {
        long startTime = System.nanoTime();

        for (int i = 0; i < repetitions; i++) {
            binarySearchHelper(sortedData, target);
        }

        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static boolean binarySearchHelper(int[] sortedData, int target) {
        int left = 0, right = sortedData.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midVal = sortedData[mid];

            if (midVal == target) {
                return true; // Target found
            } else if (midVal < target) {
                left = mid + 1; // Move to the right subtree
            } else {
                right = mid - 1; // Move to the left subtree
            }
        }

        return false; // Target not found
    }

    private static List<String[]> readCSV(String filename) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        }
        return data;
    }

    public static void main(String[] args) throws IOException {
        // Example usage
        binarySearch("dataset_sample_1000.csv"); // Replace with your dataset filename
    }
}