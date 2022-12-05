package advent;

import static advent.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class Day1Test {

    private static final String INPUT_FILE = "input.txt";

    @Test
    public void testTask1() {
        Day1Solution solution = Day1Solution
                .builder(readInput(INPUT_FILE))
                .solve(Day1Solution.Task.FIND_MAX_CALORIE_COUNT)
                .build();

        System.out.printf("Result for task 1 is: %s", solution.getResult());
    }

    @Test
    public void testTask2() {
        Day1Solution solution = Day1Solution
                .builder(readInput(INPUT_FILE))
                .solve(Day1Solution.Task.FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS)
                .build();

        System.out.printf("Result for task 2 is: %s", solution.getResult());
    }

    private List<List<Integer>> readInput(String testFile) {
        Scanner scanner = openFile(this.getClass(), testFile);

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
