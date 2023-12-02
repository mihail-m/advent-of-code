package day2

import (
	"bytes"
	"fmt"
	"os"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	if result := Solve1(readInput("sample_input1.txt")); result != 8 {
		t.Errorf("Expected 8 but got %d", result)
	}
}

func TestTask1(t *testing.T) {
	fmt.Println(Solve1(readInput("input.txt")))
}

func TestTask2SampleInput(t *testing.T) {
	if result := Solve2(readInput("sample_input2.txt")); result != 2286 {
		t.Errorf("Expected 2286 but got %d", result)
	}
}

func TestTask2(t *testing.T) {
	fmt.Println(Solve2(readInput("input.txt")))
}

func readInput(fileName string) []string {
	body, err := os.ReadFile("input/" + fileName)
	if err != nil {
		fmt.Printf("unable to read file: %v", err)
	}
	lines := bytes.Split(body, []byte("\n"))

	result := make([]string, len(lines))
	for i, line := range lines {
		result[i] = string(line)
	}
	return result
}
