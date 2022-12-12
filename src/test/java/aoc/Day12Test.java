package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day12Test extends BaseTest<Day12Task, Integer> {

    private static final int SAMPLE_RESULT_1 = 31;

    private static final int SAMPLE_RESULT_2 = 29;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day12Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day12Task.Solution.FIND_SHORTEST_PATH)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask1(Day12Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day12Task.Solution.FIND_BEST_TRAIL)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day12Task.builder(readInput(INPUT_FILE))
                .solve(Day12Task.Solution.FIND_SHORTEST_PATH)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day12Task.builder(readInput(INPUT_FILE))
                .solve(Day12Task.Solution.FIND_BEST_TRAIL)
                .build());
    }
}
