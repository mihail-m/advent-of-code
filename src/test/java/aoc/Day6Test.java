package aoc;

import static aoc.util.TestUtil.openFile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import aoc.util.BaseTest;

public class Day6Test extends BaseTest<Day6Task, Integer> {

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
        sampleTestTask1(Day6Task.builder(SAMPLE_INPUTS[testNumber])
                .solve(Day6Task.Solution.FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build(), SAMPLE_RESULTS_TASK_1[testNumber]);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    public void sampleTestTask2(int testNumber) {
        sampleTestTask2(Day6Task.builder(SAMPLE_INPUTS[testNumber])
                .solve(Day6Task.Solution.FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build(), SAMPLE_RESULTS_TASK_2[testNumber]);
    }

    @Test
    public void testTask1() {
        testTask1(Day6Task.builder(readInput())
                .solve(Day6Task.Solution.FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day6Task.builder(readInput())
                .solve(Day6Task.Solution.FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS)
                .build());
    }

    private String readInput() {
        return openFile(this.getClass()).nextLine();
    }
}
