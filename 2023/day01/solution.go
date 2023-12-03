package day01

import (
	"unicode"
)

func Solve1(input []string) int {
	res := 0
	for _, line := range input {
		dig := make([]int, 0)
		for _, r := range line {
			if unicode.IsDigit(r) {
				dig = append(dig, int(r-'0'))
			}
		}
		res += dig[0]*10 + dig[len(dig)-1]
	}
	return res
}

func Solve2(input []string) int {
	res := 0
	digStr := []string{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"}
	for _, line := range input {
		digs := make([]int, 0)
		for i, r := range line {
			if unicode.IsDigit(r) {
				digs = append(digs, int(r-'0'))
				continue
			}
			for v, dig := range digStr {
				if i+len(dig) <= len(line) && line[i:i+len(dig)] == dig {
					digs = append(digs, v+1)
					break
				}
			}
		}
		res += digs[0]*10 + digs[len(digs)-1]
	}
	return res
}
