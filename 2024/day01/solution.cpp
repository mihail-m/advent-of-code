#include <iostream>
#include <algorithm>
#include <math.h>
#include <cassert>
#include <fstream>

void read_input(std::ifstream& file, std::vector<int>& l1, std::vector<int>& l2) {
    int num1, num2;
    while (file >> num1 >> num2) {
        l1.push_back(num1);
        l2.push_back(num2);
    }
}

int solve_1(std::vector<int> l1, std::vector<int> l2) {
    std::sort(l1.begin(), l1.end());
    std::sort(l2.begin(), l2.end());

    int sum = 0;
    for (size_t i = 0; i < l1.size(); i++) {
        sum += abs(l1[i] - l2[i]);
    }
    return sum;
}

int solve_2(std::vector<int> l1, std::vector<int> l2) {
    int sum = 0;
    for (size_t i = 0; i < l1.size(); i++) {
        int cnt = 0;
        for (size_t j = 0; j < l2.size(); j++) {
            if (l1[i] == l2[j]) {
                cnt++;
            }
        }
        sum += l1[i] * cnt;
    }
    return sum;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector<int> sample_l1, sample_l2;
    read_input(sample_input, sample_l1, sample_l2);

    std::ifstream input("input/input.txt");
    std::vector<int> l1, l2;
    read_input(input, l1, l2);

    assert(11 == solve_1(sample_l1, sample_l2));
    std::cout << solve_1(l1, l2) << std::endl;

    assert(31 == solve_2(sample_l1, sample_l2));
    std::cout << solve_2(l1, l2) << std::endl;

    return 0;
}
