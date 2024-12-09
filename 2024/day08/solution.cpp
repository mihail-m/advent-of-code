#include <iostream>
#include <fstream>
#include <cassert>
#include <map>
#include <set>

void read_input(std::ifstream& file, std::vector<std::string>& mat) {
    std::string line;
    while (std::getline(file, line)) {
        mat.push_back(line);
    }
}

int gcd(int a, int b) {
    while (b != 0) {
        int temp = b;
        b = a % b;
        a = temp;
    }
    return a;
}

void add_new_points_1(std::set<std::pair<int, int> >& antinodes, std::vector<std::string>& mat, std::pair<int, int> p1, std::pair<int, int> p2) {
    int x_diff = p2.first - p1.first;
    int y_diff = p2.second - p1.second;

    auto n1 = std::make_pair(p1.first - x_diff, p1.second - y_diff);
    auto n2 = std::make_pair(p2.first + x_diff, p2.second + y_diff);

    if (n1.first >= 0 && n1.first < mat.size() && n1.second >= 0 && n1.second < mat[n1.first].size()) {
        antinodes.insert(n1);
    }
    if (n2.first >= 0 && n2.first < mat.size() && n2.second >= 0 && n2.second < mat[n2.first].size()) {
        antinodes.insert(n2);
    }
}

void add_new_points_2(std::set<std::pair<int, int> >& antinodes, std::vector<std::string>& mat, std::pair<int, int> p1, std::pair<int, int> p2) {
    int x_diff = p2.first - p1.first;
    int y_diff = p2.second - p1.second;
    int div = gcd(x_diff, y_diff);
    x_diff /= div;
    y_diff /= div;
    
    auto np = std::make_pair(p1.first, p1.second);
    while (np.first >= 0 && np.first < mat.size() && np.second >= 0 && np.second < mat[np.first].size()) {
        antinodes.insert(np);
        np = std::make_pair(np.first + x_diff, np.second + y_diff);
    }
    np = std::make_pair(p1.first, p1.second);
    while (np.first >= 0 && np.first < mat.size() && np.second >= 0 && np.second < mat[np.first].size()) {
        antinodes.insert(np);
        np = std::make_pair(np.first - x_diff, np.second - y_diff);
    }
}

typedef void (*PointAdder)(std::set<std::pair<int, int> >&, std::vector<std::string>&, std::pair<int, int>, std::pair<int, int>);

int solve(std::vector<std::string>& mat, PointAdder add_new_points) {
    std::map<char, std::vector<std::pair<int, int> > > mp;
    for (int i = 0; i < mat.size(); i++) {
        for (int j = 0; j < mat[i].size(); j++) {
            if (mat[i][j] != '.') {
                mp[mat[i][j]].push_back(std::make_pair(i, j));
            }
        }
    }

    std::set<std::pair<int, int> > antinodes;
    for (auto it : mp) {
        auto points = it.second;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < i; j++) {
                add_new_points(antinodes, mat, points[i], points[j]);
            }
        }
    }
    return antinodes.size();
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector<std::string> sample_mat;
    read_input(sample_input, sample_mat);

    std::ifstream input("input/input.txt"); 
    std::vector<std::string> mat;
    read_input(input, mat);

    assert(14 == solve(sample_mat, add_new_points_1));
    std::cout << solve(mat, add_new_points_1) << std::endl;

    assert(34 == solve(sample_mat, add_new_points_2));
    std::cout << solve(mat, add_new_points_2) << std::endl;

    return 0;
}