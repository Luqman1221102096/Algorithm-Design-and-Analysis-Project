import java.io.*;
import java.util.*;

public class BinarySearchStep {
    public static void binarySearchStep(String filename, int target) throws IOException {
        // Read dataset into a list
        List<String[]> data = readCSV(filename);
        
        // Sort the dataset by the integer key
        data.sort((a, b) -> Integer.compare(Integer.parseInt(a[0]), Integer.parseInt(b[0])));
        
        // Open file for writing steps
        BufferedWriter writer = new BufferedWriter(new FileWriter("binary_search_step_" + target + ".txt"));
        
        // Perform binary search and record steps
        int left = 0, right = data.size() - 1;
        boolean found = false;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            String[] midVal = data.get(mid);
            int midInt = Integer.parseInt(midVal[0]);
            
            // Write the current step to the file
            // Add 1 to the index to convert from zero-based to one-based indexing
            writer.write((mid + 1) + ": " + midVal[0] + "/" + midVal[1] + "\n");
            
            if (midInt == target) {
                found = true;
                break;
            } else if (midInt < target) {
                left = mid + 1; // Move to the right subtree
            } else {
                right = mid - 1; // Move to the left subtree
            }
        }
        
        // If target is not found, write -1
        if (!found) {
            writer.write("-1");
        }
        
        // Close the writer
        writer.close();
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
        binarySearchStep("dataset_sample_1000.csv", 2008864030); // Replace with desired target
    }
}