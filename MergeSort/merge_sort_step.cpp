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
void writeFile(const vector<int>& S, const vector<string>& id);

static int startrow = 1;
static int endrow = 4;
static string output = "merge_sort_step_"+ to_string(startrow) + "_" + to_string(endrow) + ".txt";

int main() {
    vector<int> S;
    vector<string> idArray;
    string filePath = "MergeSort/testDataSet.csv"; // Adjust path if needed
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
        if (currentrow >= startrow && currentrow <= endrow){
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
        return 0;
    }
    clearFile.close();

    writeFile(S, idArray);
    mergeSort(S, idArray, 0, S.size() - 1);


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
    writeFile(S, id);
}

void writeFile(const vector<int>& S, const vector<string>& id) {
    // Write the opening bracket
    ofstream writer(output, ios::app);
    if (!writer) {
        cerr << "Error opening file: " << output << endl;
        return;
    }
    writer << "[";

    // Write each num/id pair
    for (size_t i = 0; i < S.size(); ++i) {
        writer << S[i] << "/" << id[i] << ", ";
    }

    // Write the closing bracket and newline
    writer << "]\n";
    writer.close();
}