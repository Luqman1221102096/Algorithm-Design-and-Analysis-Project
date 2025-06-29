#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

// Function declarations
void quickSort(vector<int>& S, vector<string>& id, int low, int high);
int partition(vector<int>& S, vector<string>& id, int low, int high);
void writeFile(const vector<int>& S, const vector<string>& id, int pivotIndex = -1);

// Global configuration
static int startrow = 1;
static int endrow = 4;
static string output = "quick_sort_step_" + to_string(startrow) + "_" + to_string(endrow) + ".txt";

int main() {
    vector<int> S;
    vector<string> idArray;
    string filePath = "dataset_sample_1000.csv"; // Adjust path if needed
    ifstream file(filePath);
    string line;
    int currentrow = 1;

    if (!file.is_open()) {
        cerr << "Could not open file: " << filePath << endl;
        return 1;
    }

    while (getline(file, line)) {
        stringstream ss(line);
        string numStr, idStr;
        if (currentrow >= startrow && currentrow <= endrow) {
            if (getline(ss, numStr, ',') && getline(ss, idStr)) {
                S.push_back(stoi(numStr));
                idArray.push_back(idStr);
            }
        }
        currentrow++;
    }

    file.close();

    ofstream clearFile(output, ios::out);
    if (!clearFile) {
        cerr << "Error clearing file." << endl;
        return 1;
    }
    clearFile.close();

    writeFile(S, idArray); // initial unsorted state
    quickSort(S, idArray, 0, S.size() - 1);

    return 0;
}

void quickSort(vector<int>& S, vector<string>& id, int low, int high) {
    if (low < high) {
        int pi = partition(S, id, low, high);
        writeFile(S, id, pi); // log each partition step
        quickSort(S, id, low, pi - 1);
        quickSort(S, id, pi + 1, high);
    }
}

int partition(vector<int>& S, vector<string>& id, int low, int high) {
    int pivot = S[high];
    int i = low - 1;
    for (int j = low; j < high; ++j) {
        if (S[j] < pivot) {
            ++i;
            swap(S[i], S[j]);
            swap(id[i], id[j]);
        }
    }
    swap(S[i + 1], S[high]);
    swap(id[i + 1], id[high]);
    return i + 1;
}

void writeFile(const vector<int>& S, const vector<string>& id, int pivotIndex) {
    ofstream writer(output, ios::app);
    if (!writer) {
        cerr << "Error opening file: " << output << endl;
        return;
    }

    if (pivotIndex >= 0)
        writer << "pi=" << pivotIndex << " ";

    writer << "[";
    for (size_t i = 0; i < S.size(); ++i) {
        writer << S[i] << "/" << id[i];
        if (i != S.size() - 1)
            writer << ", ";
    }
    writer << "]\n";

    writer.close();
}
