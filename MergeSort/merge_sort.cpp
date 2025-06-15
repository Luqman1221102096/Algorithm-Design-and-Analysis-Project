#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <list>
#include <chrono>

using namespace std;

// Function declarations
void mergeSort(vector<int>& S, vector<string>& id, int left, int right);
void merge(vector<int>& S, vector<string>& id, int left, int mid, int right);
void writeCsv(const vector<int>& S, const vector<string>& id);

int main() {
    vector<int> S;
    vector<string> idArray;
    string filePath = "MergeSort/testDataSet.csv"; // Adjust path if needed
    //string filePath = "Assingment/dataset_sample_1000.csv";
    ifstream file(filePath);
    string line;

    if (!file.is_open()) {
        cerr << "Could not open file: " << filePath << endl;
        return 1;
    }

    while (getline(file, line)) {
        stringstream ss(line);
        string numStr, idStr;

        if (getline(ss, numStr, ',') && getline(ss, idStr)) {
            S.push_back(stoi(numStr));
            idArray.push_back(idStr);
        }
    }

    file.close();

    auto start = chrono::high_resolution_clock::now();
    mergeSort(S, idArray, 0, S.size() - 1);
    auto end = chrono::high_resolution_clock::now();
    auto runtime = chrono::duration_cast<chrono::milliseconds>(end - start).count();

    cout << "Runtime: " << runtime << " ms" << endl;

    writeCsv(S, idArray);


    return 0;
}

void mergeSort(vector<int>& S, vector<string>& id, int left, int right) {
    if (left >= right) return;

    int mid = (left + right) / 2;
    mergeSort(S, id, left, mid);
    mergeSort(S, id, mid + 1, right);
    merge(S, id, left, mid, right);
}

void merge(vector<int>& S, vector<string>& id, int left, int mid, int right) {
    list<int> L, R;
    list<string> Lid, Rid;

    for (int i = left; i <= mid; ++i) {
        L.push_back(S[i]);
        Lid.push_back(id[i]);
    }
    for (int j = mid + 1; j <= right; ++j) {
        R.push_back(S[j]);
        Rid.push_back(id[j]);
    }

    int k = left;
    while (!L.empty() && !R.empty()) {
        if (L.front() < R.front()) {
            S[k] = L.front(); L.pop_front();
            id[k] = Lid.front(); Lid.pop_front();
        } else {
            S[k] = R.front(); R.pop_front();
            id[k] = Rid.front(); Rid.pop_front();
        }
        ++k;
    }

    while (!L.empty()) {
        S[k] = L.front(); L.pop_front();
        id[k] = Lid.front(); Lid.pop_front();
        ++k;
    }

    while (!R.empty()) {
        S[k] = R.front(); R.pop_front();
        id[k] = Rid.front(); Rid.pop_front();
        ++k;
    }
}

void writeCsv(const vector<int>& S, const vector<string>& id) {
    // Clear the file
    ofstream clearFile("merge_sort_" + to_string(S.size()) + ".csv", ios::out);
    if (!clearFile) {
        cerr << "Error clearing file." << endl;
        return;
    }
    clearFile.close();

    // Append data to the file
    for (size_t i = 0; i < S.size(); ++i) {
        ofstream outFile("merge_sort_" + to_string(S.size()) + ".csv", ios::app);
        if (!outFile) {
            cerr << "Error writing to file." << endl;
            return;
        }
        outFile << S[i] << "," << id[i] << "\n";
        outFile.close();
    }
}
