package aoc;

import static aoc.util.TestUtil.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aoc.base.Day11Task;
import aoc.util.BaseTest;

public class Day11Test extends BaseTest<Day11Task, Long> {

    private static final Long SAMPLE_RESULT_1 = 10605L;

    private static final Long SAMPLE_RESULT_2 = 2713310158L;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day11Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS)
                .build(), SAMPLE_RESULT_1);
    }

    @Test
    public void sampleTestTask2() {
        sampleTestTask1(Day11Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS_10K)
                .build(), SAMPLE_RESULT_2);
    }

    @Test
    public void testTask1() {
        testTask1(Day11Task.builder(parseInput(INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day11Task.builder(parseInput(INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS_10K)
                .build());
    }

    private List<Day11Task.Monkey> parseInput(String fileName) {
        Scanner scanner = openFile(this.getClass(), fileName);

        List<Day11Task.Monkey> input = new ArrayList<>();
        Day11Task.Monkey monkey = new Day11Task.Monkey();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains(Day11Task.MONKEY)) {
                monkey = new Day11Task.Monkey();
            }

            if (line.contains(Day11Task.STARTING)) {
                startingOp(monkey, line);
            }

            if (line.contains(Day11Task.OPERATION)) {
                operationOp(monkey, line);
            }

            if (line.contains(Day11Task.TEST)) {
                testOp(scanner, input, monkey, line);
            }
        }

        return input;
    }

    private void startingOp(Day11Task.Monkey monkey, String line) {
        line = line.replaceAll("[^0-9]", " ").trim();

        monkey.items = new ArrayDeque<>(Arrays
                .stream(line.split(" "))
                .filter(str->!str.isEmpty())
                .map(Long::parseLong)
                .toList());
    }

    private void operationOp(Day11Task.Monkey monkey, String line) {
        String op = line.contains("*") ? "*" : "+";
        String num = line.replaceAll("[^0-9]", " ").trim();

        monkey.operation = (item) -> {
            if (num.isEmpty()) {
                return item * item;
            }
            return op.equals("*")
                    ? item * Long.parseLong(num)
                    : item + Long.parseLong(num);
        };
    }

    private void testOp(Scanner scanner, List<Day11Task.Monkey> input, Day11Task.Monkey monkey, String line) {
        Long num = Long.parseLong(line.replaceAll("[^0-9]", " ").trim());
        int ifTrue = Integer.parseInt(scanner.nextLine().replaceAll("[^0-9]", " ").trim());
        int ifFalse = Integer.parseInt(scanner.nextLine().replaceAll("[^0-9]", " ").trim());

        monkey.test = (item) -> item % num == 0 ? ifTrue : ifFalse;

        input.add(monkey);
        Day11Task.Monkey.MOD *= num;
    }
}
