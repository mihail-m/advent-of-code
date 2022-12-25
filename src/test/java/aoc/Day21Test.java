package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;
public class Day21Test extends BaseTest<Day21Task, Long> {

    private static final long SAMPLE_RESULT_1 = 152;
    private static final long SAMPLE_RESULT_2 = 301;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day21Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day21Task.Solution.FIND_ROOT_NUMBER)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day21Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day21Task.Solution.FIND_MY_NUMBER)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day21Task.builder(parseInput(INPUT_FILE))
                .solve(Day21Task.Solution.FIND_ROOT_NUMBER)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day21Task.builder(parseInput(INPUT_FILE))
                .solve(Day21Task.Solution.FIND_MY_NUMBER)
                .build());
    }

    private List<String[]> parseInput(String fileName) {
        return readInput(fileName).stream()
                .map(line -> line.replace(":", "").split(" "))
                .collect(Collectors.toList());
    }
}
