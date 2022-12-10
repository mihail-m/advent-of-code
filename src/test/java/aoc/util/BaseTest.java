package aoc.util;

import static aoc.util.TestUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;

import aoc.base.Task;

public abstract class BaseTest<TaskType extends Task<?, SolutionType>, SolutionType> {

    protected void sampleTestTask1(TaskType task, SolutionType expectedResult) {
        Assertions.assertEquals(expectedResult, task.getResult());
    }

    protected void sampleTestTask2(TaskType task, SolutionType expectedResult) {
        Assertions.assertEquals(expectedResult, task.getResult());
    }

    protected void testTask1(TaskType task) {
        System.out.printf(RESULT_MESSAGE, "1", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "1");
    }

    protected void testTask2(TaskType task) {
        System.out.printf(RESULT_MESSAGE, "2", task.getResult());
        postAndValidateResult(this.getClass(), task.getResult().toString(), "2");
    }

    protected List<String> readInput(String inputFile) {
        Scanner scanner = openFile(this.getClass(), inputFile);

        List<String> input = new ArrayList<>();
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }

        return input;
    }
}
