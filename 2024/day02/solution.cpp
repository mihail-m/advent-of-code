#include <iostream>
#include <string>
#include <sstream>
#include <fstream>
#include <cassert>

bool solve_level(std::vector<int>& nums) {
    bool flagInc = true;
    bool flagDec = true;
    for (size_t i = 1; i < nums.size(); i++) {
        if (nums[i-1] - nums[i] > 3 || nums[i-1] - nums[i] <= 0) {
            flagDec = false;
        }
        if (nums[i-1] - nums[i] < -3 || nums[i-1] - nums[i] >= 0) {
            flagInc = false;
        }
    }
    return flagInc || flagDec;
}

int solve_1(std::ifstream& file) {
    int sum = 0;
    std::string line;
    while (std::getline(file, line)) {
        std::stringstream ss(line);
        std::vector<int> nums;
        int num;
        while (ss >> num) {
            nums.push_back(num);
        }
        sum += solve_level(nums);
    }
    return sum;
}

int solve_2(std::ifstream& file) {
    int sum = 0;
    std::string line;
    while (std::getline(file, line)) {
        std::stringstream ss(line);
        std::vector<int> nums;
        int num;
        while (ss >> num) {
            nums.push_back(num);
        }

        for (size_t skip = 0; skip < nums.size(); skip++) {
            std::vector<int> new_nums;
            for (size_t i = 0; i < nums.size(); i++) {
                if (i == skip) {
                    continue;
                }
                new_nums.push_back(nums[i]);
            }

            if (solve_level(new_nums)) {
                sum++;
                break;
            }
        }
    }
    return sum;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    assert(2 == solve_1(sample_input));

    std::ifstream input("input/input.txt");
    std::cout << solve_1(input) << std::endl;

    sample_input.clear();
    sample_input.seekg(0, std::ios::beg);
    assert(2 == solve_1(sample_input));

    input.clear();
    input.seekg(0, std::ios::beg);
    std::cout << solve_2(input) << std::endl;

    return 0;
}
