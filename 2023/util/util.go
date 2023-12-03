package util

import (
	"bytes"
	"fmt"
	"os"
)

func ReadInput(fileName string) []string {
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
