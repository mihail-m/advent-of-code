package aoc;

import aoc.util.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

public class Day20Test extends BaseTest<Day20Task, Long> {

    private static final long SAMPLE_RESULT_1 = 3;
    private static final long SAMPLE_RESULT_2 = 1623178306;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day20Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day20Task.Solution.FIND_GROVE_COORDINATES_SUM)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day20Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day20Task.Solution.FIND_GROVE_COORDINATES_SUM_WITH_KEY)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day20Task.builder(parseInput(INPUT_FILE))
                .solve(Day20Task.Solution.FIND_GROVE_COORDINATES_SUM)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day20Task.builder(parseInput(INPUT_FILE))
                .solve(Day20Task.Solution.FIND_GROVE_COORDINATES_SUM_WITH_KEY)
                .build());
    }

    private List<Long> parseInput(String fileName) {
        return readInput(fileName).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
