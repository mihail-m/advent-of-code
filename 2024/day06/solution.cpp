#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>
#include <map>

void read_input(std::ifstream& file, std::vector<std::string>& mat, std::pair<int, int>& start) {
    std::string line;
    while (std::getline(file, line)) {
        mat.push_back(line);
        for (int i = 0; i < line.size(); i++) {
            if (line[i] == '^') {
                start = std::pair<int, int>(mat.size() - 1, i);
            }
        }
    }
}

const int moves[4][2] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

bool inside(const std::vector<std::string>& mat, std::pair<int, int> cell) {
    if (cell.first < 0 || cell.second < 0) {
        return false;
    }
    if (cell.first >= mat.size() || cell.second >= mat[cell.first].size()) {
        return false;
    }
    return true;
}

int solve_1(std::vector<std::string> mat, std::pair<int, int> start) {
    int result = 1;
    int dir = 0;
    std::pair<int, int> cur(start.first, start.second);

    while (inside(mat, cur)) {
        if (mat[cur.first][cur.second] == '#') {
            cur = std::pair<int, int>(cur.first - moves[dir][0], cur.second - moves[dir][1]);
            dir = (dir + 1) % 4;
        }

        cur = std::pair<int, int>(cur.first + moves[dir][0], cur.second + moves[dir][1]);
        if (mat[cur.first][cur.second] == '.') {
            result++;
            mat[cur.first][cur.second] = 'X';
        }
    }

    return result;
}

bool loops(std::vector<std::string> mat, std::pair<int, int> start) {
    int dir = 0;
    std::pair<int, int> cur(start.first, start.second);
    std::map< std::pair<int, int>, std::vector<bool> > cnt;

    while (inside(mat, cur)) {
        if (mat[cur.first][cur.second] == '#') {
            cur = std::pair<int, int>(cur.first - moves[dir][0], cur.second - moves[dir][1]);
            dir = (dir + 1) % 4;
        } else {
            if (!cnt.count(cur)) {
                cnt[cur] = std::vector<bool>(4, false);
            }
            if (cnt[cur][dir]) {
                return true;
            }
            cnt[cur][dir] = true;
        }

        cur = std::pair<int, int>(cur.first + moves[dir][0], cur.second + moves[dir][1]);
        if (mat[cur.first][cur.second] == '.') {
            mat[cur.first][cur.second] = 'X';
        }
    }
    return false;
}

int solve_2(std::vector<std::string>& mat, std::pair<int, int> start) {
    int result = 0;

    for (int i = 0; i < mat.size(); i++) {
        for (int j = 0; j < mat[i].size(); j++) {
            if (mat[i][j] == '.') {
                mat[i][j] = '#';
                result += loops(mat, start);
                mat[i][j] = '.';
            }
        }
    }

    return result;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector<std::string> sample_mat;
    std::pair<int, int> sample_start;
    read_input(sample_input, sample_mat, sample_start);

    std::ifstream input("input/input.txt"); 
    std::vector<std::string> mat;
    std::pair<int, int> start;
    read_input(input, mat, start);

    assert(41 == solve_1(sample_mat, sample_start));
    std::cout << solve_1(mat, start) << std::endl;

    assert(6 == solve_2(sample_mat, sample_start));
    std::cout << solve_2(mat, start) << std::endl;

    return 0;
}
