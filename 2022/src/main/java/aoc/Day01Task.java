package aoc;

import java.util.List;

import aoc.base.Task;

public class Day01Task extends Task<List<List<Integer>>, Integer> {

    private Day01Task(List<List<Integer>> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day01Task> {

        FIND_MAX_CALORIE_COUNT {
            @Override
            public void solve(Day01Task task) {
                task.result = task.input.stream()
                        .mapToInt(foodList -> foodList.stream()
                                .mapToInt(food -> food)
                                .sum())
                        .max()
                        .orElse(0);
            }
        },

        FIND_SUM_OF_3_HIGHEST_CALORIE_COUNTS {
            @Override
            public void solve(Day01Task task) {
                task.result = task.input.stream()
                        .mapToInt(foodList -> foodList.stream()
                                .mapToInt(food -> food)
                                .sum())
                        .sorted()
                        .skip(task.input.size() - 3)
                        .limit(3)
                        .sum();
            }
        }
    }

    public static Builder<Day01Task> builder(List<List<Integer>> input) {
        return new Builder<>(() -> new Day01Task(input));
    }
}
