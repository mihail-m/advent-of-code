package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day24Test extends BaseTest<Day24Task, Integer> {

    private static final int SAMPLE_RESULT_1 = 18;

    private static final int SAMPLE_RESULT_2 = 54;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day24Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day24Task.Solution.FIND_DISTANCE)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day24Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day24Task.Solution.FIND_TIME_TO_GET_SNACKS)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day24Task.builder(readInput(INPUT_FILE))
                .solve(Day24Task.Solution.FIND_DISTANCE)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day24Task.builder(readInput(INPUT_FILE))
                .solve(Day24Task.Solution.FIND_TIME_TO_GET_SNACKS)
                .build());
    }
}
