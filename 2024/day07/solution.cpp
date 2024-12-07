#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>

bool canSolve1(std::vector<long long>& nums, int pos, long long cur, long long res) {
    if (pos == nums.size() && cur == res) {
        return true;
    }
    if (cur > res || pos >= nums.size()) {
        return false;
    }
    return canSolve1(nums, pos + 1, cur + nums[pos], res) | canSolve1(nums, pos + 1, cur * nums[pos], res);
}

long long concat(long long n1, long long n2) {
    std::string str1 = std::to_string(n1);
    std::string str2 = std::to_string(n2);
    return std::stoll(str1 + str2);
}

bool canSolve2(std::vector<long long>& nums, int pos, long long cur, long long res) {
    if (pos == nums.size() && cur == res) {
        return true;
    }
    if (cur > res || pos >= nums.size()) {
        return false;
    }
    return canSolve2(nums, pos + 1, cur + nums[pos], res) 
        | canSolve2(nums, pos + 1, cur * nums[pos], res)
        | canSolve2(nums, pos + 1, concat(cur, nums[pos]), res);
}


long long solve(std::ifstream& file, bool (*canSolve)(std::vector<long long>&, int, long long, long long)) {
    long long sum = 0;

    std::string line;
    while (std::getline(file, line)) {
        std::stringstream ss(line);

        long long result;
        ss >> result;
        ss.ignore(1, ':');

        long long num;
        std::vector<long long> nums;
        while (ss >> num) {
            nums.push_back(num);
        }

        if (canSolve(nums, 1, nums[0], result)) {
            sum += result;
        }
    }

    return sum;
}

void resetFile(std::ifstream& f) {
    f.clear();
    f.seekg(0, std::ios::beg);
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::ifstream input("input/input.txt"); 

    assert(3749 == solve(sample_input, canSolve1));
    std::cout << solve(input, canSolve1) << std::endl;

    resetFile(sample_input);
    assert(11387 == solve(sample_input, canSolve2));
    resetFile(input);
    std::cout << solve(input, canSolve2) << std::endl;

    return 0;
}
