package advent;

import advent.base.Task;

import java.util.HashMap;
import java.util.Map;

public class Day6Task extends Task {

    private final String buffer;

    private int result;

    private Day6Task(String buffer) {
        this.buffer = buffer;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Solution implements SolutionStrategy<Day6Task> {

        FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day6Task solution) {
                solution.result = findKDifferentConsecutiveLetters(solution.buffer, 4);
            }
        },

        FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day6Task solution) {
                solution.result = findKDifferentConsecutiveLetters(solution.buffer, 14);
            }
        };

        private static int findKDifferentConsecutiveLetters(String text, int k) {
            Map<Character, Integer> lettersMap = new HashMap<>();
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

    public static Builder<Day6Task> builder(String input) {
        return new Builder<>(() -> new Day6Task(input));
    }
}
