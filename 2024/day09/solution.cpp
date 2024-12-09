#include <iostream>
#include <fstream>
#include <sstream>
#include <cassert>

struct File {
    long long len;
    int id, start;
    File() {}
    File (int id, long long len): id(id), len(len) {}
    File (int id, long long len, int start): id(id), len(len), start(start) {}
};

void read_input(std::ifstream& file, std::vector<File>& empty_spaces, std::vector<File>& split_files) {
    std::string disk;
    file >> disk;

    int id = 0, pos = 0;
    for (int i = 0; i < disk.size(); i++) {
        if (i % 2 != 0) {
            empty_spaces.push_back(File(-1, disk[i] - '0', pos));
        } else {
            split_files.push_back(File(id++, disk[i] - '0', pos));
        }
        pos += disk[i] - '0';
    }
}

long long solve1(std::ifstream& file) {
    std::vector<File> empty_spaces;
    std::vector<File> split_files;
    read_input(file, empty_spaces, split_files);

    std::vector<File> files;
    int fl_l = 0;
    int fl_r = split_files.size() - 1;
    int to = empty_spaces.size();
    for (int i = 0; i < to && fl_l < fl_r; i++) {
        int space = empty_spaces[i].len;
        files.push_back(split_files[fl_l++]);
        while (space > 0 && fl_l < fl_r) {
            if (space < split_files[fl_r].len) {
                files.push_back(File(split_files[fl_r].id, space));
                split_files[fl_r].len -= space;
                space = 0;
            } else {
                files.push_back(split_files[fl_r]);
                space -= split_files[fl_r].len;
                fl_r--;
                to--;
            }
        }
    }
    while (fl_l <= fl_r) {
        files.push_back(split_files[fl_l++]);
    }

    long long sum = 0, mult = 0;
    for (File file: files) {
        for (int i = 0; i < file.len; i++) {
            sum += file.id * mult;
            mult++;
        }
    }
    return sum;
}

long long solve2(std::ifstream& file) {
    std::vector<File> empty_spaces;
    std::vector<File> split_files;
    read_input(file, empty_spaces, split_files);

    std::vector<File> files;
    for (int fl = split_files.size() - 1; fl >= 0; fl--) {
        bool moved = false;
        for (int s = 0; s < fl; s++) {
            if (empty_spaces[s].len >= split_files[fl].len) {
                files.push_back(File(split_files[fl].id, split_files[fl].len, empty_spaces[s].start));
                empty_spaces[s].len -= split_files[fl].len;
                empty_spaces[s].start += split_files[fl].len;
                moved = true;
                break;
            }
        }
        if (!moved) {
            files.push_back(split_files[fl]);
        }
    }

    long long sum = 0;
    for (File file: files) {
        for (int i = file.start; i < file.start + file.len; i++) {
            sum += file.id * i;
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

    assert(1928 == solve1(sample_input));
    std::cout << solve1(input) << std::endl;

    resetFile(sample_input);
    assert(2858 == solve2(sample_input));
    resetFile(input);
    std::cout << solve2(input) << std::endl;

    return 0;
}
