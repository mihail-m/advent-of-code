package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day16Test extends BaseTest<Day16Task, Long> {

    private static final long SAMPLE_TEST_RESULT_1 = 1651;
    private static final long SAMPLE_TEST_RESULT_2 = 1707;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day16Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day16Task.Solution.FIND_MAX_PRESSURE)
                .build(), SAMPLE_TEST_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day16Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day16Task.Solution.FIND_MAX_PRESSURE_WITH_ELEPHANT)
                .build(), SAMPLE_TEST_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day16Task.builder(parseInput(INPUT_FILE))
                .solve(Day16Task.Solution.FIND_MAX_PRESSURE)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day16Task.builder(parseInput(INPUT_FILE))
                .solve(Day16Task.Solution.FIND_MAX_PRESSURE_WITH_ELEPHANT)
                .build());
    }

    private Map<String, Day16Task.Node> parseInput(String inputFile) {
        Map<String, Day16Task.Node> graph = new HashMap<>();

        readInput(inputFile).forEach(line -> {
            List<String> info = Arrays.stream(line.replaceAll("[^0-9|^A-Z]", " ")
                    .trim()
                    .split(" "))
                    .filter(s -> !s.isEmpty())
                    .toList();

            graph.put(info.get(1), new Day16Task.Node(info.get(1), Integer.parseInt(info.get(2)), info.subList(3, info.size())));
        });

        return graph;
    }
}
