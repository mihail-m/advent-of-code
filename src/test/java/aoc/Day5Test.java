package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day5Test extends BaseTest<Day5Task, String> {

    static final Map<Integer, List<Character>> SAMPLE_INPUT = Map.of(
            1, List.of('Z', 'N'),
            2, List.of('M', 'C', 'D'),
            3, List.of('P'));

    static final List<List<Integer>> SAMPLE_INSTRUCTIONS = List.of(
            List.of(1, 2, 1),
            List.of(3, 1, 3),
            List.of(2, 2, 1),
            List.of(1, 1, 2)
    );

    private static final String SAMPLE_RESULT_TASK_1 = "CMZ";

    private static final String SAMPLE_RESULT_TASK_2 = "MCD";

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day5Task.builder(new Day5Task.Input(SAMPLE_INPUT, SAMPLE_INSTRUCTIONS))
                .solve(Day5Task.Solution.FIND_TOP_CRATES_CRATE_MOVER_9000)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day5Task.builder(new Day5Task.Input(SAMPLE_INPUT, SAMPLE_INSTRUCTIONS))
                .solve(Day5Task.Solution.FIND_TOP_CRATES_CRATE_MOVER_9001)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day5Task.builder(readInput())
                .solve(Day5Task.Solution.FIND_TOP_CRATES_CRATE_MOVER_9000)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day5Task.builder(readInput())
                .solve(Day5Task.Solution.FIND_TOP_CRATES_CRATE_MOVER_9001)
                .build());
    }

    private Day5Task.Input readInput() {
        Scanner scanner = openFile(this.getClass(), INPUT_FILE);
        return new Day5Task.Input(readCrates(scanner), readInstructions(scanner));
    }

    private Map<Integer, List<Character>> readCrates(Scanner scanner) {
        Map<Integer, List<Character>> crates = new HashMap<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.isEmpty()) {
                break;
            }

            for (int index = 1, key = 1; index < line.length(); index += 4, key++) {
                if (line.charAt(index) < 'A' || line.charAt(index) > 'Z') {
                    continue;
                }

                if (!crates.containsKey(key)) {
                    crates.put(key, new ArrayList<>());
                }
                crates.get(key).add(line.charAt(index));
            }
        }

        crates.forEach((__, container) -> Collections.reverse(container));
        return crates;
    }

    private List<List<Integer>> readInstructions(Scanner scanner) {
        List<List<Integer>> instructions = new ArrayList<>();

        while (scanner.hasNextLine()) {
            List<String> line = Arrays.stream(scanner.nextLine().split("[^0-9]"))
                    .filter(el -> !el.isEmpty())
                    .toList();

            assert line.size() == 3;

            int amount = Integer.parseInt(line.get(0));
            int from = Integer.parseInt(line.get(1));
            int to = Integer.parseInt(line.get(2));

            instructions.add(List.of(amount, from, to));
        }

        return instructions;
    }
}
