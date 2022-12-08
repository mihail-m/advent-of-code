package advent;

import static advent.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import advent.util.BaseTest;

public class Day3Test extends BaseTest<Day3Task, Integer> {

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
        sampleTestTask1(Day3Task.builder(SAMPLE_INPUT)
                .solve(Day3Task.Solution.FIND_DUPLICATE_ITEMS_SUM)
                .build(), SAMPLE_RESULT_TASK_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day3Task.builder(SAMPLE_INPUT)
                .solve(Day3Task.Solution.FIND_BADGES_SUM)
                .build(), SAMPLE_RESULT_TASK_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day3Task.builder(readInput())
                .solve(Day3Task.Solution.FIND_DUPLICATE_ITEMS_SUM)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day3Task.builder(readInput())
                .solve(Day3Task.Solution.FIND_BADGES_SUM)
                .build());
    }

    private List<String> readInput() {
        Scanner scanner = openFile(this.getClass());

        List<String> rucksacks = new ArrayList<>();
        while (scanner.hasNextLine()) {
            rucksacks.add(scanner.nextLine());
        }

        return rucksacks;
    }
}
