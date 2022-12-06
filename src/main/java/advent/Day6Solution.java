package advent;

import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
import java.util.Map;

public class Day6Solution {

    private final String buffer;

    private int result;

    private Day6Solution(String buffer) {
        this.buffer = buffer;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Task {
        FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day6Solution solution) {
                solution.result = findKDifferentConsecutiveLetters(solution.buffer, 4);
            }
        },
        FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day6Solution solution) {
                solution.result = findKDifferentConsecutiveLetters(solution.buffer, 14);
            }
        };

        public abstract void solve(Day6Solution solution);

        private static int findKDifferentConsecutiveLetters(String text, int k) {
            HashMap<Character, Integer> lettersMap = new HashMap<>();
            for (int index = 0; index < k - 1; index++) {
                addLetter(lettersMap, text.charAt(index));
            }

            for (int index = k - 1; index < text.length(); index++) {
                addLetter(lettersMap, text.charAt(index));
                if (lettersMap.size() == k) {
                    return index + 1;
                }
                removeLetter(lettersMap, text.charAt(index - (k - 1)));
            }

            return -1;
        }

        private static void addLetter(Map<Character, Integer> lettersMap, char letter) {
            if (lettersMap.containsKey(letter)) {
                lettersMap.replace(letter, lettersMap.get(letter) + 1);
            } else {
                lettersMap.put(letter, 1);
            }
        }

        private static void removeLetter(Map<Character, Integer> lettersMap, char letter) {
            lettersMap.replace(letter, lettersMap.get(letter) - 1);
            if (lettersMap.get(letter) == 0) {
                lettersMap.remove(letter);
            }
        }
    }

    public static class Builder {
        private final Day6Solution solution;
        private Builder(String input) {
            this.solution = new Day6Solution(input);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day6Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(String input) {
        return new Builder(input);
    }
}
