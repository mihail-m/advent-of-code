package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import aoc.base.Task;

public class Day17Task extends Task<String, Long> {

    public Day17Task(String input) {
        super(input);
    }

    public enum Solution implements SolutionStrategy<Day17Task> {
        SIMULATE_2022_ROCKS {

            @Override
            public void solve(Day17Task task) {
                List<StringBuilder> chamber = new ArrayList<>();
                AtomicInteger nextMove = new AtomicInteger(0);

                for (int counter = 0; counter < 2022; counter++) {
                    Solution.spawnRock(chamber, counter);
                    Solution.moveRock(chamber, task.input, nextMove);
                    Solution.completeMovement(chamber);
                    Solution.trim(chamber);
                }

                task.result = (long) chamber.size();
            }
        },

        CALCULATE_1000000000000_ROCKS {

            class MapKey {
                final int move;
                final int shape;
                final int[] layout;

                public MapKey(int m, int s, List<StringBuilder> chamber) {
                    this.move = m;
                    this.shape = s;
                    this.layout = new int[WIDTH];

                    for (int j = 0; j < WIDTH; j++) {
                        for (int i = chamber.size() - 1; i >= 0; i--) {
                            if (chamber.get(i).charAt(j) != '.') {
                                layout[j] = i;
                                break;
                            }
                        }
                    }

                    int min = chamber.size();
                    for (int i = 0; i < WIDTH; i++) {
                        min = Math.min(min, layout[i]);
                    }

                    for (int i = 0; i < WIDTH; i++) {
                        layout[i] -= min;
                    }
                }

                @Override
                public boolean equals(Object o) {
                    if (this == o) {
                        return true;
                    }
                    if (o == null || getClass() != o.getClass()) {
                        return false;
                    }
                    MapKey that = (MapKey) o;
                    return this.move == that.move
                            && this.shape == that.shape
                            && Arrays.equals(this.layout, that.layout);
                }

                @Override
                public int hashCode() {
                    return Objects.hash(this.move, this.shape, Arrays.hashCode(this.layout));
                }
            }

            class MapValue {
                final long counter;
                final long size;
                public MapValue(long c, long s) {
                    this.counter = c;
                    this.size = s;
                }
            }

            @Override
            public void solve(Day17Task task) {
                List<StringBuilder> chamber = new ArrayList<>();
                AtomicInteger nextMove = new AtomicInteger(0);

                Map<MapKey, MapValue> chamberStates = new HashMap<>();
                long counter = 0;
                while(true) {
                    MapKey cur = new MapKey(nextMove.get(), (int) (counter % SHAPES.size()), chamber);

                    if (chamberStates.containsKey(cur)) {
                        MapValue mv = chamberStates.get(cur);
                        task.result = (long) chamber.size();
                        long cycleShapesCnt = counter - mv.counter;
                        long cycleHeight = chamber.size() - mv.size;

                        long cycleReps = (1000000000000L - counter) / cycleShapesCnt;
                        long leftover = (1000000000000L - counter) % cycleShapesCnt;

                        task.result += cycleReps * cycleHeight;
                        counter = 1000000000000L - leftover;

                        break;
                    }

                    chamberStates.put(cur, new MapValue(counter, chamber.size()));

                    Solution.spawnRock(chamber, counter);
                    Solution.moveRock(chamber, task.input, nextMove);
                    Solution.completeMovement(chamber);
                    Solution.trim(chamber);
                    counter++;
                }

                long heightBefore = chamber.size();
                for (; counter < 1000000000000L; counter++) {
                    Solution.spawnRock(chamber, counter);
                    Solution.moveRock(chamber, task.input, nextMove);
                    Solution.completeMovement(chamber);
                    Solution.trim(chamber);
                }

                task.result += chamber.size() - heightBefore;
            }
        };

        private static final int WIDTH = 7;

        private static final String EMPTY_ROW = ".......";

        private static final List<List<String>> SHAPES = List.of(
                List.of("..@@@@.",
                        ".......",
                        ".......",
                        "......."),

                List.of("...@...",
                        "..@@@..",
                        "...@...",
                        ".......",
                        ".......",
                        "......."),

                List.of("....@..",
                        "....@..",
                        "..@@@..",
                        ".......",
                        ".......",
                        "......."),

                List.of("..@....",
                        "..@....",
                        "..@....",
                        "..@....",
                        ".......",
                        ".......",
                        "......."),

                List.of("..@@...",
                        "..@@...",
                        ".......",
                        ".......",
                        "......."));

