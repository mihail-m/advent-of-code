package advent;

import static advent.util.TestUtil.openFile;
import static advent.util.TestUtil.postAndValidateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day1Test {

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
        Day1Task task = Day1Task.builder(SAMPLE_INPUT)
                .solve(Day1Task.Solution.FIND_MAX_CALORIE_COUNT)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_1, task.getResult());
    }

    @Test
    public void sampleTestTask2() {
        Day1Task task = Day1Task.builder(SAMPLE_INPUT)
                .solve(Day1Task.Solution.FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_2, task.getResult());
    }

    @Test
    public void testTask1() {
        Day1Task task = Day1Task
                .builder(readInput())
                .solve(Day1Task.Solution.FIND_MAX_CALORIE_COUNT)
                .build();

        System.out.printf("Result for task 1 is: %s", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "1");
    }

    @Test
    public void testTask2() {
        Day1Task task = Day1Task
                .builder(readInput())
                .solve(Day1Task.Solution.FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS)
                .build();

        System.out.printf("Result for task 2 is: %s", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "2");
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
