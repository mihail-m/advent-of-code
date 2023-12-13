package day05

import (
	"regexp"
	"strconv"
	"strings"
)

var rNum = regexp.MustCompile("[0-9]+")

type Range struct {
	from int64
	to   int64
	add  int64
}

func toInt64(s string) int64 {
	res, _ := strconv.ParseInt(s, 10, 64)
	return res
}

func getMaps(input []string) [][]Range {
	maps := make([][]Range, 0)
	ranges := make([]Range, 0)
	for line := 3; line < len(input); line++ {
		if input[line] == "" || line == len(input)-1 {
			maps = append(maps, ranges)
			ranges = make([]Range, 0)
			line++
			continue
		}
		r := rNum.FindAllString(input[line], -1)
		from := toInt64(r[1])
		to := from + toInt64(r[2]) - 1
		add := toInt64(r[0]) - from
		ranges = append(ranges, Range{from, to, add})
	}
	return maps
}

func inRange(seed int64, ranges []Range) bool {
	for _, r := range ranges {
		if r.from <= seed && seed <= r.to {
			return true
		}
	}
	return false
}

func Solve1(input []string) int64 {
	seeds := rNum.FindAllString(strings.Split(input[0], ":")[1], -1)
	maps := getMaps(input)

	var res int64 = -1
	for _, s := range seeds {
		seed := toInt64(s)
		for _, ranges := range maps {
			for _, r := range ranges {
				if r.from <= seed && seed <= r.to {
					seed += r.add
					break
				}
			}
		}
		if res == -1 {
			res = seed
		} else {
			res = min(res, seed)
		}
	}
	return res
}

func Solve2(input []string) int64 {
	seedRanges := make([]Range, 0)
	nums := rNum.FindAllString(strings.Split(input[0], ":")[1], -1)
	for i := 0; i < len(nums); i += 2 {
		f := toInt64(nums[i])
		t := f + toInt64(nums[i+1])
		seedRanges = append(seedRanges, Range{f, t, 0})

	}
	maps := getMaps(input)

	var res int64 = 0
	for {
		seed := res
		for i := len(maps) - 1; i >= 0; i-- {
			for _, r := range maps[i] {
				if r.from <= seed-r.add && seed-r.add <= r.to {
					seed -= r.add
					break
				}
			}
		}
		if inRange(seed, seedRanges) {
			return res
		}
		res++
	}
}
