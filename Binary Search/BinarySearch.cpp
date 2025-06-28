#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>
#include <random>
#include <chrono>

using namespace std;

// Function to read a CSV file into a vector of pairs (integer, string)
vector<pair<int, string>> readCSV(const string& filename) {
    vector<pair<int, string>> data;
    ifstream file(filename);
    string line;

    while (getline(file, line)) {
        stringstream ss(line);
        string token1, token2;

        // Split the line by comma
        getline(ss, token1, ',');
        getline(ss, token2, ',');

        // Convert the first token to an integer
        int key = stoi(token1);
        data.emplace_back(key, token2);
    }

    return data;
}

// Binary search helper function
bool binarySearchHelper(const vector<int>& sortedData, int target) {
    int left = 0, right = sortedData.size() - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int midVal = sortedData[mid];

        if (midVal == target) {
            return true; // Target found
        } else if (midVal < target) {
            left = mid + 1; // Move to the right subtree
        } else {
            right = mid - 1; // Move to the left subtree
        }
    }

    return false; // Target not found
}

// Measure the running time of binary search for a given target
long long measureBinarySearch(const vector<int>& sortedData, int target, int repetitions) {
    auto startTime = chrono::high_resolution_clock::now();

    for (int i = 0; i < repetitions; ++i) {
        binarySearchHelper(sortedData, target);
    }

    auto endTime = chrono::high_resolution_clock::now();
    return chrono::duration_cast<chrono::nanoseconds>(endTime - startTime).count();
}

// Main binary search function
void binarySearch(const string& filename) {
    // Read dataset into a vector of pairs
    vector<pair<int, string>> data = readCSV(filename);

    // Sort the dataset by the integer key
    sort(data.begin(), data.end(), [](const pair<int, string>& a, const pair<int, string>& b) {
        return a.first < b.first;
    });

    // Extract integers from the dataset for searching
    vector<int> sortedData;
    for (const auto& entry : data) {
        sortedData.push_back(entry.first);
    }

    // Prepare targets for best, average, and worst cases
    int bestCaseTarget = sortedData[sortedData.size() / 2]; // Middle element (best case)

    // Generate 10 random targets for the average case
    vector<int> averageCaseTargets(10);
    mt19937 rng(random_device{}());
    uniform_int_distribution<> dist(0, sortedData.size() - 1);
    for (int i = 0; i < 10; ++i) {
        averageCaseTargets[i] = sortedData[dist(rng)];
    }

    int worstCaseTarget = -1; // A value not in the dataset (worst case)

    // Perform n searches and measure time for each case
    int n = sortedData.size() * 10; // Perform 10x the dataset size searches

    // Warm-up phase
    for (int i = 0; i < 1000; ++i) {
        binarySearchHelper(sortedData, sortedData[sortedData.size() / 2]);
    }

    // Measure running times
    long long bestCaseTime = measureBinarySearch(sortedData, bestCaseTarget, n);

    long long totalAverageCaseTime = 0;
    for (int target : averageCaseTargets) {
        totalAverageCaseTime += measureBinarySearch(sortedData, target, n);
    }
    long long averageCaseTime = totalAverageCaseTime / averageCaseTargets.size();

    long long worstCaseTime = measureBinarySearch(sortedData, worstCaseTarget, n);

    // Write results to file
    ofstream outFile("binary_search_" + to_string(sortedData.size()) + ".txt");
    outFile << "Best Case Running Time: " << bestCaseTime << " nanoseconds\n";
    outFile << "Average Case Running Time: " << averageCaseTime << " nanoseconds\n";
    outFile << "Worst Case Running Time: " << worstCaseTime << " nanoseconds\n";
    outFile.close();
}

int main() {
    // Example usage
    try {
        binarySearch("dataset_sample_1000.csv"); // Replace with your dataset filename
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
    }

    return 0;
}