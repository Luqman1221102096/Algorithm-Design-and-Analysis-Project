package MergeSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//import java.util.LinkedList;

public class merge_sort {
    
  public static void main (String args[]) { 
    ArrayList<Integer> S = new ArrayList<>();
    ArrayList<String> idArray = new ArrayList<>();
    String line;
    String filePath = "dataset_100000000.csv";
    //String filePath = "Assingment/dataset_sample_1000.csv";
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        while ((line = br.readLine()) != null) {
            // Split the line by comma
            String[] values = line.split(",");
            S.add(Integer.parseInt(values[0]));
            idArray.add(values[1]);
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }


    long startTime = System.nanoTime();
    mergeSort (S, idArray); 
    long endTime = System.nanoTime();
    long runtime = endTime - startTime;
    System.out.println ("Runtime: " + (runtime / 1_000_000) + " ms");
    writeCsv(S,idArray);

  } 

  static void mergeSort (ArrayList<Integer> S, ArrayList<String> id) { 

    mergeSort (S, id, 0, S.size()-1); 

  } 


  static void mergeSort (ArrayList<Integer> S, ArrayList<String> id, int left, int right) { 

    // WRITE YOUR CODE 
    int mid = (left+right)/2;
    if (left != right){
        mergeSort (S, id, left, mid);
        mergeSort (S, id, mid + 1, right);
        merge (S, id, left, mid, right);
    }
    
  } 

  static void merge (ArrayList<Integer> S, ArrayList<String> id, int left, int mid, int right) { 

    int n1 = mid - left + 1;
    int n2 = right - mid;

    int[] L = new int[n1];
    String[] Lid = new String[n1];
    int[] R = new int[n2];
    String[] Rid = new String[n2];

    // Copy data to temp arrays
    for (int i = 0; i < n1; i++) {
        L[i] = S.get(left + i);
        Lid[i] = id.get(left + i);
    }
    for (int j = 0; j < n2; j++) {
        R[j] = S.get(mid + 1 + j);
        Rid[j] = id.get(mid + 1 + j);
    }

    int i = 0, j = 0, k = left;

    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            S.set(k, L[i]);
            id.set(k, Lid[i]);
            i++;
        } else {
            S.set(k, R[j]);
            id.set(k, Rid[j]);
            j++;
        }
        k++;
    }

    while (i < n1) {
        S.set(k, L[i]);
        id.set(k, Lid[i]);
        i++;
        k++;
    }

    while (j < n2) {
        S.set(k, R[j]);
        id.set(k, Rid[j]);
        j++;
        k++;
    }
  } 

  static void writeCsv(ArrayList<Integer> S, ArrayList<String> id){
    // Write the values into the csv file
    String filename = "merge_sort_" + S.size() + ".csv";
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
        for (int i = 0; i < S.size(); i++) {
            writer.write(S.get(i) + "," + id.get(i));
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}
