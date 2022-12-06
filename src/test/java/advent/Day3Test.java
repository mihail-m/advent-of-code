package advent;

import static advent.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day3Test {

    private static final String INPUT_FILE = "input.txt";

    private static final List<String> SAMPLE_INPUT = List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw");

    private static final int SAMPLE_RESULT_TASK_1 = 157;

    private static final int SAMPLE_RESULT_TASK_2 = 70;

    @Test
    public void sampleTestTask1() {
        Day3Solution solution = Day3Solution.builder(SAMPLE_INPUT)
                .solve(Day3Solution.Task.FIND_DUPLICATE_ITEMS_SUM)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_1, solution.getResult());
    }

    @Test
    public void sampleTestTask2() {
        Day3Solution solution = Day3Solution.builder(SAMPLE_INPUT)
                .solve(Day3Solution.Task.FIND_BADGES_SUM)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_2, solution.getResult());
    }

    @Test
    public void testTask1() {
        Day3Solution solution = Day3Solution.builder(readInput())
                .solve(Day3Solution.Task.FIND_DUPLICATE_ITEMS_SUM)
                .build();


        System.out.printf("Result for task 1 is: %s", solution.getResult());
    }

    @Test
    public void testTask2() {
        Day3Solution solution = Day3Solution.builder(readInput())
                .solve(Day3Solution.Task.FIND_BADGES_SUM)
                .build();


        System.out.printf("Result for task 2 is: %s", solution.getResult());
    }

    private List<String> readInput() {
        Scanner scanner = openFile(this.getClass(), INPUT_FILE);

        List<String> rucksacks = new ArrayList<>();
        while (scanner.hasNextLine()) {
            rucksacks.add(scanner.nextLine());
        }

        return rucksacks;
    }
}
