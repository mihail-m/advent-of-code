package day13

import (
	"strconv"
	"strings"
)

func rec(i int, pat string, g int, g_len int, m []int, mem *map[string]int) int {
	if i == len(pat) {
		if g == len(m) || (g_len > 0 && g == len(m)-1 && g_len == m[g]) {
			return 1
		}
		return 0
	}
	state := strconv.Itoa(i) + "_" + strconv.Itoa(g) + "_" + strconv.Itoa(g_len) + "_" + string(pat[i])
	val, ok := (*mem)[state]
	if ok {
		return val
	}
	if pat[i] == '.' {
		if g_len == 0 {
			(*mem)[state] = rec(i+1, pat, g, g_len, m, mem)
		} else if g >= len(m) || g_len != m[g] {
			(*mem)[state] = 0
		} else {
			(*mem)[state] = rec(i+1, pat, g+1, 0, m, mem)
		}
	} else if pat[i] == '#' {
		if g >= len(m) || g_len+1 > m[g] {
			(*mem)[state] = 0
		} else {
			(*mem)[state] = rec(i+1, pat, g, g_len+1, m, mem)
		}
	} else { // pat[i] == '?'
		(*mem)[state] = rec(i, pat[:i]+"."+pat[i+1:], g, g_len, m, mem) + rec(i, pat[:i]+"#"+pat[i+1:], g, g_len, m, mem)
	}
	return (*mem)[state]
}

func Solve1(input []string) (result int) {
	for _, line := range input {
		args := strings.Split(line, " ")
		nms := strings.Split(args[1], ",")
		nums := make([]int, len(nms))
		for i, n := range nms {
			nums[i], _ = strconv.Atoi(n)
		}
		mem := make(map[string]int)
		result += rec(0, args[0], 0, 0, nums, &mem)
	}
	return result
}

func Solve2(input []string) (result int) {
	for _, line := range input {
		args := strings.Split(line, " ")
		nms := strings.Split(args[1], ",")
		nums := make([]int, 5*len(nms))
		pat := ""
		for cnt := 0; cnt < 5; cnt++ {
			pat = pat + "?" + args[0]
			for i, n := range nms {
				nums[cnt*len(nms)+i], _ = strconv.Atoi(n)
			}
		}
		mem := make(map[string]int)
		result += rec(0, pat[1:], 0, 0, nums, &mem)
	}
	return result
}
