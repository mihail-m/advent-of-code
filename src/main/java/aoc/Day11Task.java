package aoc;

import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.function.Function;

import aoc.base.Task;

public class Day11Task extends Task<List<Day11Task.Monkey>, Long> {

    public static final String MONKEY = "Monkey";
    public static final String STARTING = "Starting";
    public static final String OPERATION = "Operation";
    public static final String TEST = "Test";

    private Day11Task(List<Day11Task.Monkey> input) {
        super(input);
        this.result = 0L;
    }

    public static class Monkey {

        public static final String ADD = "+";
        public static final String MULTIPLY = "*";

        public static Long MOD = 1L;

        public Deque<Long> items;
        public Function<Long, Long> operation;
        public Function<Long, Integer> test;

        public long inspections = 0;

        public static void addToMOD(Long num) {
            MOD *= num;
        }

        public static Function<Long, Long> getOperation(String num, String op) {
            return (item) -> {
                if (num.isEmpty()) {
                    return item * item;
                }
                return op.equals(MULTIPLY)
                        ? item * Long.parseLong(num)
                        : item + Long.parseLong(num);
            };
        }

        public static Function<Long, Integer> getTest(long num, int ifTrue, int ifFalse) {
            return (item) -> item % num == 0 ? ifTrue : ifFalse;
        }
    }

    public enum Solution implements SolutionStrategy<Day11Task> {

        FIND_MONKEY_BUSINESS {
            @Override
            public void solve(Day11Task task) {
                task.result = getMonkeyBusiness(task.input, 20, (item) -> item / 3);
            }
        },

        FIND_MONKEY_BUSINESS_10K {
            @Override
            public void solve(Day11Task task) {
                task.result = getMonkeyBusiness(task.input, 10000, (item) -> item % Monkey.MOD);
            }
        };

        private static Long getMonkeyBusiness(List<Monkey> monkeys, int rounds, Function<Long, Long> handleStress) {

            for (int round = 0; round < rounds; round++) {
                monkeys.forEach(monkey -> {
                    while (!monkey.items.isEmpty()) {
                        long item = monkey.items.pollFirst();
                        item = monkey.operation.apply(item);
                        item = handleStress.apply(item);

                        monkey.inspections++;

                        int throwTo = monkey.test.apply(item);
                        monkeys.get(throwTo).items.addLast(item);
                    }
                });
            }

            monkeys.sort(Comparator.comparingLong(monkey -> monkey.inspections));
            return monkeys.get(monkeys.size() - 1).inspections * monkeys.get(monkeys.size() - 2).inspections;
        }
    }

    public static Builder<Day11Task> builder(List<Monkey> input) {
        return new Builder<>(() -> new Day11Task(input));
    }
}
