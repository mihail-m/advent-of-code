package aoc;

import static aoc.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day1Test extends BaseTest<Day1Task, Integer> {

    private static final List<List<Integer>> SAMPLE_INPUT = List.of(
            List.of(1000, 2000, 3000),
            List.of(4000),
            List.of(5000, 6000),
            List.of(7000, 8000, 9000),
            List.of(10000)
    );

    private static final int SAMPLE_RESULT_TASK_1 = 24000;

    private static final int SAMPLE_RESULT_TASK_2 = 45000;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day1Task.builder(SAMPLE_INPUT)
                .solve(Day1Task.Solution.FIND_MAX_CALORIE_COUNT)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day1Task.builder(SAMPLE_INPUT)
                .solve(Day1Task.Solution.FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day1Task.builder(readInput())
                .solve(Day1Task.Solution.FIND_MAX_CALORIE_COUNT)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day1Task.builder(readInput())
                .solve(Day1Task.Solution.FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS)
                .build());
    }

    private List<List<Integer>> readInput() {
        Scanner scanner = openFile(this.getClass());

        List<List<Integer>> elfsFoodList = new ArrayList<>();
        List<Integer> currentElfFood = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String inputLine = scanner.nextLine();

            if (inputLine.isEmpty()) {
                elfsFoodList.add(currentElfFood);
                currentElfFood = new ArrayList<>();
            } else {
                currentElfFood.add(Integer.parseInt(inputLine));
            }
        }
        elfsFoodList.add(currentElfFood);

        return elfsFoodList;
    }
}
