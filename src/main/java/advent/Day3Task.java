package advent;

import advent.base.Task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3Task extends Task {

    private final List<String> rucksacks;

    private int result;

    private Day3Task(List<String> matches) {
        this.rucksacks = matches;
        this.result = 0;
    }

    public int getResult() {
        return this.result;
    }

    public enum Solution implements SolutionStrategy<Day3Task> {

        FIND_DUPLICATE_ITEMS_SUM {
            @Override
            public void solve(Day3Task solution) {
                solution.result = solution.rucksacks.stream()
                        .mapToInt(rucksack -> findMatchingItemValue(List.of(
                                rucksack.substring(0, rucksack.length() / 2),
                                rucksack.substring(rucksack.length() / 2))))
                        .sum();
            }
        },
        FIND_BADGES_SUM {
            @Override
            public void solve(Day3Task solution) {
                for (int index = 0; index < solution.rucksacks.size(); index += 3) {
                    solution.result += findMatchingItemValue(List.of(
                            solution.rucksacks.get(index),
                            solution.rucksacks.get(index + 1),
                            solution.rucksacks.get(index + 2)
                    ));
                }
            }
        };

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

    public static Builder<Day3Task> builder(List<String> input) {
        return new Builder<>(() -> new Day3Task(input));
    }
}
