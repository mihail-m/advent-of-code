package advent;

import advent.base.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5Task extends Task<Day5Task.Input, String> {

    private Day5Task(Input input) {
        super(input);
        this.result = "";
    }

    public static class Input {
        public Map<Integer, List<Character>> crates;
        public List<List<Integer>> instructions;

        public Input(Map<Integer, List<Character>> crates, List<List<Integer>> instructions) {
            Map<Integer, List<Character>> cratesMutable = new HashMap<>();
            crates.forEach((key, list) -> cratesMutable.put(key, new ArrayList<>(list)));
            this.crates = cratesMutable;
            this.instructions = instructions;
        }
    }

    public enum Solution implements SolutionStrategy<Day5Task> {

        FIND_TOP_CRATES_CRATE_MOVER_9000 {
            @Override
            public void solve(Day5Task task) {
                task.result = findTopCrates(task.input.crates, task.input.instructions, (from, to, amount) -> {
                    for (int moved = 0; moved < amount; moved++) {
                        to.add(from.get(from.size() - 1));
                        from.remove(from.size() - 1);
                    }
                });
            }
        },

        FIND_TOP_CRATES_CRATE_MOVER_9001 {
            @Override
            public void solve(Day5Task task) {
                task.result = findTopCrates(task.input.crates, task.input.instructions, (from, to, amount) -> {
                    to.addAll(from.subList(from.size() - amount, from.size()));
                    for (int removed = 0; removed < amount; removed++) {
                        from.remove(from.size() - 1);
                    }
                });
            }
        };

        private static String findTopCrates(Map<Integer, List<Character>> crates,
                List<List<Integer>> instructions,
                MoverFunction crateMover) {

            instructions.forEach(instruction -> crateMover.move(
                    crates.get(instruction.get(1)),
                    crates.get(instruction.get(2)),
                    instruction.get(0)));

            StringBuilder result = new StringBuilder();
            crates.forEach((key, list) -> {
                if (!list.isEmpty()) {
                    result.append(list.get(list.size() - 1));
                }
            });
            return result.toString();
        }

        private interface MoverFunction {
            void move(List<Character> from, List<Character> to, int amount);
        }
    }

    public static Builder<Day5Task> builder(Input input) {
        return new Builder<>(() -> new Day5Task(input));
    }
}
