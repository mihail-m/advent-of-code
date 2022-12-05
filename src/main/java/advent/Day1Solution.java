package advent;

import java.util.List;

public class Day1Solution {
    private final List<List<Integer>> elfsFoodList;

    private int result;

    private Day1Solution(List<List<Integer>> elfsFoodList) {
        this.result = 0;
        this.elfsFoodList = elfsFoodList;
    }

    public int getResult() {
        return this.result;
    }

    public enum Task {
        FIND_MAX_CALORIE_COUNT {
            @Override
            public void solve(Day1Solution solution) {
                solution.result = solution.elfsFoodList.stream()
                        .mapToInt(foodList -> foodList.stream()
                                .mapToInt(food -> food)
                                .sum())
                        .max()
                        .orElse(0);
            }
        },
        FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS {
            @Override
            public void solve(Day1Solution solution) {
                solution.result = solution.elfsFoodList.stream()
                        .mapToInt(foodList -> foodList.stream()
                                .mapToInt(food -> food)
                                .sum())
                        .sorted()
                        .skip(solution.elfsFoodList.size() - 3)
                        .limit(3)
                        .sum();
            }
        };

        public abstract void solve(Day1Solution solution);
    }

    public static class Builder {
        private final Day1Solution solution;
        private Builder(List<List<Integer>> input) {
            this.solution = new Day1Solution(input);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day1Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(List<List<Integer>> input) {
        return new Builder(input);
    }
}
