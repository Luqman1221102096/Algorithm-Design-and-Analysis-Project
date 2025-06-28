#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <algorithm>

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

// Perform binary search and record steps in a file
void binarySearchStep(const string& filename, int target) {
    // Read dataset into a vector of pairs
    vector<pair<int, string>> data = readCSV(filename);

    // Sort the dataset by the integer key
    sort(data.begin(), data.end(), [](const pair<int, string>& a, const pair<int, string>& b) {
        return a.first < b.first;
    });

    // Open file for writing steps
    ofstream outFile("binary_search_step_" + to_string(target) + ".txt");
    if (!outFile.is_open()) {
        cerr << "Error: Unable to open output file." << endl;
        return;
    }

    // Perform binary search and record steps
    int left = 0, right = data.size() - 1;
    bool found = false;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        int midInt = data[mid].first;
        string midStr = data[mid].second;

        // Write the current step to the file
        // Add 1 to the index to convert from zero-based to one-based indexing
        outFile << (mid + 1) << ": " << midInt << "/" << midStr << "\n";

        if (midInt == target) {
            found = true;
            break;
        } else if (midInt < target) {
            left = mid + 1; // Move to the right subtree
        } else {
            right = mid - 1; // Move to the left subtree
        }
    }

    // If target is not found, write -1
    if (!found) {
        outFile << "-1";
    }

    // Close the file
    outFile.close();
}

int main() {
    try {
        // Example usage
        binarySearchStep("dataset_sample_1000.csv", 2008864030); // Replace with desired target
    } catch (const exception& e) {
        cerr << "Error: " << e.what() << endl;
    }

    return 0;
}