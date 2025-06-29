

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.time.Duration;
import java.time.Instant;

public class newQuick {
    static List<Pair> elements = new ArrayList<>();
    //list to store each row from the CSV 

    public static void main(String[] args) {
        //input file to be read
        String inputFile = "dataset_5000000.csv";

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile))) {
            String line;
            //read the file
            while ((line = reader.readLine()) != null) {
                //split into 2,number and text
                String[] tokens = line.split(",", 2);
                if (tokens.length == 2) {
                    try {
                        int number = Integer.parseInt(tokens[0]);
                        elements.add(new Pair(number, tokens[1]));
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid line skipped: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Cannot open file: " + inputFile);
            return;
        }
        //start time
        Instant start = Instant.now();
        quickSort(0, elements.size() - 1);
        //end time
        Instant end = Instant.now();

        //write output to csv file
        /*outputFile = "quick_sort_" + elements.size() + ".csv";

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {
            for (Pair p : elements) {
                writer.write(p.num + "," + p.text);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + outputFile);
        }*/
        //calculate time taken
        Duration elapsed = Duration.between(start, end);
        System.out.println("Sorting time: " + elapsed.toMillis() / 1000.0 + " seconds");
    }

    // QuickSort with median-of-three
    public static void quickSort(int low, int high) {
        while (low < high) {
            int pi = partition(low, high);

            // Tail call optimization
            if (pi - low < high - pi) {
                quickSort(low, pi - 1);
                low = pi + 1;
            } else {
                quickSort(pi + 1, high);
                high = pi - 1;
            }
        }
    }

    public static int partition(int low, int high) {
        medianOfThree(low, high);
        int pivot = elements.get(high).num;
        int i = low - 1;

        for (int j = low; j < high; ++j) {
            if (elements.get(j).num < pivot) {
                i++;
                Collections.swap(elements, i, j);
            }
        }

        Collections.swap(elements, i + 1, high);
        return i + 1;
    }

    public static void medianOfThree(int low, int high) {
        int mid = low + (high - low) / 2;

        if (elements.get(high).num < elements.get(low).num)
            Collections.swap(elements, low, high);
        if (elements.get(mid).num < elements.get(low).num)
            Collections.swap(elements, mid, low);
        if (elements.get(high).num < elements.get(mid).num)
            Collections.swap(elements, mid, high);

        Collections.swap(elements, mid, high); // Move median to pivot position
    }

    // Pair class
    static class Pair {
        int num;
        String text;

        Pair(int num, String text) {
            this.num = num;
            this.text = text;
        }
    }
}

