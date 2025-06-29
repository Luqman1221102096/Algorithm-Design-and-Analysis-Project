import java.io.*;
import java.util.*;

public class quick_sort_step {
    // list to store the data read from the CSV file
    static List<Pair> elements = new ArrayList<>();
    // writer to log sorting steps to a text file
    static BufferedWriter stepWriter;
    // set the range of input rows from the dataset to process
    static int start = 1;  // Start row 
    static int end = 7;    // End row 

    public static void main(String[] args) throws Exception {
        // input dataset file which is csv
        String inputFile = "dataset_sample_1000.csv";
        // output file in txt to store the steps
        String outputFile = "quick_sort_step_" + start + "_" + end + ".txt";
        // initialize file reader for input dataset
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        // initialize file writer for logging sort steps
        stepWriter = new BufferedWriter(new FileWriter(outputFile));

        int index = 1;  // tracks the current row number being read
        String line;

        // read the file 
        while ((line = reader.readLine()) != null) {
            // read rows in start end range
            if (index >= start && index <= end) {
                String[] parts = line.split(",");
                // convert each line into a Pair object and add it to the list
                elements.add(new Pair(Integer.parseInt(parts[0]), parts[1]));
            }
            index++;
        }

        reader.close();

        // log the initial state of the list before sorting
        logStep(elements, -1);
        // begin the quick sort process on the list
        quickSort(0, elements.size() - 1);
        // log the final state of the list after sorting completes
        logStep(elements, -2); // -2 means "final"
        stepWriter.close();
        System.out.println("Steps written to: " + outputFile);
    }

    // recursive quick sort method that sorts the list between the given indices
    static void quickSort(int low, int high) throws IOException {
        if (low < high) {
            // partition the list and get the pivot index
            int pi = partition(low, high);

            // log the current state of the list after partitioning
            logStep(elements, pi); 

            // recursively sort the sublists on both sides of the pivot
            quickSort(low, pi - 1);
            quickSort(pi + 1, high);
        }
    }

    // partition method to rearrange elements around a pivot
    static int partition(int low, int high) {
        // use the last element as the pivot value
        int pivot = elements.get(high).number;
        int i = low - 1;

        // go through the sublist and move smaller elements to the left
        for (int j = low; j < high; j++) {
            if (elements.get(j).number < pivot) {
                i++;
                // swap element at j with element at i
                Collections.swap(elements, i, j);
            }
        }

        // place the pivot in the correct position
        Collections.swap(elements, i + 1, high);

        // return the index where the pivot ended up
        return i + 1;
    }

    // logs the current list state to the output file with the given pivot index
    static void logStep(List<Pair> list, int pi) throws IOException {
        if (pi >= 0) {
            stepWriter.write("pi=" + pi + " ");
        } else if (pi == -1) {
            stepWriter.write("initial ");
        } else if (pi == -2) {
            stepWriter.write("final ");
        }

        // output the list 
        stepWriter.write("[");
        for (int i = 0; i < list.size(); i++) {
            Pair p = list.get(i);
            stepWriter.write(p.number + "/" + p.text);
            if (i < list.size() - 1) stepWriter.write(", ");
        }
        stepWriter.write("]\n");
    }

    // pair class to hold a number and its associated text
    static class Pair {
        int number;
        String text;

        // constructor to initialize the pair
        Pair(int n, String t) {
            number = n;
            text = t;
        }
    }
}
