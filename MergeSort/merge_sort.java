package MergeSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class merge_sort {
    
  public static void main (String args[]) { 
    ArrayList<Integer> S = new ArrayList<>();
    ArrayList<String> idArray = new ArrayList<>();
    String line;
    String filePath = "dataset_20000000.csv";
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

    // Create LinkedList L from S[left]...S[mid] 

    LinkedList<Integer> L = new LinkedList<>(); 
    LinkedList<String> Lid = new LinkedList<>(); 

    for (int i = 0; i < mid - left + 1; i++) 
    {
        L.add (S.get(left + i)); 
        Lid.add(id.get(left + i)); 
    }
 

    // Create LinkedList R from S[mid+1]...S[right] 

    LinkedList<Integer> R = new LinkedList<>(); 
    LinkedList<String> Rid = new LinkedList<>();

    for (int j = 0; j < right - mid; j++) 
    {
        R.add (S.get(mid + 1 + j)); 
        Rid.add(id.get(mid + 1 + j)); 
    }

    int k = left; 

    while (!L.isEmpty() &&  !R.isEmpty()){
        if (L.getFirst() < R.getFirst()){
            S.set(k, L.removeFirst());
            id.set(k, Lid.removeFirst());
            k++;
        }else{
            S.set(k, R.removeFirst());
            id.set(k, Rid.removeFirst());
            k++;
        }

    }
  
    while (!L.isEmpty()){
        S.set(k, L.removeFirst());
        id.set(k, Lid.removeFirst());
        k++;
    } 
  
    while (!R.isEmpty()){
        S.set(k, R.removeFirst());
        id.set(k, Rid.removeFirst());
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