        private static final Map<Character, Consumer<List<StringBuilder>>> MOVE = Map.of(
                '>', (chamber) -> {
                    for (int i = chamber.size() - 1; i >= 0; i--) {
                        for (int j = chamber.get(i).length() - 1; j > 0; j--) {
                            if (chamber.get(i).charAt(j - 1) == '@') {
                                chamber.get(i).setCharAt(j - 1, '.');
                                chamber.get(i).setCharAt(j, '@');
                            }
                        }
                    }
                },
                '<', (chamber) -> {
                    for (int i = chamber.size() - 1; i >= 0; i--) {
                        for (int j = 0; j < chamber.get(i).length() - 1; j++) {
                            if (chamber.get(i).charAt(j + 1) == '@') {
                                chamber.get(i).setCharAt(j + 1, '.');
                                chamber.get(i).setCharAt(j, '@');
                            }
                        }
                    }
                },
                'v', (chamber) -> {
                    for (int i = 0; i < chamber.size() - 1; i++) {
                        for (int j = 0; j < chamber.get(i).length(); j++) {
                            if (chamber.get(i + 1).charAt(j) == '@') {
                                chamber.get(i + 1).setCharAt(j, '.');
                                chamber.get(i).setCharAt(j, '@');
                            }
                        }
                    }
                }
        );

        private static final Map<Character, Predicate<List<StringBuilder>>> CAN_MOVE = Map.of(
                '>', (chamber) -> {
                    for (int i = chamber.size() - 1; i >= 0; i--) {
                        for (int j = 0; j < chamber.get(i).length(); j++) {
                            if (chamber.get(i).charAt(j) == '@' && (j == chamber.get(i).length() - 1 || chamber.get(i).charAt(j + 1) == '#')) {
                                return false;
                            }
                        }
                    }
                    return true;
                },
                '<', (chamber) -> {
                    for (int i = chamber.size() - 1; i >= 0; i--) {
                        for (int j = chamber.get(i).length() - 1; j >= 0 ; j--) {
                            if (chamber.get(i).charAt(j) == '@' && (j == 0 || chamber.get(i).charAt(j - 1) == '#')) {
                                return false;
                            }
                        }
                    }
                    return true;
                },
                'v', (chamber) -> {
                    for (int i = chamber.size() - 1; i >= 0; i--) {
                        for (int j = 0; j < chamber.get(i).length(); j++) {
                            if (chamber.get(i).charAt(j) == '@' && (i == 0 || chamber.get(i - 1).charAt(j) == '#')) {
                                return false;
                            }
                        }
                    }
                    return true;
                }
        );

        private static void spawnRock(List<StringBuilder> chamber, long counter) {
            List<String> shape = SHAPES.get((int) (counter % SHAPES.size()));
            for (int i = shape.size() - 1; i >= 0; i--) {
                chamber.add(new StringBuilder(shape.get(i)));
            }
        }

        private static void trim(List<StringBuilder> chamber) {
            while (!chamber.isEmpty() && EMPTY_ROW.equals(chamber.get(chamber.size() - 1).toString())) {
                chamber.remove(chamber.get(chamber.size() - 1));
            }
        }

        private static void moveRock(List<StringBuilder> chamber, String moves, AtomicInteger nextMove) {
            boolean moving = true;
            while (moving) {
                moving = false;

                if (CAN_MOVE.get(moves.charAt(nextMove.get())).test(chamber)) {
                    MOVE.get(moves.charAt(nextMove.get())).accept(chamber);
                }
                nextMove.set((nextMove.get() + 1) % moves.length());

                if (CAN_MOVE.get('v').test(chamber)) {
                    MOVE.get('v').accept(chamber);
                    moving = true;
                }
            }
        }

        private static void completeMovement(List<StringBuilder> chamber) {
            for (StringBuilder s : chamber) {
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == '@') {
                        s.setCharAt(i, '#');
                    }
                }
            }
        }
    }

    public static Builder<Day17Task> builder(String input) {
        return new Builder<>(() -> new Day17Task(input));
    }
}
