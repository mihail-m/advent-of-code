package advent;

import java.util.List;

public class Day4Solution {

    private final List<List<Integer>> intervalPairs;

    private int result;

    private Day4Solution(List<List<Integer>> intervalPairs) {
        this.result = 0;
        this.intervalPairs = intervalPairs;
    }

    public int getResult() {
        return this.result;
    }

    public enum Task {
        FIND_COMPLETELY_COVERED_INTERVALS_COUNT {
            @Override
            public void solve(Day4Solution solution) {
                solution.result = (int) solution.intervalPairs.stream()
                        .filter(Task::intervalCompletelyCovered)
                        .count();
            }
        },
        FIND_OVERLAPPING_INTERVALS_COUNT {
            @Override
            public void solve(Day4Solution solution) {
                solution.result = (int) solution.intervalPairs.stream()
                        .filter(Task::intervalOverlap)
                        .count();
            }
        };

        public abstract void solve(Day4Solution solution);

        private static boolean intervalCompletelyCovered(List<Integer> intervals) {
            assert intervals.size() == 4;

            return (intervals.get(0) >= intervals.get(2) && intervals.get(1) <= intervals.get(3))
                    || intervals.get(0) <= intervals.get(2) && intervals.get(1) >= intervals.get(3);
        }

        private static boolean intervalOverlap(List<Integer> intervals) {
            assert intervals.size() == 4;

           return (intervals.get(0) <= intervals.get(2) && intervals.get(2) <= intervals.get(1))
                   || (intervals.get(0) <= intervals.get(3) && intervals.get(3) <= intervals.get(1))
                   || (intervals.get(2) <= intervals.get(0) && intervals.get(0) <= intervals.get(3))
                   || (intervals.get(2) <= intervals.get(1) && intervals.get(1) <= intervals.get(3));
        }
    }

    public static class Builder {
        private final Day4Solution solution;
        private Builder(List<List<Integer>> input) {
            this.solution = new Day4Solution(input);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day4Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(List<List<Integer>> input) {
        return new Builder(input);
    }
}
