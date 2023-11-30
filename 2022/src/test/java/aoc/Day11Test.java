package aoc;

import static aoc.Day11Task.Monkey;

import static aoc.util.TestUtil.INPUT_FILE;
import static aoc.util.TestUtil.SAMPLE_INPUT_FILE;
import static aoc.util.TestUtil.openFile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import aoc.util.BaseTest;

public class Day11Test extends BaseTest<Day11Task, Long> {

    private static final Long SAMPLE_RESULT_1 = 10605L;

    private static final Long SAMPLE_RESULT_2 = 2713310158L;

    @Test
    public void sampleTestTask1() {
        sampleTestTask1(Day11Task.builder(parseInput(SAMPLE_INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS_20)
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
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS_20)
                .build());
    }

    @Test
    public void testTask2() {
        testTask2(Day11Task.builder(parseInput(INPUT_FILE))
                .solve(Day11Task.Solution.FIND_MONKEY_BUSINESS_10K)
                .build());
    }

    private List<Monkey> parseInput(String fileName) {
        Scanner scanner = openFile(this.getClass(), fileName);

        List<Monkey> input = new ArrayList<>();
        Monkey monkey = new Day11Task.Monkey();

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

    private void startingOp(Monkey monkey, String line) {
        monkey.items = new ArrayDeque<>(Arrays
                .stream(getOnlyNumbers(line).split(" "))
                .filter(str->!str.isEmpty())
                .map(Long::parseLong)
                .toList());
    }

    private void operationOp(Monkey monkey, String line) {
        String op = line.contains(Monkey.ADD) ? Monkey.ADD : Monkey.MULTIPLY;
        String num = getOnlyNumbers(line);

        monkey.operation = Monkey.getOperation(num, op);
    }

    private void testOp(Scanner scanner, List<Monkey> input, Monkey monkey, String line) {
        long num = Long.parseLong(getOnlyNumbers(line));
        int ifTrue = Integer.parseInt(getOnlyNumbers(scanner.nextLine()));
        int ifFalse = Integer.parseInt(getOnlyNumbers(scanner.nextLine()));

        monkey.test = Monkey.getTest(num, ifTrue, ifFalse);

        Monkey.addToMOD(num);

        input.add(monkey);
    }

    private String getOnlyNumbers(String str) {
        return str.replaceAll("[^0-9]", " ").trim();
    }
}
