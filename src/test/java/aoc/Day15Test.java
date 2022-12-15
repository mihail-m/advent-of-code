package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day15Test extends BaseTest<Day15Task, Long> {

    private static final long SAMPLE_RESULT_1 = 26;
    private static final long SAMPLE_RESULT_2 = 56000011;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day15Task.builder(parseInput(SAMPLE_INPUT_FILE), 10)
                .solve(Day15Task.Solution.FIND_NON_COVERED_CELLS_ROW_2000000)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day15Task.builder(parseInput(SAMPLE_INPUT_FILE), 10)
                .solve(Day15Task.Solution.FIND_ONLY_POSSIBLE_BEACON_POSITION)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day15Task.builder(parseInput(INPUT_FILE), 2_000_000)
                .solve(Day15Task.Solution.FIND_NON_COVERED_CELLS_ROW_2000000)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day15Task.builder(parseInput(INPUT_FILE), 2_000_000)
                .solve(Day15Task.Solution.FIND_ONLY_POSSIBLE_BEACON_POSITION)
                .build());
    }

    private List<Day15Task.Sensor> parseInput(String inputFile) {
        List<String> input = readInput(inputFile);

        List<Day15Task.Sensor> sensors = new ArrayList<>();

        input.forEach(line -> {
            List<Integer> info = Arrays.stream(line
                            .replaceAll("[^0-9|^-]", " ")
                            .trim()
                            .split(" "))
                    .filter(str->!str.isEmpty())
                    .map(Integer::parseInt)
                    .toList();

            sensors.add(new Day15Task.Sensor(info.get(0), info.get(1), info.get(2), info.get(3)));
        });

        return sensors;
    }
}
