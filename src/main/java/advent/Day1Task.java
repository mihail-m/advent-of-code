package advent;

import advent.base.Task;

import java.util.List;

public class Day1Task extends Task {

    private final List<List<Integer>> elfsFoodList;

    private int result;

    private Day1Task(List<List<Integer>> elfsFoodList) {
        this.result = 0;
        this.elfsFoodList = elfsFoodList;
    }

    public int getResult() {
        return this.result;
    }

    public enum Solution implements SolutionStrategy<Day1Task> {

        FIND_MAX_CALORIE_COUNT {
            @Override
            public void solve(Day1Task solution) {
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
            public void solve(Day1Task solution) {
                solution.result = solution.elfsFoodList.stream()
                        .mapToInt(foodList -> foodList.stream()
                                .mapToInt(food -> food)
                                .sum())
                        .sorted()
                        .skip(solution.elfsFoodList.size() - 3)
                        .limit(3)
                        .sum();
            }
        }
    }

    public static Builder<Day1Task> builder(List<List<Integer>> input) {
        return new Builder<>(() -> new Day1Task(input));
    }
}
