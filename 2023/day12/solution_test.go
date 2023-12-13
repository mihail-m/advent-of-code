package day13

import (
	"aoc_2023/util"
	"fmt"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	if result := Solve1(util.ReadInput("sample_input.txt")); result != 21 {
		t.Errorf("Expected 21 but got %d", result)
	}
}

func TestTask1(t *testing.T) {
	fmt.Println(Solve1(util.ReadInput("input.txt")))
}

func TestTask2SampleInput(t *testing.T) {
	if result := Solve2(util.ReadInput("sample_input.txt")); result != 525152 {
		t.Errorf("Expected 525152 but got %d", result)
	}
}

func TestTask2(t *testing.T) {
	fmt.Println(Solve2(util.ReadInput("input.txt")))
}
