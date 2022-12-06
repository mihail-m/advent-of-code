package advent;

import java.util.List;
import java.util.Map;

public class Day2Solution {

    private final List<String> matches;

    private int result;

    private Day2Solution(List<String> matches) {
        this.matches = matches;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Task {
        FIND_GUESSED_STRATEGY_SCORE {
            @Override
            public void solve(Day2Solution solution) {
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
            public void solve(Day2Solution solution) {
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
        };

        public abstract void solve(Day2Solution solution);
    }

    public static class Builder {
        private final Day2Solution solution;
        private Builder(List<String> input) {
            this.solution = new Day2Solution(input);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day2Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(List<String> input) {
        return new Builder(input);
    }
}
