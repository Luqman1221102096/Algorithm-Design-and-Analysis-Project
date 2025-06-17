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

int main() {
    string inputFile = "dataset_sample_1000.csv";
    ifstream inFile(inputFile);
    string line;

    if (!inFile.is_open()) {
        cerr << "Cannot open file: " << inputFile << endl;
        return 1;
    }

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

    string outputFile = "quick_sort_" + to_string(elements.size()) + ".csv";
    ofstream outFile(outputFile);

    for (const auto& p : elements) {
        outFile << p.first << "," << p.second << "\n";
    }
    outFile.close();

    chrono::duration<double> elapsed = end - start;
    cout << "Sorting time: " << elapsed.count() << " seconds" << endl;
    cout << "Sorted output written to: " << outputFile << endl;

    return 0;
}

void quickSort(int low, int high) {
    if (low < high) {
        int pi = partition(low, high);
        quickSort(low, pi - 1);
        quickSort(pi + 1, high);
    }
}

int partition(int low, int high) {
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
