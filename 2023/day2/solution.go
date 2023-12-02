package day2

import (
	"regexp"
	"strconv"
	"strings"
)

var rNum = regexp.MustCompile("[0-9]+")
var rBlue = regexp.MustCompile("[0-9]+ blue")
var rRed = regexp.MustCompile("[0-9]+ red")
var rGreen = regexp.MustCompile("[0-9]+ green")

func get(s string, r *regexp.Regexp, rNum *regexp.Regexp) int {
	balls, err := strconv.Atoi(rNum.FindString(r.FindString(s)))
	if err != nil {
		return 0
	}
	return balls
}

func check(s string, r *regexp.Regexp, rNum *regexp.Regexp, max int) bool {
	balls, err := strconv.Atoi(rNum.FindString(r.FindString(s)))
	if err == nil && balls > max {
		return false
	}
	return true
}

func Solve1(input []string) int {
	result := 0
	for _, line := range input {
		split := strings.Split(line, ":")
		id, _ := strconv.Atoi(rNum.FindString(split[0]))

		possible := true
		for _, draw := range strings.Split(split[1], ";") {
			possible = possible && check(draw, rRed, rNum, 12)
			possible = possible && check(draw, rGreen, rNum, 13)
			possible = possible && check(draw, rBlue, rNum, 14)
		}

		if possible {
			result += id
		}
	}
	return result
}

func Solve2(input []string) int {
	result := 0
	for _, line := range input {
		draws := strings.Split(line, ":")[1]

		red, green, blue := 0, 0, 0
		for _, draw := range strings.Split(draws, ";") {
			red = max(red, get(draw, rRed, rNum))
			green = max(green, get(draw, rGreen, rNum))
			blue = max(blue, get(draw, rBlue, rNum))
		}
		result += red * green * blue
	}
	return result
}
