package advent;

import advent.base.Task;

import java.util.Arrays;

public class Day8Task extends Task<Integer[][], Integer> {

    private Day8Task(Integer[][] input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day8Task> {

        FIND_VISIBLE_TREES {
            @Override
            public void solve(Day8Task task) {
                task.result = traverseAllDirections(task.input);
            }
        },

        FIND_MAX_VIEWING_SCORE {
            @Override
            public void solve(Day8Task task) {
                task.result = checkAllViewingScores(task.input);
            }
        };

        private static int traverseAllDirections(Integer[][] map) {
            boolean[][] visible = new boolean[map.length][map[0].length];
            for (boolean[] booleans : visible) {
                Arrays.fill(booleans, false);
            }

            int result = 0;

            for (int i = 0; i < map.length; i++) {
                int max = -1;
                for (int j = 0; j < map[i].length; j++) {
                    if (!visible[i][j] && map[i][j] > max) {
                        result++;
                        visible[i][j] = true;
                    }
                    max = Math.max(max, map[i][j]);
                }

                max = -1;
                for (int j = map[i].length -1 ; j >= 0; j--) {
                    if (!visible[i][j] && map[i][j] > max) {
                        result++;
                        visible[i][j] = true;
                    }
                    max = Math.max(max, map[i][j]);
                }
            }

            for (int i = 0; i < map[0].length; i++) {
                int max = -1;
                for (int j = 0; j < map.length; j++) {
                    if (!visible[j][i] && map[j][i] > max) {
                        result++;
                        visible[j][i] = true;
                    }
                    max = Math.max(max, map[j][i]);
                }

                max = -1;
                for (int j = map.length - 1; j >= 0; j--) {
                    if (!visible[j][i] && map[j][i] > max) {
                        result++;
                        visible[j][i] = true;
                    }
                    max = Math.max(max, map[j][i]);
                }
            }

            return result;
        }

        private static int checkAllViewingScores(Integer[][] map) {
            int result = 0;

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {

                    int scoreDown = i == map.length - 1 ? 0 : 1;
                    for (int t = i + 1; t < map.length - 1 && map[t][j] < map[i][j]; t++) {
                        scoreDown++;
                    }

                    int scoreUp = i == 0 ? 0 : 1;
                    for (int t = i - 1; t >= 1 && map[t][j] < map[i][j]; t--) {
                        scoreUp++;
                    }

                    int scoreLeft = j == map[i].length - 1 ? 0 : 1;
                    for (int t = j + 1; t < map[i].length - 1 && map[i][t] < map[i][j]; t++) {
                        scoreLeft++;
                    }

                    int scoreRight = j == 0 ? 0 : 1;
                    for (int t = j - 1; t >= 1 && map[i][t] < map[i][j]; t--) {
                        scoreRight++;
                    }

                    result = Math.max(result, scoreDown * scoreUp * scoreLeft * scoreRight);
                }
            }

            return result;
        }
    }

    public static Builder<Day8Task> builder(Integer[][] map) {
        return new Builder<>(() -> new Day8Task(map));
    }
}
