package day1

import (
	"bytes"
	"fmt"
	"os"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	if result := Solve1(readInput("sample_input1.txt")); result != 142 {
		t.Errorf("Expected 142 but got %d", result)
	}
}

func TestTask1(t *testing.T) {
	fmt.Println(Solve1(readInput("input.txt")))
}

func TestTask2SampleInput(t *testing.T) {
	if result := Solve2(readInput("sample_input2.txt")); result != 281 {
		t.Errorf("Expected 281 but got %d", result)
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
