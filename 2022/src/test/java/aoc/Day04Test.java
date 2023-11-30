package aoc;

import static aoc.util.TestUtil.INPUT_FILE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day04Test extends BaseTest<Day04Task, Integer> {

    static final List<List<Integer>> SAMPLE_INPUT = List.of(
            List.of(2, 4, 6 ,8),
            List.of(2, 3, 4 ,5),
            List.of(5, 7, 7 ,9),
            List.of(2, 8, 3 ,7),
            List.of(6, 6, 4 ,6),
            List.of(2, 6, 4 ,8));

    private static final int SAMPLE_RESULT_TASK_1 = 2;

    private static final int SAMPLE_RESULT_TASK_2 = 4;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day04Task.builder(SAMPLE_INPUT)
                .solve(Day04Task.Solution.FIND_COMPLETELY_COVERED_INTERVALS_COUNT)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day04Task.builder(SAMPLE_INPUT)
                .solve(Day04Task.Solution.FIND_OVERLAPPING_INTERVALS_COUNT)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day04Task.builder(readInput())
                .solve(Day04Task.Solution.FIND_COMPLETELY_COVERED_INTERVALS_COUNT)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day04Task.builder(readInput())
                .solve(Day04Task.Solution.FIND_OVERLAPPING_INTERVALS_COUNT)
                .build());
    }

    private List<List<Integer>> readInput() {
        return readInput(INPUT_FILE)
                .stream()
                .map(line -> Arrays.stream(line.split("([,\\-])"))
                        .map(Integer::parseInt)
                        .toList())
                .collect(Collectors.toList());
    }
}
