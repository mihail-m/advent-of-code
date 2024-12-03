#include <iostream>
#include <fstream>
#include <cassert>
#include <regex>

std::string read_input(std::ifstream& file) {
    std::string str, line;
    while (std::getline(file, line)) {
        str.append(line);  
    }
    return str;
}

int solve_1(const std::string& str) {
    std::regex pattern("mul\\(([1-9][0-9]*),([1-9][0-9]*)\\)");
    std::sregex_iterator begin(str.begin(), str.end(), pattern);
    std::sregex_iterator end;

    int sum = 0;  
    for (auto it = begin; it != end; ++it) {
        std::smatch match = *it;
        int num1 = std::stoi(match[1].str());
        int num2 = std::stoi(match[2].str());
        sum += (num1 * num2);
    }
    return sum;
}

int solve_2(const std::string& str) {
    std::regex pattern("(mul\\(([1-9][0-9]*),([1-9][0-9]*)\\))|(do\\(\\))|(don't\\(\\))");
    std::sregex_iterator begin(str.begin(), str.end(), pattern);
    std::sregex_iterator end;
    
    int sum = 0;
    bool enabled = true;
    for (auto it = begin; it != end; ++it) {
        std::smatch match = *it;
        if (match[1].matched && enabled) {
            int num1 = std::stoi(match[2].str());
            int num2 = std::stoi(match[3].str());
            sum += (num1 * num2);
        } else if (match[4].matched) {
            enabled = true;
        } else if (match[5].matched) {
            enabled = false;
        }
    } 
    return sum;
}

int main() {
    std::ifstream sample_input_1("input/sample_input_1.txt");
    std::string sample1 = read_input(sample_input_1);

    std::ifstream sample_input_2("input/sample_input_2.txt");
    std::string sample2 = read_input(sample_input_2);

    std::ifstream input("input/input.txt");
    std::string input_str = read_input(input);

    assert(161 == solve_1(sample1));
    std::cout << solve_1(input_str) << std::endl;

    assert(48 == solve_2(sample2));
    std::cout << solve_2(input_str) << std::endl;

    return 0;
}
