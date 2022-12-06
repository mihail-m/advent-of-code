package advent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3Solution {

    private final List<String> rucksacks;

    private int result;

    private Day3Solution(List<String> matches) {
        this.rucksacks = matches;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Task {
        FIND_DUPLICATE_ITEMS_SUM {
            @Override
            public void solve(Day3Solution solution) {
                solution.result = solution.rucksacks.stream()
                        .mapToInt(rucksack -> findMatchingItemValue(List.of(
                                rucksack.substring(0, rucksack.length() / 2),
                                rucksack.substring(rucksack.length() / 2))))
                        .sum();
            }
        },
        FIND_BADGES_SUM {
            @Override
            public void solve(Day3Solution solution) {
                for (int index = 0; index < solution.rucksacks.size(); index += 3) {
                    solution.result += findMatchingItemValue(List.of(
                            solution.rucksacks.get(index),
                            solution.rucksacks.get(index + 1),
                            solution.rucksacks.get(index + 2)
                    ));
                }
            }
        };

        public abstract void solve(Day3Solution solution);

        private static int findMatchingItemValue(List<String> itemGroups) {
            List<Set<Character>> itemSets = new ArrayList<>();
            itemGroups.forEach(itemGroup -> {
                Set<Character> items = new HashSet<>();
                for (int index = 0; index < itemGroup.length(); index++) {
                    items.add(itemGroup.charAt(index));
                }
                itemSets.add(items);
            });

            Set<Character> matchingItem = new HashSet<>(itemSets.get(0));
            itemSets.forEach(matchingItem::retainAll);
            assert matchingItem.size() == 1;

            char item = matchingItem.stream().findFirst().get();

            if (item >= 'a') {
                return item - 'a' + 1;
            } else {
                return item - 'A' + 27;
            }
        }
    }

    public static class Builder {
        private final Day3Solution solution;
        private Builder(List<String> input) {
            this.solution = new Day3Solution(input);
        }

        public Builder solve(Task task) {
            task.solve(this.solution);
            return this;
        }

        public Day3Solution build() {
            return this.solution;
        }
    }

    public static Builder builder(List<String> input) {
        return new Builder(input);
    }
}
