package util

import "testing"

func ReadInputTest(t *testing.T) {
	input := ReadInput("test.txt")
	if len(input) > 1 {
		t.Errorf("input length expected to be 1 but is %d", len(input))
	}
	if input[0] != "test text" {
		t.Errorf("input value expected to be \"test text\" but is %s", input[0])
	}
}
