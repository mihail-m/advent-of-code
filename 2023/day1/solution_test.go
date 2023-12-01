package day1

import (
	"bytes"
	"fmt"
	"os"
	"testing"
)

func TestTask1SampleInput(t *testing.T) {
	fmt.Println(readInput("sample_input"))
}

// func TestTask1(t *testing.T) {

// }

// func TestTask2SampleInput(t *testing.T) {

// }

// func TestTask2(t *testing.T) {

// }

func readInput(fileName string) []string {
	body, err := os.ReadFile("input/" + fileName + ".txt")
	if err != nil {
		fmt.Printf("unable to read file: %v", err)
	}
	lines := bytes.Split(body, []byte(" "))

	result := make([]string, len(lines))
	for _, line := range lines {
		result = append(result, string(line))
	}
	return result
}
