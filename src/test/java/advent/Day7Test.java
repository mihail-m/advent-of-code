package advent;

import static advent.util.TestUtil.openFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Day7Test {

    private static final String INPUT_FILE = "input.txt";

    private static final List<String> SAMPLE_INPUT = List.of(
            "$ cd /",
            "$ ls",
            "dir a",
            "14848514 b.txt",
            "8504156 c.dat",
            "dir d",
            "$ cd a",
            "$ ls",
            "dir e",
            "29116 f",
            "2557 g",
            "62596 h.lst",
            "$ cd e",
            "$ ls",
            "584 i",
            "$ cd ..",
            "$ cd ..",
            "$ cd d",
            "$ ls",
            "4060174 j",
            "8033020 d.log",
            "5626152 d.ext",
            "7214296 k"
    );

    private static final int SAMPLE_RESULT_TASK_1 = 95437;

    private static final int SAMPLE_RESULT_TASK_2 = 24933642;

    @Test
    public void sampleTestTask1() {
        Day7Task task = Day7Task.builder(SAMPLE_INPUT)
                .solve(Day7Task.Solution.FIND_DIRECTORIES_UNDER_100000_SIZE_SUM)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_1, task.getResult());
    }

    @Test
    public void sampleTestTask2() {
        Day7Task task = Day7Task.builder(SAMPLE_INPUT)
                .solve(Day7Task.Solution.FIND_DIRECTORY_TO_DELETE)
                .build();

        Assertions.assertEquals(SAMPLE_RESULT_TASK_2, task.getResult());
    }

    @Test
    public void testTask1() {
        Day7Task task = Day7Task.builder(readInput())
                .solve(Day7Task.Solution.FIND_DIRECTORIES_UNDER_100000_SIZE_SUM)
                .build();

        System.out.printf("Result for task 1 is: %s", task.getResult());;
    }

    @Test
    public void testTask2() {
        Day7Task task = Day7Task.builder(readInput())
                .solve(Day7Task.Solution.FIND_DIRECTORY_TO_DELETE)
                .build();

        System.out.printf("Result for task 2 is: %s", task.getResult());;
    }

    private List<String> readInput() {
        Scanner scanner = openFile(this.getClass(), INPUT_FILE);

        List<String> consoleOutput = new ArrayList<>();
        while (scanner.hasNextLine()) {
            consoleOutput.add(scanner.nextLine());
        }

        return consoleOutput;
    }
}
