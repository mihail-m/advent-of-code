package aoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aoc.base.Task;

public class Day9Task extends Task<List<String>, Integer> {

    protected Day9Task(List<String> input) {
        super(input);
        this.result = 0;
    }

    public enum Solution implements SolutionStrategy<Day9Task> {

        FIND_VISITED_CELLS_COUNT_SIZE_2 {
            @Override
            public void solve(Day9Task task) {
                task.result = simulate(task.input, 2);
            }
        },

        FIND_VISITED_CELLS_COUNT_SIZE_10 {
            @Override
            public void solve(Day9Task task) {
                task.result = simulate(task.input, 10);
            }
        };

        private static final Map<String, Integer[]> moveTo = Map.of(
                "L", new Integer[] { 0, -1},
                "R", new Integer[] { 0,  1},
                "U", new Integer[] { 1,  0},
                "D", new Integer[] {-1,  0});

        private static int simulate(List<String> input, int tailSize) {
            List<Integer[]> tPos = new ArrayList<>();
            for(int i = 0; i < tailSize; i++) {
                tPos.add(new Integer[]{0, 0});
            }

            Set<String> visitedCells = new HashSet<>();
            visitedCells.add(getLastVisitedCell(tPos));

            for (String move : input) {
                String[] move_info = move.split(" ");
                doNextMove(tPos, move_info[0], Integer.parseInt(move_info[1]), visitedCells);
            }

            return visitedCells.size();
        }

        private static void doNextMove(List<Integer[]> tPos, String direction, int distance, Set<String> visitedCells) {
            for (int moved = 0; moved < distance; moved++) {
                tPos.get(0)[0] += moveTo.get(direction)[0];
                tPos.get(0)[1] += moveTo.get(direction)[1];

                for (int i = 1; i < tPos.size(); i++) {
                    if (notAdjacent(tPos.get(i - 1), tPos.get(i))) {
                        move(tPos, i, 0);
                        move(tPos, i, 1);
                    }
                }

                visitedCells.add(getLastVisitedCell(tPos));
            }
        }

        private static void move(List<Integer[]> tPos, int tail, int coord) {
            if (Math.abs(tPos.get(tail - 1)[coord] - tPos.get(tail)[coord]) > 0) {
                tPos.get(tail)[coord] += tPos.get(tail - 1)[coord] < tPos.get(tail)[coord] ? -1 : 1;
            }
        }

        private static String getLastVisitedCell(List<Integer[]> tPos) {
            return String.format("%s_%s", tPos.get(tPos.size() - 1)[0], tPos.get(tPos.size() - 1)[1]);
        }

        private static boolean notAdjacent(Integer[] cell1, Integer[] cell2) {
            int dist = Math.abs(cell1[0] - cell2[0]) + Math.abs(cell1[1] - cell2[1]);
            return dist > 1 && (dist != 2 || Math.abs(cell1[0] - cell2[0]) != 1);
        }
    }

    public static Builder<Day9Task> builder(List<String> input) {
        return new Builder<>(() -> new Day9Task(input));
    }
}
