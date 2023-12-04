package day04

import (
	"aoc_2023/util"
	"fmt"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	if result := Solve1(util.ReadInput("sample_input.txt")); result != 13 {
		t.Errorf("Expected 13 but got %d", result)
	}
}

func TestTask1(t *testing.T) {
	fmt.Println(Solve1(util.ReadInput("input.txt")))
}

func TestTask2SampleInput(t *testing.T) {
	if result := Solve2(util.ReadInput("sample_input.txt")); result != 30 {
		t.Errorf("Expected 30 but got %d", result)
	}
}

func TestTask2(t *testing.T) {
	fmt.Println(Solve2(util.ReadInput("input.txt")))
}
