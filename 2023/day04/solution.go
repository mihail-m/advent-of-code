package day04

import (
	"regexp"
	"strings"
)

var rNum = regexp.MustCompile("[0-9]+")

func matches(numbers []string) int {
	res := 0
	for _, num1 := range rNum.FindAllString(numbers[0], -1) {
		for _, num2 := range rNum.FindAllString(numbers[1], -1) {
			if num1 == num2 {
				res++
			}
		}
	}
	return res
}

func Solve1(input []string) int {
	res := 0
	for _, line := range input {
		m := matches(strings.Split(strings.Split(line, ":")[1], "|"))
		res += (1 << m) / 2
	}
	return res
}

func Solve2(input []string) int {
	cards := make([]int, len(input))
	for i := 0; i < len(cards); i++ {
		cards[i] = 1
	}
	for i, line := range input {
		m := matches(strings.Split(strings.Split(line, ":")[1], "|"))
		for j := i + 1; j <= i+m; j++ {
			cards[j] += cards[i]
		}
	}
	sum := 0
	for _, c := range cards {
		sum += c
	}
	return sum
}
