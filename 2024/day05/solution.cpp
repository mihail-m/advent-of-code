#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>
#include <map>
#include <stack>

void read_input(std::ifstream& file, std::vector< std::vector<int> >& graph, std::vector< std::vector<int> >& requests) {
    std::string line;
    bool switchToRequests = false;
    while (std::getline(file, line)) {
        if (line.empty()) {
            switchToRequests = true;
            continue;
        }
        if (!switchToRequests) {
            int n1, n2;
            char delimiter;
            std::stringstream ss(line);
            ss >> n1 >> delimiter >> n2;
            graph[n1].push_back(n2);
        } else {
            std::vector<int> numbers;
            std::stringstream ss(line);
            std::string token;
            while (std::getline(ss, token, ',')) {
                numbers.push_back(std::stoi(token));
            }
            requests.push_back(numbers);
        }
    }
}

bool isOrdered(const std::vector< std::vector<int> >& graph, const std::vector<int>& req) {
    std::map<int, int> indexMap;
    for (int j = 0; j < req.size(); j++) {
        indexMap[req[j]] = j;
    }

    bool inOrder = true;
    for (int j = 0; j < graph.size(); j++) {
        for (int node : graph[j]) {
            if (!indexMap.count(j) || !indexMap.count(node)) {
                continue;
            }
            if (indexMap[j] > indexMap[node]) {
                inOrder = false;
                break;
            }
        }
    }
    return inOrder;
}

int solve_1(const std::vector< std::vector<int> >& graph, const std::vector< std::vector<int> >& requests) {
    int result = 0;
    for (int i = 0; i < requests.size(); i++) {
        if (isOrdered(graph, requests[i])) {
            result += requests[i][requests[i].size() / 2];
        }
     }
    return result;
}

std::vector< std::vector<int> > graph_g;
bool cmp(int a, int b) {
    for (int n : graph_g[a]) {
        if (n == b) {
            return true;
        }
    }
    return false;
}

int solve_2(const std::vector< std::vector<int> >& graph, std::vector< std::vector<int> >& requests) {
    int result = 0;
    for (auto& req : requests) {
        if (isOrdered(graph, req)) {
            continue;
        }

        graph_g = graph;
        std::sort(req.begin(), req.end(), cmp);

        result += req[req.size() / 2];
     }
    return result;
}

int main() {
    std::ifstream sample_input("input/sample_input.txt");
    std::vector< std::vector<int> > sample_graph(100);
    std::vector< std::vector<int> > sample_requests;
    read_input(sample_input, sample_graph, sample_requests);

    std::ifstream input("input/input.txt");
    std::vector< std::vector<int> > graph(100);
    std::vector< std::vector<int> > requests;
    read_input(input, graph, requests);

    assert(143 == solve_1(sample_graph, sample_requests));
    std::cout << solve_1(graph, requests) << std::endl;

    assert(123 == solve_2(sample_graph, sample_requests));
    std::cout << solve_2(graph, requests) << std::endl;

    return 0;
}
