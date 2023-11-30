package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day19Test extends BaseTest<Day19Task, Long> {

    private static final long SAMPLE_TEST_RESULT_1 = 33;
    private static final long SAMPLE_TEST_RESULT_2 = 56 * 62;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day19Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day19Task.Solution.FIND_QUALITY_LEVEL_SUM)
                .build(), SAMPLE_TEST_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day19Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day19Task.Solution.FIND_FIRST_3_BLUEPRINTS_PRODUCT)
                .build(), SAMPLE_TEST_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day19Task.builder(parseInput(INPUT_FILE))
                .solve(Day19Task.Solution.FIND_QUALITY_LEVEL_SUM)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day19Task.builder(parseInput(INPUT_FILE))
                .solve(Day19Task.Solution.FIND_FIRST_3_BLUEPRINTS_PRODUCT)
                .build());
    }

    private List<Day19Task.Blueprint> parseInput(String inputFile) {
        List<Day19Task.Blueprint> input = new ArrayList<>();

        readInput(inputFile).forEach(line -> {
            List<Integer> info = Arrays.stream(line.replaceAll("[^0-9]", " ")
                            .trim()
                            .split(" "))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::parseInt)
                    .toList();

            input.add(new Day19Task.Blueprint(info.get(1), info.get(2), info.get(3), info.get(4), info.get(5), info.get(6)));
        });

        return input;
    }
}
