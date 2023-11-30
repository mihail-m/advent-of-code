package aoc;

import aoc.util.BaseTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

public class Day18Test extends BaseTest<Day18Task, Integer> {

    private static final int SAMPLE_TEST_RESULT_1 = 64;
    private static final int SAMPLE_TEST_RESULT_2 = 58;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day18Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day18Task.Solution.FIND_SURFACE_AREA)
                .build(), SAMPLE_TEST_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day18Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day18Task.Solution.FIND_OUTER_SURFACE_AREA)
                .build(), SAMPLE_TEST_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day18Task.builder(parseInput(INPUT_FILE))
                .solve(Day18Task.Solution.FIND_SURFACE_AREA)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day18Task.builder(parseInput(INPUT_FILE))
                .solve(Day18Task.Solution.FIND_OUTER_SURFACE_AREA)
                .build());
    }

    private Integer[][][] parseInput(String inputFile) {
        Integer[][][] input = new Integer[22][22][22];
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 22; j++) {
                for (int k = 0; k < 22; k++) {
                    input[i][j][k] = 0;
                }
            }
        }

        readInput(inputFile).forEach(line -> {
            List<Integer> c = Arrays.stream(line.split(",")).map(Integer::parseInt).toList();
            input[c.get(0)][c.get(1)][c.get(2)] = 1;
        });

        return input;
    }
}
