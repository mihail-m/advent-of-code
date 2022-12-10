package aoc;

import static aoc.util.TestUtil.INPUT_FILE;

import java.util.List;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day3Test extends BaseTest<Day3Task, Integer> {

    private static final List<String> SAMPLE_INPUT = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw");

    private static final int SAMPLE_RESULT_TASK_1 = 157;

    private static final int SAMPLE_RESULT_TASK_2 = 70;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day3Task.builder(SAMPLE_INPUT)
                .solve(Day3Task.Solution.FIND_DUPLICATE_ITEMS_SUM)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day3Task.builder(SAMPLE_INPUT)
                .solve(Day3Task.Solution.FIND_BADGES_SUM)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day3Task.builder(readInput(INPUT_FILE))
                .solve(Day3Task.Solution.FIND_DUPLICATE_ITEMS_SUM)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day3Task.builder(readInput(INPUT_FILE))
                .solve(Day3Task.Solution.FIND_BADGES_SUM)
                .build());
    }
}
