package day03

import (
	"strconv"
	"unicode"
)

var adj = [][]int{{-1, -1}, {-1, 0}, {0, -1}, {-1, 1}, {1, -1}, {1, 0}, {0, 1}, {1, 1}}

func checkAdj(input []string, r, c int) bool {
	for _, mv := range adj {
		x := r + mv[0]
		y := c + mv[1]
		if x < 0 || x >= len(input) {
			continue
		}
		if y < 0 || y >= len(input[r]) {
			continue
		}
		if !unicode.IsDigit(rune(input[x][y])) && input[x][y] != '.' {
			return true
		}
	}
	return false
}

func Solve1(input []string) int {
	sum := 0
	for r, row := range input {
		num := 0
		add := false
		for c := range row {
			if unicode.IsDigit(rune(input[r][c])) {
				num = num*10 + int(input[r][c]-'0')
				add = add || checkAdj(input, r, c)
				continue
			}
			if add {
				sum += num
			}
			add = false
			num = 0
		}
		if add {
			sum += num
		}
	}
	return sum
}

func addCogs(input []string, r, c int, cogs map[string]struct{}) map[string]struct{} {
	for _, mv := range adj {
		x := r + mv[0]
		y := c + mv[1]
		if x < 0 || x >= len(input) {
			continue
		}
		if y < 0 || y >= len(input[r]) {
			continue
		}
		if input[x][y] == '*' {
			cogs[strconv.Itoa(x)+"-"+strconv.Itoa(y)] = struct{}{}
		}
	}
	return cogs
}

func Solve2(input []string) int {
	allCogs := make(map[string][]int, 0)
	for r, row := range input {
		num := 0
		cogs := make(map[string]struct{}, 0)
		for c := range row {
			if unicode.IsDigit(rune(input[r][c])) {
				num = num*10 + int(input[r][c]-'0')
				cogs = addCogs(input, r, c, cogs)
				continue
			}
			if num == 0 {
				continue
			}
			for c := range cogs {
				allCogs[c] = append(allCogs[c], num)
			}
			num = 0
			cogs = make(map[string]struct{}, 0)
		}
		if num != 0 {
			for c := range cogs {
				allCogs[c] = append(allCogs[c], num)
			}
		}
	}
	sum := 0
	for _, nums := range allCogs {
		if len(nums) == 2 {
			sum += nums[0] * nums[1]
		}
	}
	return sum
}
