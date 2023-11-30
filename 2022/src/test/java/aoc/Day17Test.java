package aoc;

import aoc.util.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

public class Day17Test extends BaseTest<Day17Task, Long> {

    private static final long SAMPLE_TEST_RESULT_1 = 3068;
    private static final long SAMPLE_TEST_RESULT_2 = 1514285714288L;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day17Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day17Task.Solution.SIMULATE_2022_ROCKS)
                .build(), SAMPLE_TEST_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day17Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day17Task.Solution.CALCULATE_1000000000000_ROCKS)
                .build(), SAMPLE_TEST_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day17Task.builder(parseInput(INPUT_FILE))
                .solve(Day17Task.Solution.SIMULATE_2022_ROCKS)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day17Task.builder(parseInput(INPUT_FILE))
                .solve(Day17Task.Solution.CALCULATE_1000000000000_ROCKS)
                .build());
    }

    private String parseInput(String inputFile) {
        return readInput(inputFile).get(0);
    }
}
