package day13

func reflect(s int, pat []string, err int, axis int) bool {
	t1, t2 := len(pat), len(pat[0])
	if axis == 0 {
		t1, t2 = len(pat[0]), len(pat)
	}
	for i := 0; i < t1; i++ {
		i1, i2 := s, s+1
		for i1 >= 0 && i2 < t2 {
			if axis == 1 && pat[i][i1] != pat[i][i2] {
				err--
			}
			if axis == 0 && pat[i1][i] != pat[i2][i] {
				err--
			}
			if err < 0 {
				return false
			}
			i1--
			i2++
		}
	}
	if err > 0 {
		return false
	}
	return true
}

func findReflection(pat []string, err int) int {
	for row := 0; row < len(pat)-1; row++ {
		if reflect(row, pat, err, 0) {
			return (row + 1) * 100
		}
	}
	for col := 0; col < len(pat[0])-1; col++ {
		if reflect(col, pat, err, 1) {
			return col + 1
		}
	}
	return 0
}

func solve(input []string, err int) (result int) {
	input = append(input, "")
	pattern := make([]string, 0)
	for _, line := range input {
		if line == "" {
			result += findReflection(pattern, err)
			pattern = make([]string, 0)
			continue
		}
		pattern = append(pattern, line)
	}
	return result
}

func Solve1(input []string) int {
	return solve(input, 0)
}

func Solve2(input []string) int {
	return solve(input, 1)
}
