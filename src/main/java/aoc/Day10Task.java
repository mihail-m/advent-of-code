package aoc;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import aoc.base.Task;

public class Day10Task extends Task<List<String>, String> {

    protected Day10Task(List<String> input) {
        super(input);
        this.result = "";
    }

    public enum Solution implements SolutionStrategy<Day10Task> {

        FIND_SUM_INTERESTING_SIGNAL_STRENGTHS {
            @Override
            public void solve(Day10Task task) {
                AtomicInteger res = new AtomicInteger(0);
                AtomicInteger nextInterestingCycle = new AtomicInteger(20);

                executeCommands(task.input, ((cycle, X) -> {
                    if (cycle + 1 >= nextInterestingCycle.get()) {
                        res.addAndGet(nextInterestingCycle.get() * X);
                        nextInterestingCycle.addAndGet(40);
                    }
                }));

                task.result = String.valueOf(res.get());
            }
        },

        RENDER_IMAGE {
            @Override
            public void solve(Day10Task task) {
                StringBuilder[] res = new StringBuilder[6];
                for (int i = 0; i < res.length; i++) {
                    res[i] = new StringBuilder(".".repeat(40));
                }

                executeCommands(task.input, ((cycle, X) -> {
                    if (X - 1 <= (cycle % 40) && (cycle % 40) <= X + 1) {
                        res[(cycle / 40)].setCharAt((cycle % 40), '#');
                    }
                }));

                task.result = String.join("\n", res);
            }
        };

        private static void executeCommands(List<String> input, ResultModifier resultModifier) {
            int X = 1;
            int cycle = 0;

            for (int command = 1; command <= input.size(); command++) {
                String[] info = input.get(command - 1).split(" ");

                resultModifier.updateResult(cycle, X);

                if (info[0].equals("noop")) {
                    cycle++;
                } else {
                    resultModifier.updateResult(cycle + 1, X);

                    cycle += 2;
                    X += Integer.parseInt(info[1]);
                }
            }
        }

        private interface ResultModifier {
            void updateResult(int cycle, int X);
        }
    }

    public static Builder<Day10Task> builder(List<String> input) {
        return new Builder<>(() -> new Day10Task(input));
    }
}
