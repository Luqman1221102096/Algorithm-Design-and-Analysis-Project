package MergeSort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class merge_sort_step {
    static int startrow = 1;
    static int endrow = 7;
    static String output = "merge_sort_step_"+ startrow +"_" + endrow + ".txt";
  public static void main (String args[]) { 
    ArrayList<Integer> S = new ArrayList<>();
    ArrayList<String> idArray = new ArrayList<>();
    String line;
    String filePath = "MergeSort/testDataSet.csv"; // Path to your CSV file
    //String filePath = "Assingment/dataset_sample_1000.csv";
    int currentrow = 1;

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        while ((line = br.readLine()) != null) {
            // Split the line by comma
            if (currentrow >= startrow && currentrow <= endrow){
                String[] values = line.split(",");
                S.add(Integer.parseInt(values[0]));
                idArray.add(values[1]);
            }
            currentrow++;
            
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    //Overrides outputfile
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    writeFile(S, idArray);

    mergeSort (S, idArray); 


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
    writeFile(S, id);
  } 
  public static void writeFile(ArrayList<Integer> num, ArrayList<String> id){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output,true))) {
            writer.write("[");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < num.size(); i++){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
        
            writer.write(num.get(i) + "/" + id.get(i) + ", ");
            //writer.newLine();  // Adds a new line
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(output, true))) {
            writer.write("]\n");
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }
    

}
