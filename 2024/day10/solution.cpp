#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>
#include <set>
#include <queue>

void read_input(std::ifstream& file, std::vector<std::string>& map) {
    std::string line;
    while (std::getline(file, line)) {
        map.push_back(line);
    }
}

const int moves[4][2] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

int dfs(std::vector<std::string>& map, int r, int c, std::set<std::pair<int, int> >& visited) {
    visited.insert(std::make_pair(r, c));
    if (map[r][c] == '9') {
        return 1;
    }
    int res = 0;
    for (int mv = 0; mv < 4; mv++) {
        int nr = r + moves[mv][0];
        int nc = c + moves[mv][1];
        if (nr < 0 || nr >= map.size()) {
            continue;
        }
        if (nc < 0 || nc >= map[nr].size()) {
            continue;
        }
        if (visited.count(std::make_pair(nr, nc))) {
            continue;
        }
        if (map[nr][nc] - map[r][c] == 1) {
            res += dfs(map, nr, nc, visited);
        }
    }
    return res;
}

int solve_1(std::vector<std::string>& map) {
    int ans = 0;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map[i].size(); j++) {
            if (map[i][j] == '0') {
                std::set<std::pair<int, int> > visited;
                ans += dfs(map, i, j, visited);
            }
        }
    }
    return ans;
}

long long solve_2(std::vector<std::string>& map) {
    std::vector<std::vector<long long> > roads;
    for (int i = 0; i < map.size(); i++) {
        roads.push_back(std::vector<long long>(map[i].size(), 0));
    }
    for (int i = 0; i < map.size(); i++) {
        roads.push_back(std::vector<long long>(map[i].size(), 0));
    }

    std::queue<std::pair<int, int> > q;
    for (int i = 0; i < map.size(); i++) {
        for (int j = 0; j < map[i].size(); j++) {
            if (map[i][j] == '0') {
                q.push(std::make_pair(i, j));
                roads[i][j] = 1;
            }
        }
    }

    while (!q.empty()) {
        int r = q.front().first;
        int c = q.front().second;
        q.pop();

        for (int mv = 0; mv < 4; mv++) {
            int nr = r + moves[mv][0];
            int nc = c + moves[mv][1];
            if (nr < 0 || nr >= map.size()) {
                continue;
            }
            if (nc < 0 || nc >= map[nr].size()) {
                continue;
            }
            if (map[nr][nc] - map[r][c] != 1) {
                continue;
            }
            if (roads[nr][nc] == 0) {
                q.push(std::make_pair(nr, nc));
            }
            roads[nr][nc] += roads[r][c];
        }
    }

    long long ans = 0;
    for (int i = 0; i < roads.size(); i++) {
        for (int j = 0; j < roads[i].size(); j++) {
            if (map[i][j] == '9') {
                ans += roads[i][j];
            }
        }
    }
    return ans;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector<std::string> sample_map;
    read_input(sample_input, sample_map);

    std::ifstream input("input/input.txt");
    std::vector<std::string> map;
    read_input(input, map);

    assert(36 == solve_1(sample_map));
    std::cout << solve_1(map) << std::endl;

    assert(81 == solve_2(sample_map));
    std::cout << solve_2(map) << std::endl;

    return 0;
}
