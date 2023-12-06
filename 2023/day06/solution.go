package day06

import (
	"regexp"
	"strconv"
	"strings"
)

var rNum = regexp.MustCompile("[0-9]+")

func findWays(times []string, distances []string) int {
	ways := make([]int, len(times))
	for race := 0; race < len(times); race++ {
		dist, _ := strconv.Atoi(distances[race])
		time, _ := strconv.Atoi(times[race])
		ways[race] = 0
		for hold := 0; hold <= time; hold++ {
			if hold*(time-hold) > dist {
				ways[race]++
			}
		}
	}
	res := 1
	for _, num := range ways {
		res *= num
	}
	return res
}

func Solve1(input []string) int {
	times := rNum.FindAllString(strings.Split(input[0], ":")[1], -1)
	distances := rNum.FindAllString(strings.Split(input[1], ":")[1], -1)
	return findWays(times, distances)
}

func Solve2(input []string) int {
	input[0] = strings.ReplaceAll(input[0], " ", "")
	input[1] = strings.ReplaceAll(input[1], " ", "")
	return Solve1(input)
}
