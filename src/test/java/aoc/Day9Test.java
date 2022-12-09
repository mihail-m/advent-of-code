package aoc;

import static aoc.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day9Test extends BaseTest<Day9Task, Integer> {

    private static final List<String> SAMPLE_INPUT_1 = List.of(
            "R 4",
            "U 4",
            "L 3",
            "D 1",
            "R 4",
            "D 1",
            "L 5",
            "R 2"
    );

    private static final List<String> SAMPLE_INPUT_2 = List.of(
            "R 5",
            "U 8",
            "L 8",
            "D 3",
            "R 17",
            "D 10",
            "L 25",
            "U 20"
    );

    private static final int SAMPLE_RESULT_1 = 13;

    private static final int SAMPLE_RESULT_2 = 36;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day9Task.builder(SAMPLE_INPUT_1)
                .solve(Day9Task.Solution.FIND_VISITED_CELLS_COUNT_SIZE_2)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask1(Day9Task.builder(SAMPLE_INPUT_2)
                .solve(Day9Task.Solution.FIND_VISITED_CELLS_COUNT_SIZE_10)
                .build(), SAMPLE_RESULT_2);
    }


    @Test
    public void testTask1() {
        testTask1(Day9Task.builder(readInput())
                .solve(Day9Task.Solution.FIND_VISITED_CELLS_COUNT_SIZE_2)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day9Task.builder(readInput())
                .solve(Day9Task.Solution.FIND_VISITED_CELLS_COUNT_SIZE_10)
                .build());
    }

    private List<String> readInput() {
        Scanner scanner = openFile(this.getClass());

        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }

        return input;
    }
}
