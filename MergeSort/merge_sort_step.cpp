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
static int endrow = 7;
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
    vector<int> L, R;
    vector<string> Lid, Rid;

    for (int i = left; i <= mid; ++i) {
        L.push_back(S[i]);
        Lid.push_back(id[i]);
    }
    for (int j = mid + 1; j <= right; ++j) {
        R.push_back(S[j]);
        Rid.push_back(id[j]);
    }

    int n1 = mid - left + 1;
    int n2 = right - mid;
    int i = 0, j = 0, k = left;

    while (i < n1 && j < n2) {
        if (L[i] <= R[j]) {
            S[k] = L[i];
            id[k] = Lid[i];
            i++;
        } else {
            S[k] = R[j];
            id[k] = Rid[j];
            j++;
        }
        k++;
    }

    while (i < n1) {
        S[k] = L[i];
        id[k] = Lid[i];
        i++; k++;
    }

    while (j < n2) {
        S[k] = R[j];
        id[k] = Rid[j];
        j++; k++;
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