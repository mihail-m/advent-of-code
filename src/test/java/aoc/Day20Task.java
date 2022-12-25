package aoc;

import aoc.base.Task;

import java.util.List;

public class Day20Task extends Task<List<Integer>, Long> {

    private Day20Task(List<Integer> input) {
        super(input);
        this.result = 0L;
    }

    public enum Solution implements SolutionStrategy<Day20Task> {

        FIND_GROVE_COORDINATES_SUM {
            @Override
            public void solve(Day20Task task) {

            }
        }
    }

    public static Builder<Day20Task> builder(List<Integer> input) {
        return new Builder<>(() -> new Day20Task(input));
    }
}
