package day01

import (
	"aoc_2023/util"
	"fmt"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	if result := Solve1(util.ReadInput("sample_input1.txt")); result != 142 {
		t.Errorf("Expected 142 but got %d", result)
	}
}

func TestTask1(t *testing.T) {
	fmt.Println(Solve1(util.ReadInput("input.txt")))
}

func TestTask2SampleInput(t *testing.T) {
	if result := Solve2(util.ReadInput("sample_input2.txt")); result != 281 {
		t.Errorf("Expected 281 but got %d", result)
	}
}

func TestTask2(t *testing.T) {
	fmt.Println(Solve2(util.ReadInput("input.txt")))
}
