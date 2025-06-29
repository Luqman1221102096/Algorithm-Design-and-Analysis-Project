#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <utility>
#include <algorithm>
#include <chrono>

using namespace std;

typedef pair<int, string> Pair;
vector<Pair> elements;

void quickSort(int low, int high);
int partition(int low, int high);
void medianOfThree(int low, int high);

int main() {
    string inputFile = "dataset_5000000.csv";
    ifstream inFile(inputFile);
    string line;

    if (!inFile.is_open()) {
        cerr << "Cannot open file: " << inputFile << endl;
        return 1;
    }

    elements.reserve(5'000'000); // Reserve space for performance

    while (getline(inFile, line)) {
        stringstream ss(line);
        string numStr, text;

        if (getline(ss, numStr, ',') && getline(ss, text)) {
            try {
                int num = stoi(numStr);
                elements.emplace_back(num, text);
            } catch (...) {
                cerr << "Invalid line skipped: " << line << endl;
            }
        }
    }
    inFile.close();

    auto start = chrono::high_resolution_clock::now();
    quickSort(0, elements.size() - 1);
    auto end = chrono::high_resolution_clock::now();

    /*string outputFile = "quick_sort_" + to_string(elements.size()) + ".csv";
    ofstream outFile(outputFile);

    for (const auto& p : elements) {
        outFile << p.first << "," << p.second << "\n";
    }
    outFile.close();*/

    chrono::duration<double> elapsed = end - start;
    cout << "Sorting time: " << elapsed.count() << " seconds" << endl;

    return 0;
}

// Median-of-three pivot strategy
void medianOfThree(int low, int high) {
    int mid = low + (high - low) / 2;

    if (elements[high].first < elements[low].first)
        swap(elements[low], elements[high]);
    if (elements[mid].first < elements[low].first)
        swap(elements[mid], elements[low]);
    if (elements[high].first < elements[mid].first)
        swap(elements[mid], elements[high]);

    swap(elements[mid], elements[high]); // Move median to pivot position
}

// Partition using the chosen pivot
int partition(int low, int high) {
    medianOfThree(low, high);
    int pivot = elements[high].first;
    int i = low - 1;

    for (int j = low; j < high; ++j) {
        if (elements[j].first < pivot) {
            ++i;
            swap(elements[i], elements[j]);
        }
    }
    swap(elements[i + 1], elements[high]);
    return i + 1;
}

// Optimized QuickSort
void quickSort(int low, int high) {
    while (low < high) {
        int pi = partition(low, high);

        // Tail call optimization: recurse into smaller partition
        if (pi - low < high - pi) {
            quickSort(low, pi - 1);
            low = pi + 1;
        } else {
            quickSort(pi + 1, high);
            high = pi - 1;
        }
    }
}
