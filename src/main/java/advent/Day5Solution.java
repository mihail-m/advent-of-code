package advent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day5Solution {
    private final Map<Integer, List<Character>> crates;

    private final List<List<Integer>> instructions;

    private String result;

    private Day5Solution(Map<Integer, List<Character>> crates, List<List<Integer>> instructions) {
        this.result = "";
        this.crates = crates;
        this.instructions = instructions;
    }

    public String getResult() {
        return this.result;
    }

    public enum Task {
        FIND_TOP_CRATES_CRATE_MOVER_9000 {
            @Override
            public void solve(Day5Solution solution) {
                solution.result = findTopCrates(solution.crates, solution.instructions, (from, to, amount) -> {
                    for (int moved = 0; moved < amount; moved++) {
                        to.add(from.get(from.size() - 1));
                        from.remove(from.size() - 1);
                    }
                });
            }
        },
        FIND_TOP_CRATES_CRATE_MOVER_9001 {
            @Override
            public void solve(Day5Solution solution) {
                solution.result = findTopCrates(solution.crates, solution.instructions, (from, to, amount) -> {
                    to.addAll(from.subList(from.size() - amount, from.size()));
                    for (int removed = 0; removed < amount; removed++) {
                        from.remove(from.size() - 1);
                    }
                });
            }
        };

        public abstract void solve(Day5Solution solution);

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

    public static class Builder {
        private final Day5Solution solution;
        private Builder(Map<Integer, List<Character>> containers, List<List<Integer>> instructions) {
            this.solution = new Day5Solution(containers, instructions);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day5Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(Map<Integer, List<Character>> crates, List<List<Integer>> instructions) {
        Map<Integer, List<Character>> cratesMutable = new HashMap<>();
        crates.forEach((key, list) -> cratesMutable.put(key, new ArrayList<>(list)));
        return new Builder(cratesMutable, instructions);
    }
}
