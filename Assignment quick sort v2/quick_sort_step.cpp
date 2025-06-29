#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <utility>
#include <algorithm>
#include <chrono>

using namespace std;

// hold number, text record from the CSV
typedef pair<int, string> Pair;
vector<Pair> elements; // Stores all data entries from the file


void quickSort(int low, int high);
int partition(int low, int high);

int main() {
    string inputFile = "dataset_sample_1000.csv";
    ifstream inFile(inputFile);
    string line;

    // check if the input file was successfully opened
    if (!inFile.is_open()) {
        cerr << "Cannot open file: " << inputFile << endl;
        return 1; // Exit with error if file can't be opened
    }

    // Read the file 
    while (getline(inFile, line)) {
        stringstream ss(line);
        string numStr, text;

        // split into number and text
        if (getline(ss, numStr, ',') && getline(ss, text)) {
            try {
                int num = stoi(numStr); // convert string to integer
                elements.emplace_back(num, text); // Add to the elements vector
            } catch (...) {
                // catch conversion errors and line skipped
                cerr << "Invalid line skipped: " << line << endl;
            }
        }
    }
    inFile.close(); // done read input

    // start measuring time before sorting begins
    auto start = chrono::high_resolution_clock::now();
    quickSort(0, elements.size() - 1); // run quick sort on the entire vector
    auto end = chrono::high_resolution_clock::now(); // Stop timing after sort completes

    // output csv file
    string outputFile = "quick_sort_" + to_string(elements.size()) + ".csv";
    ofstream outFile(outputFile);

    // write to output file
    for (const auto& p : elements) {
        outFile << p.first << "," << p.second << "\n";
    }
    outFile.close(); // close output file

    // calculate time taken for sorting
    chrono::duration<double> elapsed = end - start;
    cout << "Sorting time: " << elapsed.count() << " seconds" << endl;
    cout << "Sorted output written to: " << outputFile << endl;

    return 0; // Successful execution
}

// quick sort function
void quickSort(int low, int high) {
    if (low < high) {
        int pi = partition(low, high); // partition and get pivot index
        quickSort(low, pi - 1);        // sort left half
        quickSort(pi + 1, high);       // sort right half
    }
}

// partition logic used in quick sort
// small to left large to right
int partition(int low, int high) {
    int pivot = elements[high].first; // use the last element as the pivot
    int i = low - 1; // index for elements smaller than pivot

    for (int j = low; j < high; ++j) {
        // if the current element is less than the pivot move it
        if (elements[j].first < pivot) {
            ++i;
            swap(elements[i], elements[j]); // swap elements to move smaller values to left
        }
    }

    // finally, move the pivot to its correct position
    swap(elements[i + 1], elements[high]);

    return i + 1; // return the final pivot position
}
