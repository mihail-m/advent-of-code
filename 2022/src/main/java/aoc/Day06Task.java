package aoc;

import java.util.HashMap;
import java.util.Map;

import aoc.base.Task;

public class Day06Task extends Task<String, Integer> {

    private Day06Task(String input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day06Task> {

        FIND_FIRST_4_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day06Task task) {
                task.result = findKDifferentConsecutiveLetters(task.input, 4);
            }
        },

        FIND_FIRST_14_CONSECUTIVE_DIFFERENT_CHARACTERS {
            @Override
            public void solve(Day06Task task) {
                task.result = findKDifferentConsecutiveLetters(task.input, 14);
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

    public static Builder<Day06Task> builder(String input) {
        return new Builder<>(() -> new Day06Task(input));
    }
}
