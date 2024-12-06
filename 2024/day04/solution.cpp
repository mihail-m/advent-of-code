#include <iostream>
#include <fstream>
#include <cassert>
#include <regex>

std::vector<std::string> read_input(std::ifstream& file) {
    std::string line;
    std::vector<std::string> grid;
    while (std::getline(file, line)) {
        grid.push_back(line);  
    }
    return grid;
}

const char xmas[4] = {'X', 'M', 'A', 'S'};
const int move[8][2] = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

int solve_1(const std::vector<std::string>& grid) {
    int result = 0;
    for (size_t r = 0; r < grid.size(); r++) {
        for (size_t c = 0; c < grid[r].size(); c++) {
            if (grid[r][c] != xmas[0]) {
                continue;
            }
            for (int mv = 0; mv < 8; mv++) {
                int new_r = r;
                int new_c = c;
                bool found = true;
                for (int l = 1; l < 4; l++) {
                    new_r += move[mv][0];
                    new_c += move[mv][1];
                    if (new_r < 0 || new_r >= grid.size()) {
                        found = false;
                        break;
                    }
                    if (new_c < 0 || new_c >= grid[new_r].size()) {
                        found = false;
                        break;
                    }
                    if (grid[new_r][new_c] != xmas[l]) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    result++;
                }
            }
        }
    }
    return result;
}

const char match[4][3][3] = {
    {{'M', '.', 'M'},
     {'.', 'A', '.'},
     {'S', '.', 'S'}},

    {{'M', '.', 'S'},
     {'.', 'A', '.'},
     {'M', '.', 'S'}},

    {{'S', '.', 'M'},
     {'.', 'A', '.'},
     {'S', '.', 'M'}},

    {{'S', '.', 'S'},
     {'.', 'A', '.'},
     {'M', '.', 'M'}}
};

int solve_2(const std::vector<std::string>& grid) {
    int result = 0;
    for (size_t r = 0; r < grid.size(); r++) {
        for (size_t c = 0; c < grid[r].size(); c++) {
            for (int m = 0; m < 4; m++) {
                bool found = true;
                for (size_t rr = 0; rr < 3; rr++) {
                    for (size_t cc = 0; cc < 3; cc++) {
                        if (r + rr >= grid.size() || c + cc >= grid[r + rr].size()) {
                            found = false;
                            break;
                        }
                        if (match[m][rr][cc] == '.') {
                            continue;
                        }
                        if (match[m][rr][cc] != grid[r+rr][c+cc]) {
                            found = false;
                            break;
                        }
                    }
                }
                if (found) {
                    result++;
                    break;
                }
            }
        }
    }
    return result;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector<std::string> sample = read_input(sample_input);

    std::ifstream input("input/input.txt");
    std::vector<std::string> input_grid = read_input(input);

    assert(18 == solve_1(sample));
    std::cout << solve_1(input_grid) << std::endl;

    assert(9 == solve_2(sample));
    std::cout << solve_2(input_grid) << std::endl;

    return 0;
}
