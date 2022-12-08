package advent;

import advent.base.Task;

import java.util.List;
import java.util.Map;

public class Day2Task extends Task {

    private final List<String> matches;

    private int result;

    private Day2Task(List<String> matches) {
        this.matches = matches;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Solution implements SolutionStrategy<Day2Task> {

        FIND_GUESSED_STRATEGY_SCORE {
            @Override
            public void solve(Day2Task solution) {
                Map<String, Integer> matchPoints = Map.of(
                        "A X", 4,
                        "A Y", 8,
                        "A Z", 3,
                        "B X", 1,
                        "B Y", 5,
                        "B Z", 9,
                        "C X", 7,
                        "C Y", 2,
                        "C Z", 6
                );

                solution.result = solution.matches.stream()
                        .mapToInt(matchPoints::get)
                        .sum();
            }
        },

        FIND_ACTUAL_STRATEGY_SCORE {
            @Override
            public void solve(Day2Task solution) {
                Map<String, Integer> matchPoints = Map.of(
                        "A X", 3,
                        "A Y", 4,
                        "A Z", 8,
                        "B X", 1,
                        "B Y", 5,
                        "B Z", 9,
                        "C X", 2,
                        "C Y", 6,
                        "C Z", 7
                );

                solution.result = solution.matches.stream()
                        .mapToInt(matchPoints::get)
                        .sum();
            }
        }
    }

    public static Builder<Day2Task> builder(List<String> input) {
        return new Builder<>(() -> new Day2Task(input));
    }
}
