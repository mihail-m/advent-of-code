package aoc;

import aoc.base.Task;

public class Day18Task extends Task<Integer[][][], Integer> {

    private Day18Task(Integer[][][] input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day18Task> {
        FIND_SURFACE_AREA {
            @Override
            public void solve(Day18Task task) {
                task.result = calculate(task.input, 0);
            }
        },

        FIND_OUTER_SURFACE_AREA {
            @Override
            public void solve(Day18Task task) {
                markOutsideArea(0, 0, 0, task.input);
                task.result = calculate(task.input, 2);
            }
        };

        private static final int[][] MOVE = {
                {1, 0, 0},
                {-1,  0, 0},
                {0, 1, 0},
                {0, -1, 0},
                {0, 0, 1},
                {0, 0, -1}};

        private static int calculate(Integer[][][] map, int match) {
            int result = 0;

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    for (int k = 0; k < map[i][j].length; k++) {
                        if (map[i][j][k] != 1) {
                            continue;
                        }

                        for (int mv = 0; mv < 6; mv++) {
                            int ni = i + MOVE[mv][0];
                            int nj = j + MOVE[mv][1];
                            int nk = k + MOVE[mv][2];

                            if (invalidCoords(ni, nj, nk, map)) {
                                result++;
                                continue;
                            }

                            if (map[ni][nj][nk] == match) {
                                result++;
                            }
                        }
                    }
                }
            }

            return result;
        }

        private static void markOutsideArea(int i, int j, int k, Integer[][][] map) {
            map[i][j][k] = 2;
            for (int mv = 0; mv < 6; mv++) {
                int ni = i + MOVE[mv][0];
                int nj = j + MOVE[mv][1];
                int nk = k + MOVE[mv][2];

                if (invalidCoords(ni, nj, nk, map)) {
                    continue;
                }

                if (map[ni][nj][nk] == 0) {
                    markOutsideArea(ni, nj, nk, map);
                }
            }
        }

        private static boolean invalidCoords(int ni, int nj, int nk, Integer[][][] map) {
            return ni < 0 || nj < 0 || nk < 0 || ni >= map.length || nj >= map[0].length || nk >= map[0][0].length;

        }
    }

    public static Builder<Day18Task> builder(Integer[][][] input) {
        return new Builder<>(() -> new Day18Task(input));
    }
}
