package advent;

import static advent.util.TestUtil.openFile;
import static advent.util.TestUtil.postAndValidateResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day2Test {

    private static final List<String> SAMPLE_INPUT = List.of("A Y", "B X", "C Z");

    private static final int SAMPLE_RESULT_TASK_1 = 15;

    private static final int SAMPLE_RESULT_TASK_2 = 12;

    @Test
    public void sampleTestTask1() {
        Day2Task task = Day2Task.builder(SAMPLE_INPUT)
                .solve(Day2Task.Solution.FIND_GUESSED_STRATEGY_SCORE)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_1, task.getResult());
    }

    @Test
    public void sampleTestTask2() {
        Day2Task task = Day2Task.builder(SAMPLE_INPUT)
                .solve(Day2Task.Solution.FIND_ACTUAL_STRATEGY_SCORE)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_2, task.getResult());
    }

    @Test
    public void testTask1() {
        Day2Task task = Day2Task.builder(readInput())
                .solve(Day2Task.Solution.FIND_GUESSED_STRATEGY_SCORE)
                .build();

        System.out.printf("Result for task 1 is: %s", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "1");
    }

    @Test
    public void testTask2() {
        Day2Task task = Day2Task.builder(readInput())
                .solve(Day2Task.Solution.FIND_ACTUAL_STRATEGY_SCORE)
                .build();

        System.out.printf("Result for task 2 is: %s", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "2");
    }

    private List<String> readInput() {
        Scanner scanner = openFile(this.getClass());

        List<String> matches = new ArrayList<>();
        while (scanner.hasNextLine()) {
            matches.add(scanner.nextLine());
        }

        return matches;
    }
}
