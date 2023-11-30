package aoc;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day14Test extends BaseTest<Day14Task, Integer> {

    private static final int SAMPLE_RESULT_1 = 24;
    private static final int SAMPLE_RESULT_2 = 93;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day14Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day14Task.Solution.FIND_FALLEN_UNITS_OF_SAND)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask2(Day14Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day14Task.Solution.FIND_UNITS_OF_SAND_WITH_FLOOR)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day14Task.builder(parseInput(INPUT_FILE))
                .solve(Day14Task.Solution.FIND_FALLEN_UNITS_OF_SAND)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day14Task.builder(parseInput(INPUT_FILE))
                .solve(Day14Task.Solution.FIND_UNITS_OF_SAND_WITH_FLOOR)
                .build());
    }

    private List<StringBuilder> parseInput(String inputFile) {
        List<String> input = readInput(inputFile);

        List<StringBuilder> cave = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            cave.add(new StringBuilder(".".repeat(1000)));
        }

        input.forEach(line -> {
            String[] info = line.replace(" ", "").split("->");

            for (int i = 0; i + 1 < info.length; i++) {
                String[] c1 = info[i].split(",");
                String[] c2 = info[i + 1].split(",");
                draw(cave, c1, c2);
            }
        });

        int row = 0;
        for (int i = 0; i < cave.size(); i++) {
            if (cave.get(i).toString().contains("#")) {
                row = i;
            }
        }
        return cave.subList(0, row + 2);
    }

    private void draw(List<StringBuilder> cave, String[] c1, String[] c2) {
        int row = Integer.parseInt(c1[1]);
        int col = Integer.parseInt(c1[0]);
        int toRow = Integer.parseInt(c2[1]);
        int toCol = Integer.parseInt(c2[0]);

        while (row != toRow || col != toCol) {
            cave.get(row).setCharAt(col, '#');
            if (row < toRow) row++;
            if (row > toRow) row--;
            if (col < toCol) col++;
            if (col > toCol) col--;
        }

        cave.get(row).setCharAt(col, '#');
    }
}
