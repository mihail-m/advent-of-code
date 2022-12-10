package aoc;

import static aoc.util.TestUtil.INPUT_FILE;

import java.util.List;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day2Test extends BaseTest<Day2Task, Integer> {

    private static final List<String> SAMPLE_INPUT = List.of("A Y", "B X", "C Z");

    private static final int SAMPLE_RESULT_TASK_1 = 15;

    private static final int SAMPLE_RESULT_TASK_2 = 12;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day2Task.builder(SAMPLE_INPUT)
                .solve(Day2Task.Solution.FIND_GUESSED_STRATEGY_SCORE)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day2Task.builder(SAMPLE_INPUT)
                .solve(Day2Task.Solution.FIND_ACTUAL_STRATEGY_SCORE)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day2Task.builder(readInput(INPUT_FILE))
                .solve(Day2Task.Solution.FIND_GUESSED_STRATEGY_SCORE)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day2Task.builder(readInput(INPUT_FILE))
                .solve(Day2Task.Solution.FIND_ACTUAL_STRATEGY_SCORE)
                .build());
    }
}
