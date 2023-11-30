package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day13Test extends BaseTest<Day13Task, Integer> {

    private static final int SAMPLE_RESULT_1 = 13;
    private static final int SAMPLE_RESULT_2 = 140;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day13Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day13Task.Solution.FIND_PAIRS_IN_ORDER)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask1(Day13Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day13Task.Solution.FIND_DIVIDER_PACKETS)
                .build(), SAMPLE_RESULT_2);
    }

    @Test void testTask1() {
        testTask1(Day13Task.builder(readInput(INPUT_FILE))
                .solve(Day13Task.Solution.FIND_PAIRS_IN_ORDER)
                .build());
    }

    @Test void testTask2() {
        testTask2(Day13Task.builder(readInput(INPUT_FILE))
                .solve(Day13Task.Solution.FIND_DIVIDER_PACKETS)
                .build());
    }
}
