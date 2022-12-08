package advent;

import static advent.util.TestUtil.INPUT_FILE;
import static advent.util.TestUtil.openFile;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Day6Test {

    private static final String[] SAMPLE_INPUTS = {
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
            "bvwbjplbgvbhsrlpgdmjqwftvncz",
            "nppdvjthqldpwncqszvftbrmjlhg",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"};

    private static final int[] SAMPLE_RESULTS_TASK_1 = new int[] { 7, 5, 6, 10, 11 };

    private static final int[] SAMPLE_RESULTS_TASK_2 = new int[] { 19, 23, 23, 29, 26 };

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void sampleTestTask1(int testNumber) {
        Day6Task task = Day6Task.builder(SAMPLE_INPUTS[testNumber])
                .solve(Day6Task.Solution.FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build();

        Assertions.assertEquals(SAMPLE_RESULTS_TASK_1[testNumber], task.getResult());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void sampleTestTask2(int testNumber) {
        Day6Task task = Day6Task.builder(SAMPLE_INPUTS[testNumber])
                .solve(Day6Task.Solution.FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build();

        Assertions.assertEquals(SAMPLE_RESULTS_TASK_2[testNumber], task.getResult());
    }

    @Test
    public void testTask1() {
        Day6Task task = Day6Task.builder(readInput())
                .solve(Day6Task.Solution.FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build();

        System.out.printf("Result for task 1 is: %s", task.getResult());
    }

    @Test
    public void testTask2() {
        Day6Task task = Day6Task.builder(readInput())
                .solve(Day6Task.Solution.FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build();

        System.out.printf("Result for task 2 is: %s", task.getResult());
    }

    private String readInput() {
        return openFile(this.getClass(), INPUT_FILE).nextLine();
    }
}
