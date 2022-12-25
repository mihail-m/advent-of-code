package aoc;

import java.util.ArrayDeque;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import aoc.base.Task;

public class Day24Task extends Task<List<String>, Integer> {

    private Day24Task(List<String> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day24Task> {

        FIND_DISTANCE {
            @Override
            public void solve(Day24Task task) {
                int mapStates = lcm((task.input.size() - 2), (task.input.get(0).length() - 2));
                Map<Integer, Set<Integer>> blizzards = populateBlizzards(task.input, mapStates);

                final int[] start = {0, 1};
                final int[] end = {task.input.size() - 1, task.input.get(0).length() - 2};

                task.result = bfs(task.input, start[0], start[1], end[0], end[1], 0, blizzards, mapStates);
            }
        },

        FIND_TIME_TO_GET_SNACKS {
            @Override
            public void solve(Day24Task task) {
                int mapStates = lcm((task.input.size() - 2), (task.input.get(0).length() - 2));
                Map<Integer, Set<Integer>> blizzards = populateBlizzards(task.input, mapStates);

                final int[] start = {0, 1};
                final int[] end = {task.input.size() - 1, task.input.get(0).length() - 2};

                task.result += bfs(task.input, start[0], start[1], end[0], end[1], 0, blizzards, mapStates);
                task.result += bfs(task.input, end[0], end[1], start[0], start[1], task.result % mapStates, blizzards, mapStates);
                task.result += bfs(task.input, start[0], start[1], end[0], end[1], task.result % mapStates, blizzards, mapStates);
            }
        };

        private static final int[][] MOVES = {
                {-1,  0},
                { 1,  0},
                { 0, -1},
                { 0,  1},
                { 0,  0}};

        private static final Map<Character, Integer> DIR = Map.of(
                '^', 0,
                'v', 1,
                '<', 2,
                '>', 3);

        @SuppressWarnings("DataFlowIssue")
        private static int bfs(List<String> map, int startRow, int startCol, int endRow,
                int endCol, int startState, Map<Integer, Set<Integer>> blizzards, int mapStates) {

            int[][][] distances = populateDistances(map, mapStates);

            Queue<Integer> qRow = new ArrayDeque<>();
            Queue<Integer> qCol = new ArrayDeque<>();
            Queue<Integer> qMapState = new ArrayDeque<>();

            qRow.add(startRow);
            qCol.add(startCol);
            qMapState.add(startState);
            distances[startRow][startCol][startState] = 0;

            while (!qMapState.isEmpty()) {
                int row = qRow.poll();
                int col = qCol.poll();
                int mapState = qMapState.poll();

                int time = (distances[row][col][mapState] + 1);
                int nextMapState = (mapState + 1) % mapStates;

                for (int mv = 0; mv < 5; mv++) {
                    int nextRow = row + MOVES[mv][0];
                    int nextCol = col + MOVES[mv][1];

                    if (!validMove(map, nextRow, nextCol)) {
                        continue;
                    }

                    if (distances[nextRow][nextCol][nextMapState] != Integer.MAX_VALUE) {
                        continue;
                    }

                    if (blizzards.get(encode(map, nextRow, nextCol)).contains(nextMapState)) {
                        continue;
                    }

                    if (nextRow == endRow && nextCol == endCol) {
                        return time;
                    }

                    qRow.add(nextRow);
                    qCol.add(nextCol);
                    qMapState.add(nextMapState);
                    distances[nextRow][nextCol][nextMapState] = time;
                }
            }

            return Integer.MAX_VALUE;
        }

        private static int[][][] populateDistances(List<String> map, int mapStates) {
            int[][][] distances = new int[map.size()][map.get(0).length()][mapStates];
            for (int i = 0; i < map.size(); i++) {
                for (int j = 0; j < map.get(i).length(); j++) {
                    for (int t = 0; t < mapStates; t++) {
                        distances[i][j][t] = Integer.MAX_VALUE;
                    }
                }
            }
            return distances;
        }

        private static Map<Integer, Set<Integer>> populateBlizzards(List<String> map, int waitTime) {
            int rows = map.size();
            int cols = map.get(0).length();

            Map<Integer, Set<Integer>> blizzards = new HashMap<>();
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    blizzards.put(encode(map, row, col), new HashSet<>());
                }
            }

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    if (map.get(row).charAt(col) != '#' && map.get(row).charAt(col) != '.') {
                        blizzards.get(encode(map, row, col)).add(0);

                        int mv = DIR.get(map.get(row).charAt(col));
                        int newRow = row;
                        int newCol = col;
                        for (int time = 1; time < waitTime; time++) {
                            newRow += MOVES[mv][0];
                            newCol += MOVES[mv][1];

                            if (newRow == 0) {
                                newRow = rows - 2;
                            }
                            if (newRow == rows - 1) {
                                newRow = 1;
                            }
                            if (newCol == 0) {
                                newCol = cols - 2;
                            }
                            if (newCol == cols - 1) {
                                newCol = 1;
                            }

                            blizzards.get(encode(map, newRow, newCol)).add(time);
                        }
                    }
                }
            }

            return blizzards;
        }

        private static int encode(List<String> input, int row, int col) {
            return row * input.get(0).length() + col;
        }

        private static boolean validMove(List<String> input, int row, int col) {
            return row >= 0 && col >= 0
                    && row < input.size() && col < input.get(0).length()
                    && input.get(row).charAt(col) != '#';
        }

        private static int gcd(int a, int b) {
            if (a == 0) {
                return b;
            }
            return gcd(b % a, a);
        }

        private static int lcm(int a, int b) {
            return (a / gcd(a, b)) * b;
        }
    }

    public static Builder<Day24Task> builder(List<String> input) {
        return new Builder<>(() -> new Day24Task(input));
    }
}
