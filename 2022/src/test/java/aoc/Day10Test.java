package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day10Test extends BaseTest<Day10Task, String> {

    private static final String SAMPLE_RESULT_1 = "13140";

    private static final String SAMPLE_RESULT_2 = """
                    ##..##..##..##..##..##..##..##..##..##..
                    ###...###...###...###...###...###...###.
                    ####....####....####....####....####....
                    #####.....#####.....#####.....#####.....
                    ######......######......######......####
                    #######.......#######.......#######.....""";

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day10Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day10Task.Solution.FIND_SUM_INTERESTING_SIGNAL_STRENGTHS)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask1(Day10Task.builder(readInput(SAMPLE_INPUT_FILE))
                .solve(Day10Task.Solution.FIND_RENDERED_IMAGE)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day10Task.builder(readInput(INPUT_FILE))
                .solve(Day10Task.Solution.FIND_SUM_INTERESTING_SIGNAL_STRENGTHS)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day10Task.builder(readInput(INPUT_FILE))
                .solve(Day10Task.Solution.FIND_RENDERED_IMAGE)
                .build());
    }
}
