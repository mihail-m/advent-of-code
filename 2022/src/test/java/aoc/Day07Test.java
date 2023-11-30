package aoc;

import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;
import static aoc.util.TestUtil.INPUT_FILE;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day07Test extends BaseTest<Day07Task, Long> {

    private static final long SAMPLE_RESULT_TASK_1 = 95437;

    private static final long SAMPLE_RESULT_TASK_2 = 24933642;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day07Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day07Task.Solution.FIND_DIRECTORIES_UNDER_100000_SIZE_SUM)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day07Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day07Task.Solution.FIND_DIRECTORY_TO_DELETE)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day07Task.builder(readInput(INPUT_FILE))
                .solve(Day07Task.Solution.FIND_DIRECTORIES_UNDER_100000_SIZE_SUM)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day07Task.builder(readInput(INPUT_FILE))
                .solve(Day07Task.Solution.FIND_DIRECTORY_TO_DELETE)
                .build());
    }
}
